package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.PlantCatalog;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlantCatalogModel {
    private Integer id;
    private String scientificName;
    private String description;
    private String slug;
    private Double maxSize;
    private String fertilizer;
    private String soil;
    private String sunlight;
    private Double temperatureMax;
    private Double temperatureMin;
    private String growthSeason;
    private String wateringFrequency;
    private String insecticide;
    private String tips;
    private String harvestTime;
    private String plantingTime;
    private String pruning;


    public PlantCatalog toDomain() {
        return PlantCatalog.builder()
                .id(id)
                .description(description)
                .scientificName(scientificName)
                .slug(slug)
                .maxSize(maxSize)
                .fertilizer(fertilizer)
                .soil(soil)
                .sunlight(sunlight)
                .temperatureMin(temperatureMin)
                .temperatureMax(temperatureMax)
                .growthSeason(growthSeason)
                .wateringFrequency(wateringFrequency)
                .tips(tips)
                .insecticide(insecticide)
                .growthSeason(growthSeason)
                .harvestTime(harvestTime)
                .plantingTime(plantingTime)
                .pruning(pruning)
                .build();
    }
}
