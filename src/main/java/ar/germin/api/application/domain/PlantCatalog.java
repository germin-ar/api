package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PlantCatalog {
    Integer id;
    String scientificName;
    String description;
    String slug;
    String genus;
    String family;
    Double averageSize;
    String fertilizer;
    Integer light;
    String irrigation;
    String pruning;
    String soil;
    String insecticide;
    String tips;

}
