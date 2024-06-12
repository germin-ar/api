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
    String familyName;
    Double maxSize;
    String fertilizer;
    String sunlight;
    String wateringFrequency;
    String pruning;
    String soil;
    String insecticide;
    String tips;
    Double temperatureMax;
    Double temperatureMin;
    String growthSeason;
}
