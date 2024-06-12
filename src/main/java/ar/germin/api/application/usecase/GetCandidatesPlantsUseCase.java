package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.domain.Specie;
import ar.germin.api.application.exceptions.PlantCatalogNotFoundException;
import ar.germin.api.application.port.in.GetCandidatesPlantsPortIn;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.application.port.out.GetCandidateRepository;
import ar.germin.api.application.port.out.GetFileRepository;
import ar.germin.api.application.port.out.GetPlantCatalogRepository;
import ar.germin.api.application.port.out.GetPlantDetailDataRepository;
import ar.germin.api.application.port.out.SaveCandidateRepository;
import ar.germin.api.application.port.out.SavePlantCatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
public class GetCandidatesPlantsUseCase implements GetCandidatesPlantsPortIn {
    private final GetFileRepository getFileRepository;
    private final GetAIDetectionRepository getAIDetectionRepository;
    private final GetCandidateRepository getCandidateRepository;
    private final SaveCandidateRepository saveCandidateRepository;
    private final GetPlantCatalogRepository getPlantCatalogRepository;
    private final GetPlantDetailDataRepository getPlantDetailDataRepository;
    private final SavePlantCatalogRepository savePlantCatalogRepository;

    @Autowired
    public GetCandidatesPlantsUseCase(GetFileRepository getFileRepository,
                                      GetAIDetectionRepository getAIDetectionRepository,
                                      GetCandidateRepository getCandidateRepository,
                                      SaveCandidateRepository saveCandidateRepository,
                                      GetPlantCatalogRepository getPlantCatalogRepository,
                                      GetPlantDetailDataRepository getPlantDetailDataRepository,
                                      SavePlantCatalogRepository savePlantCatalogRepository
    ) {
        this.getFileRepository = getFileRepository;
        this.getAIDetectionRepository = getAIDetectionRepository;
        this.getCandidateRepository = getCandidateRepository;
        this.saveCandidateRepository = saveCandidateRepository;
        this.getPlantCatalogRepository = getPlantCatalogRepository;
        this.getPlantDetailDataRepository = getPlantDetailDataRepository;
        this.savePlantCatalogRepository = savePlantCatalogRepository;
    }

    @Override
    public AIDetection get(String id) {
        FileImage fileImage = this.getFileRepository.getById(id);

        AIDetection aiDetection = this.getAIDetectionRepository.getByFileImage(fileImage);

        List<Candidate> candidates = aiDetection
                .getCandidates()
                .stream()
                .map(candidate -> candidate.withPlantCatalog(getPlantCatalog(candidate.getSpecie())))
                .max(Comparator.comparingDouble(Candidate::getScore))
                .map(List::of)
                .orElseThrow();

        AIDetection result = aiDetection.withCandidates(candidates).withFileImage(fileImage);

        log.info("Result: [{}]", result);

        return result;
    }

    private PlantCatalog getPlantCatalog(Specie specie) {
        try {
            return this.getPlantCatalogRepository.getPlantCatalog(specie.toSlugFormat());
        } catch (PlantCatalogNotFoundException ex) {
            PlantCatalog plantCatalog = this.getPlantDetailDataRepository.searchDetail(specie.toSlugFormat());
            this.savePlantCatalogRepository.save(plantCatalog);
            return plantCatalog;
        }
    }

    private void checkHealth(Candidate candidate) {
        //llamada api plant.id

        //llamada api python
    }


}
