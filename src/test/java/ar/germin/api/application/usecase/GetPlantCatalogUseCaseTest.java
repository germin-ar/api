package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.usecase.service.GetPlantCatalogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

class GetPlantCatalogUseCaseTest {

    @Test
    void testGetPlantCatalog() {
        // Crear el mock manualmente
        GetPlantCatalogService getPlantCatalogService = Mockito.mock(GetPlantCatalogService.class);

        // Crear la instancia de GetPlantCatalogUseCase manualmente
        GetPlantCatalogUseCase getPlantCatalogUseCase = new GetPlantCatalogUseCase(getPlantCatalogService);

        // Arrange
        String scientificName = "TestScientificName";
        PlantCatalog expectedCatalog = PlantCatalog.builder()
                .id(1)
                .scientificName(scientificName)
                .description("Description")
                .slug("slug")
                .genus("Genus")
                .familyName("FamilyName")
                .maxSize(2.5)
                .fertilizer("Fertilizer")
                .sunlight("Sunlight")
                .wateringFrequency("WateringFrequency")
                .pruning("Pruning")
                .soil("Soil")
                .insecticide("Insecticide")
                .tips("Tips")
                .temperatureMax(30.0)
                .temperatureMin(10.0)
                .growthSeason("GrowthSeason")
                .harvestTime("HarvestTime")
                .plantingTime("PlantingTime")
                .build();

        Mockito.when(getPlantCatalogService.getPlantCatalog(anyString())).thenReturn(expectedCatalog);

        // Act
        PlantCatalog actualCatalog = getPlantCatalogUseCase.getPlantCatalog(scientificName);

        // Assert
        assertEquals(expectedCatalog, actualCatalog);
    }
}
