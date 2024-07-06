package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Address;
import ar.germin.api.application.domain.HistoricWeather;
import ar.germin.api.application.domain.PlantDataSuggestion;
import ar.germin.api.application.port.out.GetGeoRepository;
import ar.germin.api.application.port.out.GetPlantSuggestionRepository;
import ar.germin.api.application.port.out.GetWeatherRepository;
import ar.germin.api.application.port.in.GetPlantsSuggestionPortIn.Query;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class GetPlantSuggestionsUseCaseTest {

    @Test
    void testGetPlantsSuggestions() {

        GetGeoRepository mockGetGeoRepository = mock(GetGeoRepository.class);
        GetWeatherRepository mockGetWeatherRepository = mock(GetWeatherRepository.class);
        GetPlantSuggestionRepository mockGetPlantSuggestionRepository = mock(GetPlantSuggestionRepository.class);

        GetPlantsSuggestionsUseCase getPlantsSuggestionsUseCase = new GetPlantsSuggestionsUseCase(
                mockGetGeoRepository,
                mockGetWeatherRepository,
                mockGetPlantSuggestionRepository
        );

        Float latitude = 40.7128f;
        Float longitude = -74.0060f;
        Integer sunExposure = 7;
        Integer page = 1;

        Query query = Query.builder()
                .latitude(latitude)
                .longitude(longitude)
                .sunExposure(sunExposure)
                .page(page)
                .build();

        Address mockAddress = Address.builder()
                .country("Country")
                .countryCode("Country Code")
                .build();

        HistoricWeather mockWeather = HistoricWeather.builder()
                .avgHumidity(15)
                .minTemperature(25.00)
                .maxTemperature(40.00)
                .totalprecipMm(22.00)
                .build();

        List<PlantDataSuggestion> mockSuggestions = new ArrayList<>();
        PlantDataSuggestion suggestion = PlantDataSuggestion.builder()
                .commonName("PlantName")
                .scientificName("ScientificName")
                .image("ImageURL")
                .build();
        mockSuggestions.add(suggestion);

        when(mockGetGeoRepository.getAddress(latitude, longitude)).thenReturn(mockAddress);
        when(mockGetWeatherRepository.getWeather(latitude, longitude)).thenReturn(mockWeather);
        when(mockGetPlantSuggestionRepository.getSuggestions(mockWeather, mockAddress, sunExposure, page))
                .thenReturn(mockSuggestions);


        List<PlantDataSuggestion> result = getPlantsSuggestionsUseCase.getPlantsSuggestions(query);


        assertEquals(1, result.size());
        assertEquals("PlantName", result.get(0).getCommonName());
        assertEquals("ScientificName", result.get(0).getScientificName());
        assertEquals("ImageURL", result.get(0).getImage());

        verify(mockGetGeoRepository).getAddress(latitude, longitude);
        verify(mockGetWeatherRepository).getWeather(latitude, longitude);
        verify(mockGetPlantSuggestionRepository).getSuggestions(mockWeather, mockAddress, sunExposure, page);
    }
}
