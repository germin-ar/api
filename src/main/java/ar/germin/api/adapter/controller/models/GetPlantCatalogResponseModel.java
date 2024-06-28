package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.PlantCatalog;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetPlantCatalogResponseModel {
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

    public static GetPlantCatalogResponseModel fromDomain(PlantCatalog plantCatalog){
        return GetPlantCatalogResponseModel.builder()
                .id(plantCatalog.getId())
                .scientificName(plantCatalog.getScientificName())
                .description(plantCatalog.getDescription())
                .slugScientificName(plantCatalog.getSlugScientificName())
                .genus(plantCatalog.getGenus())
                .familyName(plantCatalog.getFamilyName())
                .maxSize(plantCatalog.getMaxSize())
                .fertilizer(plantCatalog.getFertilizer())
                .wateringFrecuency(plantCatalog.getWateringFrequency())
                .pruning(plantCatalog.getPruning())
                .soil(plantCatalog.getSoil())
                .insecticide(plantCatalog.getInsecticide())
                .tips(plantCatalog.getTips())
                .sunLight(plantCatalog.getSunlight())
                .wateringCare(plantCatalog.getWateringCare())
                .commonName(plantCatalog.getCommonName())
                .lifespan(plantCatalog.getLifespan())
                .propagation(plantCatalog.getPropagation())
                .fruit(plantCatalog.getFruit())
                .edible(plantCatalog.getEdible())
                .growthRate(plantCatalog.getGrowthRate())
                .maintenance(plantCatalog.getMaintenance())
                .temperatureMax(plantCatalog.getTemperatureMax())
                .temperatureMin(plantCatalog.getTemperatureMin())
                .specie(plantCatalog.getSpecie())
                .toxic(plantCatalog.getToxic())
                .repotting(plantCatalog.getRepotting())
                .dormancy(plantCatalog.getDormancy())
                .growthSeason(plantCatalog.getGrowthSeason())
                .atmosphericHumidity(plantCatalog.getAtmosphericHumidity())
                .plantingTime(plantCatalog.getPlantingTime())
                .harvestTime(plantCatalog.getHarvestTime())
                .plantType(plantCatalog.getPlantType())
                .width(plantCatalog.getWidth())
                .build();
    }


    public static List<GetPlantCatalogResponseModel> fromDomainList(List<PlantCatalog> plantCatalogs){
        return plantCatalogs
                .stream()
                .map(GetPlantCatalogResponseModel::fromDomain)
                .toList();
    }

}
