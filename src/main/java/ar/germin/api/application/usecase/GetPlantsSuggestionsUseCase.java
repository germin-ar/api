package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Address;
import ar.germin.api.application.domain.HistoricWeather;
import ar.germin.api.application.domain.PlantDataSuggestion;
import ar.germin.api.application.port.in.GetPlantsSuggestionPortIn;
import ar.germin.api.application.port.out.GetGeoRepository;
import ar.germin.api.application.port.out.GetPlantSuggestionRepository;
import ar.germin.api.application.port.out.GetWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetPlantsSuggestionsUseCase implements GetPlantsSuggestionPortIn {
    private final GetGeoRepository getGeoRepository;
    private final GetWeatherRepository getWeatherRepository;
    private final GetPlantSuggestionRepository getPlantSuggestionRepository;

    @Autowired
    public GetPlantsSuggestionsUseCase(GetGeoRepository getGeoRepository,
                                       GetWeatherRepository getWeatherRepository,
                                       GetPlantSuggestionRepository getPlantSuggestionRepository) {
        this.getGeoRepository = getGeoRepository;
        this.getWeatherRepository = getWeatherRepository;
        this.getPlantSuggestionRepository = getPlantSuggestionRepository;
    }

    @Override
    public List<PlantDataSuggestion> getPlantsSuggestions(Query query) {
        Address address = this.getGeoRepository.getAddress(query.latitude(), query.longitude());
        HistoricWeather weather = this.getWeatherRepository.getWeather(query.latitude(), query.longitude());
        return this.getPlantSuggestionRepository.getSuggestions(weather, address, query.sunExposure(), query.page());
    }
}
