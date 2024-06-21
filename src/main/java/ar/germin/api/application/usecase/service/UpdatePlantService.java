package ar.germin.api.application.usecase.service;

import ar.germin.api.application.port.in.UpdatePlantPortIn;
import ar.germin.api.application.port.out.SavePlantHistoryRepository;
import ar.germin.api.application.port.out.UpdatePlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UpdatePlantService {
    private final UpdatePlantRepository updatePlantRepository;
    private final SavePlantHistoryRepository savePlantHistoryRepository;

    public UpdatePlantService(UpdatePlantRepository updatePlantRepository, SavePlantHistoryRepository savePlantHistoryRepository) {
        this.updatePlantRepository = updatePlantRepository;
        this.savePlantHistoryRepository = savePlantHistoryRepository;
    }

    @Transactional
    public Integer updatePlant(UpdatePlantPortIn.Params params){

        Integer idPlant = this.updatePlantRepository.update(UpdatePlantRepository.Params.builder()
                        .id(params.getId())
                        .idGarden(params.getIdGarden())
                        .isFavorite(params.getIsFavorite())
                        .height(params.getHeight())
                        .alias(params.getAlias())
                        .notes(params.getNotes())
                        //.plantingDate(params.getPlantingDate())
                        .build());

        Integer idPlantHistory = this.savePlantHistoryRepository.save(
                SavePlantHistoryRepository.Params.builder()
                        .idPlant(idPlant)
                        .notes(params.getNotes())
                        .height(params.getHeight())
                        .alias(params.getAlias())
                        .urlImage("buildear")
                        .idDiseases(1)
                        .build());

        log.info("para la planta con id {} se guardó en el historial con id {}" , idPlant, idPlantHistory);

        return idPlant;
    }
}
