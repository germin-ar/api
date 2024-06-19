package ar.germin.api.application.usecase.service;

import ar.germin.api.application.port.in.SavePlantPortIn;
import ar.germin.api.application.port.out.SavePlantHistoryRepository;
import ar.germin.api.application.port.out.SavePlantPhotoRepository;
import ar.germin.api.application.port.out.SavePlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SavePlantService {
    private final SavePlantRepository savePlantRepository;
    private final SavePlantPhotoRepository savePlantPhotoRepository;
    private final SavePlantHistoryRepository savePlantHistoryRepository;

    @Autowired
    public SavePlantService(SavePlantRepository savePlantRepository,
                            SavePlantPhotoRepository savePlantPhotoRepository,
                            SavePlantHistoryRepository savePlantHistoryRepository) {
        this.savePlantRepository = savePlantRepository;
        this.savePlantPhotoRepository = savePlantPhotoRepository;
        this.savePlantHistoryRepository = savePlantHistoryRepository;
    }

    @Transactional
    public Integer savePlant(SavePlantPortIn.Params params) {
        Integer idPlant = this.savePlantRepository.save(SavePlantRepository.Params.builder()
                .idGarden(params.getIdGarden())
                .idUser(params.getIdUser())
                .notes(params.getNotes())
                .alias(params.getAlias())
                .height(params.getHeight())
                .plantingDate(params.getPlantingDate())
                .idPlantCatalog(params.getIdPlantCatalog())
                .build());

        Integer idPlantPhoto = this.savePlantPhotoRepository.save(
                SavePlantPhotoRepository.Params.builder()
                        .idPlant(idPlant)
                        .url(params.getImageUrl())
                        .build());

        Integer idPlantHistory = this.savePlantHistoryRepository.save(
                SavePlantHistoryRepository.Params.builder()
                        .idPlant(idPlant)
                        .notes(params.getNotes())
                        .height(params.getHeight())
                        .alias(params.getAlias())
                        .urlImage(params.getImageUrl())
                        .idDiseases(1)
                        .build());

        log.info("para la planta con id {} se guardó la foto con id {} en el historial con id {}", idPlant, idPlantPhoto, idPlantHistory);

        return idPlant;
    }
}
