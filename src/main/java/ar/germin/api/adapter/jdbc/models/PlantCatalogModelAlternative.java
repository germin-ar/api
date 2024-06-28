package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.PlantCatalog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantCatalogModelAlternative {
    Integer id;
    String scientificName;
    String description;
    String slugScientificName;
    String genus;
    String familyName;
    Double maxSize;
    String fertilizer;
    String wateringFrecuency;
    String pruning;
    String soil;
    String insecticide;
    String tips;
    String sunLight;
    String wateringCare;
    String commonName;
    String lifespan;
    String propagation;
    String fruit;
    String edible;
    String growthRate;
    String maintenance;
    Double temperatureMax;
    Double temperatureMin;
    String specie;
    String toxic;
    String repotting;
    String dormancy;
    String growthSeason;
    Integer atmosphericHumidity;
    String plantingTime;
    String harvestTime;
    String plantType;
    Double width;

    public static List<PlantCatalog> toDomainFromList(List<PlantCatalogModelAlternative> plantCatalogModelAlternative){
        return plantCatalogModelAlternative
                .stream()
                .map(PlantCatalogModelAlternative::toDomain)
                .toList();
    }

    public PlantCatalog toDomain(){
        return PlantCatalog.builder()
                .id(id)
                .scientificName(scientificName)
                .description(description)
                .slugScientificName(slugScientificName)
                .genus(genus)
                .familyName(familyName)
                .maxSize(maxSize)
                .fertilizer(fertilizer)
                .wateringFrequency(wateringCare)
                .pruning(pruning)
                .soil(soil)
                .insecticide(insecticide)
                .tips(tips)
                .sunlight(sunLight)
                .wateringCare(wateringCare)
                .commonName(commonName)
                .lifespan(lifespan)
                .propagation(propagation)
                .fruit(fruit)
                .edible(edible)
                .growthRate(growthRate)
                .maintenance(maintenance)
                .temperatureMax(temperatureMax)
                .temperatureMin(temperatureMin)
                .specie(specie)
                .toxic(toxic)
                .repotting(repotting)
                .dormancy(dormancy)
                .growthSeason(growthSeason)
                .atmosphericHumidity(atmosphericHumidity)
                .plantingTime(plantingTime)
                .harvestTime(harvestTime)
                .plantType(plantType)
                .width(width)
                .build();
    }
}
