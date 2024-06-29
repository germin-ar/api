package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.HealthAIDetection;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.port.in.GetCandidatesDiseasePlantsPortIn;
import ar.germin.api.application.port.out.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
public class GetCandidatesDiseasePlantsUseCase implements GetCandidatesDiseasePlantsPortIn {
    private final GetPlantPhotosRepository getPlantPhotosRepository;
    private final GetHealthSuggestionsRepository getHealthSuggestionsRepository;
    private final SaveCandidateDiseasePlantsRepository saveCandidateDiseasePlantsRepository;
    private final SavePlantPhotoDiseaseRelationRepository savePlantPhotoDiseaseRelationRepository;
    private final GetCandidateDiseasePlantsRepository getCandidateDiseasePlantsRepository;

    @Autowired
    public GetCandidatesDiseasePlantsUseCase(GetPlantPhotosRepository getPlantPhotosRepository,
                                             @Qualifier("cropkindwise") GetHealthSuggestionsRepository getHealthSuggestionsRepository,
                                             SaveCandidateDiseasePlantsRepository saveCandidateDiseasePlantsRepository,
                                             SavePlantPhotoDiseaseRelationRepository savePlantPhotoDiseaseRelationRepository,
                                             GetCandidateDiseasePlantsRepository getCandidateDiseasePlantsRepository) {
        this.getPlantPhotosRepository = getPlantPhotosRepository;
        this.getHealthSuggestionsRepository = getHealthSuggestionsRepository;
        this.saveCandidateDiseasePlantsRepository = saveCandidateDiseasePlantsRepository;
        this.savePlantPhotoDiseaseRelationRepository = savePlantPhotoDiseaseRelationRepository;
        this.getCandidateDiseasePlantsRepository = getCandidateDiseasePlantsRepository;
    }

    @Override
    public HealthAIDetection get(Integer id) {
        List<PlantPhoto> plantPhotoList = getPlantPhotosRepository.getByPlantId(id);

        PlantPhoto lastPlantPhoto = plantPhotoList
                .stream()
                .max(Comparator.comparing(PlantPhoto::getUploadedAt))
                .orElseThrow();

        HealthAIDetection healthAIDetection = this.getHealthSuggestionsRepository.getHealthStatus(lastPlantPhoto.getUrl());

        healthAIDetection
                .getCandidates()
                .parallelStream()
                .map(disease -> {
                    DiseaseCandidate diseaseCandidate = this.getCandidateDiseasePlantsRepository.get(disease.toSlugFormat());
                    //TODO hacer logica para guardar si no está la planta

                    Integer newId = this.saveCandidateDiseasePlantsRepository.save(disease);
                    return disease.withId(newId);
                })
                .forEach(diseaseCandidate -> this.savePlantPhotoDiseaseRelationRepository.save(diseaseCandidate, lastPlantPhoto));

        return healthAIDetection;
    }

}
