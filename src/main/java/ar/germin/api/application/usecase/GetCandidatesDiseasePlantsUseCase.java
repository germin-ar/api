package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.HealthAIDetection;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.port.in.GetCandidatesDiseasePlantsPortIn;
import ar.germin.api.application.port.out.GetHealthSuggestionsRepository;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.SaveCandidateDiseasePlantsRepository;
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

    @Autowired
    public GetCandidatesDiseasePlantsUseCase(GetPlantPhotosRepository getPlantPhotosRepository,
                                             @Qualifier("cropkindwise") GetHealthSuggestionsRepository getHealthSuggestionsRepository,
                                             SaveCandidateDiseasePlantsRepository saveCandidateDiseasePlantsRepository) {
        this.getPlantPhotosRepository = getPlantPhotosRepository;
        this.getHealthSuggestionsRepository = getHealthSuggestionsRepository;
        this.saveCandidateDiseasePlantsRepository = saveCandidateDiseasePlantsRepository;
    }

    @Override
    public HealthAIDetection get(Integer id) {
        List<PlantPhoto> plantPhotoList = getPlantPhotosRepository.getByPlantId(id);

        String lastImageUrl = plantPhotoList
                .stream()
                .max(Comparator.comparing(PlantPhoto::getUploadedAt))
                .map(PlantPhoto::getUrl)
                .orElseThrow();

        HealthAIDetection healthAIDetection = this.getHealthSuggestionsRepository.getHealthStatus(lastImageUrl);

        // TOOD: se pueden guardar candidatos repetidos
        healthAIDetection
                .getCandidates()
                .parallelStream()
                .forEach(this.saveCandidateDiseasePlantsRepository::save);

        return healthAIDetection;
    }

}
