package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.exceptions.FileImageAlreadyExistsException;
import ar.germin.api.application.port.out.SaveFileRepository;
import ar.germin.api.application.port.out.UploadFileRepository;
import ar.germin.api.application.usecase.service.SavePlantPhotoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SaveImageUseCaseTest {

    @Test
    void testSaveImage_Success() {

        byte[] mockImageBytes = "mock image bytes".getBytes();
        FileImage mockFileImage = FileImage.builder()
                .id("fileId")
                .filePath("filePath")
                .isPublic(true)
                .checksum("checksum")
                .build();

        UploadFileRepository mockUploadFileRepository = mock(UploadFileRepository.class);
        SaveFileRepository mockSaveFileRepository = mock(SaveFileRepository.class);
        SavePlantPhotoService mockSavePlantPhotoService = mock(SavePlantPhotoService.class);

        when(mockUploadFileRepository.upload(mockImageBytes)).thenReturn(mockFileImage);
        when(mockSaveFileRepository.save(mockFileImage)).thenReturn(mockFileImage);

        SaveImageUseCase saveImageUseCase = new SaveImageUseCase(
                mockUploadFileRepository,
                mockSaveFileRepository,
                mockSavePlantPhotoService
        );


        FileImage result = saveImageUseCase.save(mockImageBytes);


        assertEquals(mockFileImage, result);

        verify(mockUploadFileRepository).upload(mockImageBytes);
        verify(mockSaveFileRepository).save(mockFileImage);
    }

    @Test
    void testSaveHistory_Success() {

        byte[] mockImageBytes = "mock image bytes".getBytes();
        Integer mockIdPlant = 1;
        FileImage mockFileImage = FileImage.builder()
                .id("fileId")
                .filePath("filePath")
                .isPublic(true)
                .checksum("checksum")
                .build();

        UploadFileRepository mockUploadFileRepository = mock(UploadFileRepository.class);
        SavePlantPhotoService mockSavePlantPhotoService = mock(SavePlantPhotoService.class);
        SaveFileRepository mockSaveFileRepository = mock(SaveFileRepository.class);

        when(mockUploadFileRepository.upload(mockImageBytes)).thenReturn(mockFileImage);
        when(mockSavePlantPhotoService.savePhoto(mockFileImage, mockIdPlant)).thenReturn(mockFileImage);

        SaveImageUseCase saveImageUseCase = new SaveImageUseCase(
                mockUploadFileRepository,
                mockSaveFileRepository,
                mockSavePlantPhotoService
        );


        FileImage result = saveImageUseCase.saveHistory(mockImageBytes, mockIdPlant);


        assertEquals(mockFileImage, result);

        verify(mockUploadFileRepository).upload(mockImageBytes);
        verify(mockSavePlantPhotoService).savePhoto(mockFileImage, mockIdPlant);
    }

    @Test
    void testSaveImage_FileImageAlreadyExists() {

        byte[] mockImageBytes = "mock image bytes".getBytes();
        FileImage mockFileImage = FileImage.builder()
                .id("fileId")
                .filePath("filePath")
                .isPublic(true)
                .checksum("checksum")
                .build();

        UploadFileRepository mockUploadFileRepository = mock(UploadFileRepository.class);
        SaveFileRepository mockSaveFileRepository = mock(SaveFileRepository.class);
        SavePlantPhotoService mockSavePlantPhotoService = mock(SavePlantPhotoService.class);

        when(mockUploadFileRepository.upload(mockImageBytes)).thenReturn(mockFileImage);
        doThrow(new FileImageAlreadyExistsException("exception")).when(mockSaveFileRepository).save(mockFileImage);

        SaveImageUseCase saveImageUseCase = new SaveImageUseCase(
                mockUploadFileRepository,
                mockSaveFileRepository,
                mockSavePlantPhotoService
        );


        FileImage result = saveImageUseCase.save(mockImageBytes);


        assertEquals(mockFileImage, result);

        verify(mockUploadFileRepository).upload(mockImageBytes);
        verify(mockSaveFileRepository).save(mockFileImage);
    }
}
