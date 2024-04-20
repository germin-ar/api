package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.exceptions.FileImageAlreadyExistsException;
import ar.germin.api.application.port.in.SaveImagePortIn;
import ar.germin.api.application.port.out.SaveFileRepository;
import ar.germin.api.application.port.out.UploadFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SaveImageUseCase implements SaveImagePortIn {
    private final UploadFileRepository uploadFileRepository;
    private final SaveFileRepository saveFileRepository;

    @Autowired
    public SaveImageUseCase(UploadFileRepository uploadFileRepository,
                            SaveFileRepository saveFileRepository) {
        this.uploadFileRepository = uploadFileRepository;
        this.saveFileRepository = saveFileRepository;
    }

    @Override
    public Mono<FileImage> save(byte[] image) {
        return this
                .uploadFileRepository
                .upload(image)
                .map(this::saveImage);
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
