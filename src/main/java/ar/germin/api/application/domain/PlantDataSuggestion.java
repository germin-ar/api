package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlantDataSuggestion {
    String commonName;
    String scientificName;
    String image;
}
