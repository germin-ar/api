package ar.germin.api.application.usecase.service;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.SavePlantPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
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

    @Autowired
    public SavePlantService(SavePlantRepository savePlantRepository,
                            SavePlantPhotoRepository savePlantPhotoRepository) {
        this.savePlantRepository = savePlantRepository;
        this.savePlantPhotoRepository = savePlantPhotoRepository;
    }

    @Transactional
    public Integer savePlant(SavePlantPortIn.Params params) {
        Integer idPlant = this.savePlantRepository.save(SavePlantRepository.Params.builder()
                .idGarden(params.getIdGarden())
                .idUser(params.getIdUser())
                .alias(params.getAlias())
                .height(params.getHeight())
                .plantingDate(params.getPlantingDate())
                .build());

        Integer idPlantPhoto = this.savePlantPhotoRepository.save(
                SavePlantPhotoRepository.Params.builder()
                        .idPlant(idPlant)
                        .url(params.getImageUrl())
                        .build());

        log.info("para la planta con id {} se guardó la foto con id {}", idPlant, idPlantPhoto);

        return idPlant;
    }
}
