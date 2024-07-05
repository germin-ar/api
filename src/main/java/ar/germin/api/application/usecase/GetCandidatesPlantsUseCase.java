package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.HealthAIDetection;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.domain.Specie;
import ar.germin.api.application.exceptions.PlantCatalogNotFoundException;
import ar.germin.api.application.port.in.GetCandidatesPlantsPortIn;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.application.port.out.GetFileRepository;
import ar.germin.api.application.port.out.GetHealthSuggestionsRepository;
import ar.germin.api.application.port.out.GetPlantCatalogRepository;
import ar.germin.api.application.port.out.GetPlantDetailDataRepository;
import ar.germin.api.application.port.out.SavePlantCatalogRepository;
import ar.germin.api.application.usecase.service.GetPlantCatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
public class GetCandidatesPlantsUseCase implements GetCandidatesPlantsPortIn {
    private final GetFileRepository getFileRepository;
    private final GetAIDetectionRepository getAIDetectionRepository;
    private final GetPlantCatalogService getPlantCatalogService;

    @Autowired
    public GetCandidatesPlantsUseCase(GetFileRepository getFileRepository,
                                      @Qualifier("rest") GetAIDetectionRepository getAIDetectionRepository,
                                      GetPlantCatalogService getPlantCatalogService
    ) {
        this.getFileRepository = getFileRepository;
        this.getAIDetectionRepository = getAIDetectionRepository;
        this.getPlantCatalogService = getPlantCatalogService;
    }

    @Override
    public AIDetection get(String id) {
        FileImage fileImage = this.getFileRepository.getById(id);

        AIDetection aiDetection = this.getAIDetectionRepository.getByFileImage(fileImage);

        List<Candidate> candidates = aiDetection
                .getCandidates()
                .parallelStream()
                .map(candidate -> candidate.withPlantCatalog(this.getPlantCatalogService.getPlantCatalog(candidate.getSpecie().toSlugFormat())))
                .max(Comparator.comparingDouble(Candidate::getScore))
                .map(List::of)
                .orElseThrow();
        HealthAIDetection healthAIDetection = this.checkHealth(fileImage);

        AIDetection result = aiDetection
                .withCandidates(candidates)
                .withFileImage(fileImage)
                .withHealthAIDetection(healthAIDetection);

        log.info("Result: [{}]", result);

        return result;
    }



    private HealthAIDetection checkHealth(FileImage fileImage) {
        //HealthAIDetection healthAIDetection = this.getHealthSuggestionsRepository.getHealthStatus(fileImage);
        HealthAIDetection healthAIDetection = HealthAIDetection.builder()
                .candidates(Collections.emptyList())
                .isHealthy(true)
                .build();
        log.info("respuesta estado salud: {}", healthAIDetection);
        return healthAIDetection;
    }


}
