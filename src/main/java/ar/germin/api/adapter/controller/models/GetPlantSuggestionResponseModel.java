package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.PlantDataSuggestion;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetPlantSuggestionResponseModel {
    private String commonName;
    private String scientificName;
    private String imageUrl;

    public static GetPlantSuggestionResponseModel fromDomain(PlantDataSuggestion plantDataSuggestion) {
        return GetPlantSuggestionResponseModel.builder()
                .commonName(plantDataSuggestion.getCommonName())
                .scientificName(plantDataSuggestion.getScientificName())
                .imageUrl(plantDataSuggestion.getImage())
                .build();
    }

    public static List<GetPlantSuggestionResponseModel> fromDomain(List<PlantDataSuggestion> plantDataSuggestion) {
        return plantDataSuggestion
                .stream()
                .map(GetPlantSuggestionResponseModel::fromDomain)
                .toList();
    }
}
