package ar.germin.api.adapter.jdbc;

import ar.germin.api.application.domain.Address;
import ar.germin.api.application.domain.HistoricWeather;
import ar.germin.api.application.domain.PlantDataSuggestion;
import ar.germin.api.application.port.out.GetPlantSuggestionRepository;

import java.util.List;

public class PlantSuggestionJdbcAdapter implements GetPlantSuggestionRepository {

    @Override
    public List<PlantDataSuggestion> getSuggestions(HistoricWeather weather, Address address, Integer sunExposure, Integer page) {
        ;return null;
    }
}
