package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.*;
import ar.germin.api.application.port.in.GetCandidatesPlantsPortIn;
import ar.germin.api.application.port.out.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Objects;

@Slf4j
@Component
public class GetCandidatesPlantsUseCase implements GetCandidatesPlantsPortIn {
    private final GetFileRepository getFileRepository;
    private final GetAIDetectionRepository getAIDetectionRepository;
    private final GetCandidateRepository getCandidateRepository;
    private final SaveCandidateRepository saveCandidateRepository;
    private final GetPlantDataRepository getPlantDataRepository;
    private final GetPlantCatalogRepository getPlantCatalogRepository;

    @Autowired
    public GetCandidatesPlantsUseCase(GetFileRepository getFileRepository,
                                      GetAIDetectionRepository getAIDetectionRepository,
                                      GetCandidateRepository getCandidateRepository,
                                      SaveCandidateRepository saveCandidateRepository,
                                      GetPlantDataRepository getPlantDataRepository, GetPlantCatalogRepository getPlantCatalogRepository) {
        this.getFileRepository = getFileRepository;
        this.getAIDetectionRepository = getAIDetectionRepository;
        this.getCandidateRepository = getCandidateRepository;
        this.saveCandidateRepository = saveCandidateRepository;
        this.getPlantDataRepository = getPlantDataRepository;
        this.getPlantCatalogRepository = getPlantCatalogRepository;
    }

    @Override
    public AIDetection get(String id) {
        FileImage fileImage = this.getFileRepository.getById(id);

        AIDetection aiDetection = this.getAIDetectionRepository.getByFileImage(fileImage);
        //consulta a la base
//        //todo deberia de extraer el nombre desde los candidatos.
        PlantCatalog plantFromDtabase = this.getPlantCatalogRepository.getPlant("Ocimum basilicum");
        log.info("plantCatalog from db:{}",plantFromDtabase);

        if(Objects.equals(plantFromDtabase.getScientificName(), "")){
            log.info("entra en el if deberia ir a buscar y luego guardar en las tablas");

         }
            aiDetection
                    .getCandidates()
                    .stream()
                    .max(Comparator.comparingDouble(Candidate::getScore))
                    .ifPresent(c -> this.getPlantDataRepository.search(c.getSpecie().getScientificNameWithoutAuthor()));


        return aiDetection;
    }



}
