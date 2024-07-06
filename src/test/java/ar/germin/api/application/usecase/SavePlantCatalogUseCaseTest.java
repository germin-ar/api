package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.port.out.SavePlantCatalogRepository;

import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

public class SavePlantCatalogUseCaseTest {

    @Test
    void testSavePlantCatalog_Success() {

        SavePlantCatalogRepository mockSavePlantCatalogRepository = mock(SavePlantCatalogRepository.class);
        SavePlantCatalogUseCase savePlantCatalogUseCase = new SavePlantCatalogUseCase(mockSavePlantCatalogRepository);

        PlantCatalog mockPlantCatalog = PlantCatalog.builder()
                .id(1)
                .commonName("Rose")
                .scientificName("Rosa")
                .description("A type of flowering shrub.")
                .build();


        savePlantCatalogUseCase.save(mockPlantCatalog);


        verify(mockSavePlantCatalogRepository, times(1)).save(mockPlantCatalog);
    }

    @Test
    void testSavePlantCatalog_NullPlantCatalog() {

        SavePlantCatalogRepository mockSavePlantCatalogRepository = mock(SavePlantCatalogRepository.class);
        SavePlantCatalogUseCase savePlantCatalogUseCase = new SavePlantCatalogUseCase(mockSavePlantCatalogRepository);


        savePlantCatalogUseCase.save(null);


        verify(mockSavePlantCatalogRepository, times(1)).save(null);
    }
}
