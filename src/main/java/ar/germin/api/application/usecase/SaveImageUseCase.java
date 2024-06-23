package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.exceptions.FileImageAlreadyExistsException;
import ar.germin.api.application.port.in.SaveImagePortIn;
import ar.germin.api.application.port.out.SaveFileRepository;
import ar.germin.api.application.port.out.SavePlantPhotoRepository;
import ar.germin.api.application.port.out.UploadFileRepository;
import ar.germin.api.application.usecase.service.SavePlantPhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaveImageUseCase implements SaveImagePortIn {
    private final UploadFileRepository uploadFileRepository;
    private final SaveFileRepository saveFileRepository;

    private final SavePlantPhotoService savePlantPhotoService;

    @Autowired
    public SaveImageUseCase(UploadFileRepository uploadFileRepository,
                            SaveFileRepository saveFileRepository,
                            SavePlantPhotoService savePlantPhotoService) {
        this.uploadFileRepository = uploadFileRepository;
        this.saveFileRepository = saveFileRepository;
        this.savePlantPhotoService = savePlantPhotoService;
    }

    @Override
    public FileImage save(byte[] image) {
        FileImage fileImage = this.uploadFileRepository.upload(image);

        return this.saveImage(fileImage);
    }

    @Override
    public FileImage saveHistory(byte[] image, Integer idPlant) {
        FileImage fileImage = this.uploadFileRepository.upload(image);

        return this.savePlantPhotoService.savePhoto(fileImage, idPlant);
    }



    private FileImage saveImage(FileImage fileImage) {
        try {
            return this
                    .saveFileRepository
                    .save(fileImage);
        } catch (FileImageAlreadyExistsException ex) {
            log.info("Image already exists, continues without errors");
            return fileImage;
        }
    }
}
