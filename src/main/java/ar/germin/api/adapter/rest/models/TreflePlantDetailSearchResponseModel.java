package ar.germin.api.adapter.rest.models;

import ar.germin.api.application.domain.PlantCatalog;
import lombok.Data;

import java.util.List;

@Data
public class TreflePlantDetailSearchResponseModel {
    Long id;
    String commonName;
    String scientificName;
    String slug;
    Long mainSpeciesID;
    String imageURL;
    Long genusID;
    String observations;
    Boolean vegetable;
    MainSpecies mainSpecies;
    Genus genus;
    Family family;
    List<Images> images;
    GrowthModel growth;

    public PlantCatalog toDomain() {
        return PlantCatalog.builder()
                .scientificName(scientificName)
                .slug(slug)
                .description(observations)
                .build();
    }
}

@Data
class FamilyDetailModel {
    private Long id;
    private String name;
    private String slug;
}

@Data
class GenusDetailModel {
    private Long id;
    private String name;
    private String slug;

}

@Data
class MainSpeciesModel {
    private Long id;
    private String commonName;
    private String slug;
    private String scientificName;
    private Long genusID;
    private String observations;
    private Long vegetable;
    private String imageURL;
    private List<String> duration;
    private List<String> ediblePart;
    private Boolean edible;
    private List<Images> images;
    private List<String> commonNames;
    private Specifications specifications;
    private Growth growth;
}

@Data
class GrowthModel {
    private Integer id;
    //    private Object description;
//    private Object sowing;
//    private Object daysToHarvest;
    private Double rowSpacing;
    private Double spread;
    private Double phMaximum;
    private Double phMinimum;
    private Long light;
    private Long atmosphericHumidity;
    private Double minimumPrecipitation;
    private Double maximumPrecipitation;
    private Double minimumRootDepth;
    private Long soilNutriments;
    private Long soilSalinity;
}

@Data
class ImagesModel {
    private Integer id;
    //flower - empty - fruit - bark - leaf - habit - other
    private String partName;
    private String url;
    //relacion con mainSpecies
    private Integer MainSpeciesId;
}

@Data
class SpecificationsModel {
    //private Object ligneousType;
    private String growthHabit;
    private Double averageHeight;
    private Double maximumHeight;

}

@Data
class SpeciesDetailModel {
    private Long id;
    private String commonName;
    private String slug;
    private String scientificName;
    private String familyCommonName;
    private Long genusID;
    private String imageURL;
    private String genus;
    private String family;
}

