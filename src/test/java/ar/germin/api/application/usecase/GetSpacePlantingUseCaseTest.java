package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.SpacePlanting;
import ar.germin.api.application.port.out.GetSpacePlantingRepository;
import ar.germin.api.application.port.out.UploadFileRepository;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetSpacePlantingUseCaseTest {

    @Test
    void testGetSpacePlanting_Success() {

        byte[] mockImageBytes = "mock image bytes".getBytes();
        FileImage mockFileImage = FileImage.builder()
                .id("fileId")
                .filePath("filePath")
                .isPublic(true)
                .checksum("checksum")
                .build();

        List<String> namesList = new ArrayList<>();
        namesList.add("jardin");

        SpacePlanting mockSpacePlanting = SpacePlanting.builder()
                .names(namesList)
                .build();

        UploadFileRepository mockUploadFileRepository = mock(UploadFileRepository.class);
        GetSpacePlantingRepository mockGetSpacePlantingRepository = mock(GetSpacePlantingRepository.class);

        when(mockUploadFileRepository.upload(mockImageBytes)).thenReturn(mockFileImage);
        when(mockGetSpacePlantingRepository.getSpacePlanting(mockFileImage)).thenReturn(mockSpacePlanting);

        GetSpacePlantingUseCase getSpacePlantingUseCase = new GetSpacePlantingUseCase(
                mockUploadFileRepository,
                mockGetSpacePlantingRepository
        );


        SpacePlanting result = getSpacePlantingUseCase.get(mockImageBytes);


        assertEquals(mockSpacePlanting, result);

        verify(mockUploadFileRepository).upload(mockImageBytes);
        verify(mockGetSpacePlantingRepository).getSpacePlanting(mockFileImage);
    }

    @Test
    void testGetSpacePlanting_NoMatchingNames() {

        byte[] mockImageBytes = "mock image bytes".getBytes();
        FileImage mockFileImage = FileImage.builder()
                .id("fileId")
                .filePath("filePath")
                .isPublic(true)
                .checksum("checksum")
                .build();


        SpacePlanting mockSpacePlanting = SpacePlanting.builder()
                .names(Collections.emptyList())
                .build();


        UploadFileRepository mockUploadFileRepository = mock(UploadFileRepository.class);
        GetSpacePlantingRepository mockGetSpacePlantingRepository = mock(GetSpacePlantingRepository.class);

        when(mockUploadFileRepository.upload(mockImageBytes)).thenReturn(mockFileImage);
        when(mockGetSpacePlantingRepository.getSpacePlanting(mockFileImage)).thenReturn(mockSpacePlanting);

        GetSpacePlantingUseCase getSpacePlantingUseCase = new GetSpacePlantingUseCase(
                mockUploadFileRepository,
                mockGetSpacePlantingRepository
        );


        SpacePlanting result = getSpacePlantingUseCase.get(mockImageBytes);


        assertEquals(mockSpacePlanting, result);

        verify(mockUploadFileRepository).upload(mockImageBytes);
        verify(mockGetSpacePlantingRepository).getSpacePlanting(mockFileImage);
    }
}
