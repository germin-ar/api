package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Address;
import ar.germin.api.application.domain.PlantDataSuggestion;
import ar.germin.api.application.domain.HistoricWeather;

import java.util.List;

public interface GetPlantSuggestionRepository {
    List<PlantDataSuggestion> getSuggestions(HistoricWeather weather, Address address, Integer sunExposure, Integer page);
}
