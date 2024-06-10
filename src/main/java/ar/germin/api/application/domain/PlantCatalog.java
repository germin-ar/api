package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PlantCatalog {
    Integer id;
    String scientificName;
    String description;

}
