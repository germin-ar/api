package ar.germin.api.application.usecase;


import ar.germin.api.application.domain.HistoricWeather;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.port.out.*;

import ar.germin.api.application.usecase.service.GetPlantCatalogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetPlantCatalogUseCaseTest {

    private final GetWeatherRepository mockGetWeatherRepository = mock(GetWeatherRepository.class);
    private final GetPlantCatalogRepository mockGetPlantCatalogRepository = mock(GetPlantCatalogRepository.class);
    private final GetPlantDetailDataRepository mockGetPlantDetailDataRepository = mock(GetPlantDetailDataRepository.class);
    private final SavePlantCatalogRepository mockSavePlantCatalogRepository = mock(SavePlantCatalogRepository.class);


    private final GetPlantCatalogService getPlantCatalogService = new GetPlantCatalogService(
            mockGetPlantCatalogRepository,
            mockGetPlantDetailDataRepository,
            mockSavePlantCatalogRepository
    );

    private final GetPlantCatalogUseCase getPlantCatalogUseCase = new GetPlantCatalogUseCase(
            getPlantCatalogService,
            mockGetWeatherRepository,
            mockGetPlantCatalogRepository
    );


    @Test
    void testGetPlantCatalog() {

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


        PlantCatalog actualCatalog = getPlantCatalogUseCase.getPlantCatalog(scientificName);


        assertEquals(expectedCatalog, actualCatalog);
    }


    @Test
    void testGetPlantsCatalogPlace_Success() {

        List<PlantCatalog> mockCatalogs = new ArrayList<>();

        PlantCatalog plantCatalog = PlantCatalog.builder()
                .id(1)
                .scientificName("rosa")
                .description("Rosaceae")
                .build();

        mockCatalogs.add(plantCatalog);
        when(mockGetPlantCatalogRepository.getPlantsCatalogsPlace("sol pleno", 2500, "Verano"))
                .thenReturn(mockCatalogs);


        List<PlantCatalog> result = getPlantCatalogUseCase.getPlantsCatalogPlace("sol-pleno", "Verano", "jardin");


        assertEquals(1, result.size());
    }

    @Test
    void testGetPlantsCatalog_Success() throws Exception {
        // Arrange
        Float latitude = 40.7128f;  // New York latitude
        Float longitude = -74.0060f;  // New York longitude
        Integer sunExposure = 7;  // Full sun exposure
        Integer squareMeters = 100;  // Assuming 100 square meters

        HistoricWeather historicWeather = HistoricWeather.builder()
                .avgHumidity(15)
                .minTemperature(25.00)
                .maxTemperature(40.00)
                .totalprecipMm(22.00)
                .build();


        when(mockGetWeatherRepository.getWeather(latitude, longitude))
                .thenReturn(historicWeather);


        Method getSunLightMethod = GetPlantCatalogUseCase.class.getDeclaredMethod("getSunLight", Integer.class);
        getSunLightMethod.setAccessible(true);
        String luz = (String) getSunLightMethod.invoke(getPlantCatalogUseCase, sunExposure);
        System.out.println("SunLight: " + luz); // Should print "sol pleno"

        Method getTemporadaMethod = GetPlantCatalogUseCase.class.getDeclaredMethod("getTemporada", Float.class);
        getTemporadaMethod.setAccessible(true);
        String temporada = (String) getTemporadaMethod.invoke(getPlantCatalogUseCase, latitude);
        System.out.println("Temporada: " + temporada); // Should print "Verano"


        PlantCatalog plantCatalog = PlantCatalog.builder()
                .id(1)
                .scientificName("rosa")
                .description("Rosaceae")
                .build();

        List<PlantCatalog> mockCatalogs = new ArrayList<>();
        mockCatalogs.add(plantCatalog);

        when(mockGetPlantCatalogRepository.getPlantsCatalogs(25.00, 40.00, luz, temporada, 100))
                .thenReturn(mockCatalogs);


        List<PlantCatalog> result = getPlantCatalogUseCase.getPlantsCatalog(latitude, longitude, sunExposure, squareMeters);


        assertEquals(1, result.size());  // Verifica que se espere un solo elemento en la lista
        assertEquals("rosa", result.get(0).getScientificName());  // Verifica que el nombre científico coincida
    }


    @Test
    void testGetSunLight_Sombra() throws Exception {

        Integer sunExposure = 2;
        Method method = GetPlantCatalogUseCase.class.getDeclaredMethod("getSunLight", Integer.class);
        method.setAccessible(true);


        String result = (String) method.invoke(getPlantCatalogUseCase, sunExposure);


        assertEquals("sombra", result);
    }

    @Test
    void testGetSunLight_SolParcial() throws Exception {

        Integer sunExposure = 5;
        Method method = GetPlantCatalogUseCase.class.getDeclaredMethod("getSunLight", Integer.class);
        method.setAccessible(true);


        String result = (String) method.invoke(getPlantCatalogUseCase, sunExposure);


        assertEquals("sol parcial", result);
    }

    @Test
    void testGetSunLight_SolPleno() throws Exception {

        Integer sunExposure = 8;
        Method method = GetPlantCatalogUseCase.class.getDeclaredMethod("getSunLight", Integer.class);
        method.setAccessible(true);


        String result = (String) method.invoke(getPlantCatalogUseCase, sunExposure);


        assertEquals("sol pleno", result);
    }

    @Test
    void testGetSunLight_OutOfRange() throws Exception {

        Integer sunExposure = 11;
        Method method = GetPlantCatalogUseCase.class.getDeclaredMethod("getSunLight", Integer.class);
        method.setAccessible(true);


        String result = (String) method.invoke(getPlantCatalogUseCase, sunExposure);


        assertEquals("valor fuera de rango", result);
    }

    @Test
    void testGetTemporada_Verano() throws Exception {

        Float latitude = -10.0f;
        Method method = GetPlantCatalogUseCase.class.getDeclaredMethod("getTemporada", Float.class);
        method.setAccessible(true);


        String result = (String) method.invoke(getPlantCatalogUseCase, latitude);


        assertEquals("Invierno", result);
    }



    @Test
    void testGetLightConvert() throws Exception {

        String luz = "mas-4hs-sol";
        Method method = GetPlantCatalogUseCase.class.getDeclaredMethod("getLightConvert", String.class);
        method.setAccessible(true);


        String result = (String) method.invoke(getPlantCatalogUseCase, luz);


        assertEquals("sol parcial", result);
    }

    @Test
    void testGetPlaceConvertInt() throws Exception {

        String espacio = "comedor";
        Method method = GetPlantCatalogUseCase.class.getDeclaredMethod("getPlaceConvertInt", String.class);
        method.setAccessible(true);


        Integer result = (Integer) method.invoke(getPlantCatalogUseCase, espacio);


        assertEquals(40, result);
    }




}

