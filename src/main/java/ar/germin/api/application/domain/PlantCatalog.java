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
    String harvestTime;
    String plantingTime;


    String slugScientificName;
    String wateringCare;
    String commonName;
    String lifespan;
    String propagation;
    String fruit;
    String edible;
    String growthRate;
    String maintenance;
    String specie;
    String toxic;
    String repotting;
    String dormancy;
    Integer atmosphericHumidity;
    String plantType;
    Double width;

    String urlImage;
}
