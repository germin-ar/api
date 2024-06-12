package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.PlantDataSuggestion;
import lombok.Builder;

import java.util.List;

public interface GetPlantsSuggestionPortIn {
    List<PlantDataSuggestion> getPlantsSuggestions(Query query);

    @Builder
    record Query(Float latitude, Float longitude, Integer sunExposure, Integer squareMeters, Integer page) {
    }

}
