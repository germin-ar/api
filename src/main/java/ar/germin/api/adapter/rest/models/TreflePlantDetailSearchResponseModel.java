package ar.germin.api.adapter.rest.models;

import lombok.Data;

import java.util.List;

import lombok.Data;
import java.util.List;

@Data
public class TreflePlantDetailSearchResponseModel {
  long id;
  String commonName;
  String scientificName;
  String slug;
  long mainSpeciesID;
  String imageURL;
  long genusID;
  String observations;
  boolean vegetable;
  MainSpecies mainSpecies;
  Genus genus;
  Family family;
  List<Images> Images;
}

@Data
class FamilyDetailModel {
  private long id;
  private String name;
  private String slug;
}

@Data
class GenusDetailModel {
  private long id;
  private String name;
  private String slug;

}

@Data
class MainSpeciesModel {
  private long id;
  private String commonName;
  private String slug;
  private String scientificName;
  private long genusID;
  private String observations;
  private boolean vegetable;
  private String imageURL;
  private Object duration;
  private Object ediblePart;
  private boolean edible;
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
  private double phMaximum;
  private double phMinimum;
  private long light;
  private long atmosphericHumidity;
  private Double minimumPrecipitation;
  private Double maximumPrecipitation;
  private Double minimumRootDepth;
  private long soilNutriments;
  private long soilSalinity;

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
  private long id;
  private String commonName;
  private String slug;
  private String scientificName;
  private String familyCommonName;
  private long genusID;
  private String imageURL;
  private String genus;
  private String family;
}

