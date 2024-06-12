package ar.germin.api.adapter.rest.models;

import ar.germin.api.application.domain.PlantCatalog;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

//
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@Data
//public class TreflePlantResponseModel {
//    private DataModel1 data;
//}
//
//@Data
//class DataModel1 {
//    public int id;
//    public String common_name;
//    public String slug;
//    public String scientific_name;
//    public int main_species_id;
//    public String image_url;
//    public int year;
//}


//package ar.germin.api.adapter.rest.models;

//import ar.germin.api.application.domain.PlantCatalog;
//import lombok.Data;

//import java.util.List;

@Data
public class TreflePlantDetailSearchResponseModel {
    private DataModelDetail data;
    public PlantCatalog toDomain() {
        return PlantCatalog.builder()
                .scientificName(data.scientificName)
                .slug(data.slug)
                .light(data.light)
                .genus(data.genus.getName())
                //.averageSize(data.getSpecifications().getAverageHeight())
                .family(data.family.getName())
                .description(data.observations)
                .fertilizer("fertilizer")
                .irrigation("irrigation")
                .pruning("pruning")
                .soil("soil")
                .insecticide("insecticide")
                .tips("tips")
                .build();
    }
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
class DataModelDetail {
    Integer id;
    String commonName;
    String scientificName;
    String slug;
    Integer light;
    Integer mainSpeciesID;
    String imageURL;
    Integer genusID;
    String observations;
    Boolean vegetable;
    Specifications specifications;
    //MainSpecies mainSpecies;
    Genus genus;
    Family family;
    GrowthModel growth;


}

@Data
class FamilyDetailModel {
    private Integer id;
    private String name;
    private String slug;
}

@Data
class GenusDetailModel {
    private Integer id;
    private String name;
    private String slug;

}

@Data
class MainSpeciesModel {
    private Integer id;
    private String commonName;
    private String slug;
    private String scientificName;
    private Integer genusID;
    private String observations;
    private Integer vegetable;
    private String imageURL;
    private List<String> duration;
    private List<String> ediblePart;
    private Boolean edible;
    //private List<ImagesModel> images;
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
    private Integer light;
    private Integer atmosphericHumidity;
    private Double minimumPrecipitation;
    private Double maximumPrecipitation;
    private Double minimumRootDepth;
    private Integer soilNutriments;
    private Integer soilSalinity;
}
//
//@Data
//class ImagesModel {
//    private Integer id;
//    //flower - empty - fruit - bark - leaf - habit - other
//    private String partName;
//    private String url;
//    //relacion con mainSpecies
//    private Integer MainSpeciesId;
//}

@Data
class SpecificationsModel {
    //private Object ligneousType;
    private String growthHabit;
    private Double averageHeight;
    private Double maximumHeight;

}
@Builder
@Data
class SpeciesDetailModel {
    private Integer id;
    private String commonName;
    private String slug;
    private String scientificName;
    private String familyCommonName;
    private Integer genusID;
    private String imageURL;
    private String genus;
    private String family;
}





