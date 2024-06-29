package ar.germin.api.application.usecase.service;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.exceptions.FileImageAlreadyExistsException;
import ar.germin.api.application.port.out.SaveFileRepository;
import ar.germin.api.application.port.out.SavePlantPhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SavePlantPhotoService {

    private final SavePlantPhotoRepository savePlantPhotoRepository;
    private final SaveFileRepository  saveFileRepository;
    @Autowired
    public SavePlantPhotoService(SavePlantPhotoRepository savePlantPhotoRepository, SaveFileRepository saveFileRepository) {
        this.savePlantPhotoRepository = savePlantPhotoRepository;
        this.saveFileRepository = saveFileRepository;
    }

    @Transactional
    public FileImage savePhoto(FileImage fileImage, Integer idPlant){
        Integer plant = this.savePlantPhotoRepository.save(
                SavePlantPhotoRepository.Params.builder()
                        .idPlant(idPlant)
                        .url(fileImage.getFilePath())
                        .build());

        FileImage fileImageResult = this.saveImageHistory(fileImage, idPlant);

        log.info("para la planta con id {} se guardó la foto con path {} en el plantphoto con id {}", idPlant, fileImage.getFilePath(), plant);

        return fileImageResult;
    }

    private FileImage saveImageHistory(FileImage fileImage, Integer idPlant) {
        try {
            return this.saveFileRepository.saveHistory(fileImage, idPlant);
        }catch (FileImageAlreadyExistsException ex){
            log.info("Image already exists, continues without erros");
            return fileImage;
        }
    }
}
