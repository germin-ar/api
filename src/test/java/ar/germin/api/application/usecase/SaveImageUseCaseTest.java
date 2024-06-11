package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.exceptions.FileImageAlreadyExistsException;
import ar.germin.api.application.port.in.SaveImagePortIn;
import ar.germin.api.application.port.out.SaveFileRepository;
import ar.germin.api.application.port.out.UploadFileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaveImageUseCaseTest {

    private final UploadFileRepository uploadFileRepository = mock(UploadFileRepository.class);
    private final SaveFileRepository saveFileRepository = mock(SaveFileRepository.class);

    @Test
    void testSaveImageUploadsAndSavesImage() {
        byte[] image = new byte[]{1, 2, 3};
        FileImage uploadedFileImage = FileImage.builder().id("uploaded").build();
        FileImage savedFileImage = FileImage.builder().id("saved").build();

        when(uploadFileRepository.upload(image)).thenReturn(uploadedFileImage);
        when(saveFileRepository.save(uploadedFileImage)).thenReturn(savedFileImage);

        SaveImagePortIn useCase = new SaveImageUseCase(uploadFileRepository, saveFileRepository);

        FileImage result = useCase.save(image);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("saved", result.getId());
    }

    @Test
    void testSaveImageThrowsExceptionWhenFileAlreadyExists() {
        byte[] image = new byte[]{1, 2, 3};
        FileImage uploadedFileImage = FileImage.builder().id("uploaded").build();
        FileImage savedFileImage = FileImage.builder().id("saved").build();

        FileImageAlreadyExistsException ex = new FileImageAlreadyExistsException("Image already exists");

        when(uploadFileRepository.upload(image)).thenReturn(uploadedFileImage);
        when(saveFileRepository.save(uploadedFileImage)).thenReturn(savedFileImage);
        when(uploadFileRepository.upload(image)).thenThrow(ex);

        SaveImagePortIn useCase = new SaveImageUseCase(uploadFileRepository, saveFileRepository);

        Assertions.assertThrows(FileImageAlreadyExistsException.class, () -> {
            useCase.save(image);
        });
    }
}
