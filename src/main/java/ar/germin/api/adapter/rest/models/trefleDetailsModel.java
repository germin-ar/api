package ar.germin.api.adapter.rest.models;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
class TrefleDetailModel {
  private long id;
  private String commonName;
  private String slug;
  private String scientificName;
  private long mainSpeciesID;
  private String imageURL;
  //private Object familyCommonName;
  private long genusID;
  private String observations;
  private boolean vegetable;
  private MainSpecies mainSpecies;
  private Genus genus;
  private Family family;
  private List<Images> Images;
}
@Builder
@Data
class Family {
  private long id;
  private String name;
  //private Object commonName;
  private String slug;
  //private FamilyLinks links;
}
@Builder
@Data
class Genus {
  private long id;
  private String name;
  private String slug;
  //private GenusLinks links;

}

//@Data
//class GenusLinks {
//    private String self;
//    private String plants;
//    private String species;
//    private String family;
//
//}
//
//
//@Data
//class DataLinks {
//    private String self;
//    private String species;
//    private String genus;
//
//}
@Builder
@Data
class MainSpecies {
  private long id;
  private String commonName;
  private String slug;
  private String scientificName;
  //private Object familyCommonName;
  private long genusID;
  private String observations;
  private boolean vegetable;
  private String imageURL;
  //    private String genus;
//    private String family;
  private Object duration;
  private Object ediblePart;
  private boolean edible;
  private List<Images> images;
  private List<String> commonNames;
  //private Distribution distribution;
  //private Distributions distributions;
  //private Flower flower;
  //private Foliage foliage;
  //private FruitOrSeed fruitOrSeed;
  //private Source[] sources;
  private Specifications specifications;
  // private Synonym[] synonyms;
  private Growth growth;
  //private MainSpeciesLinks links;


}

//@Data
//class Distribution {
//    private String[] distributionNative;
//    private String[] introduced;
//
//}
//
//@Data
//class Distributions {
//    private Introduced[] distributionsNative;
//    private Introduced[] introduced;
//
//}

//@Data
//class Introduced {
//    private long id;
//    private String name;
//    private String slug;
//    private String tdwgCode;
//    private long tdwgLevel;
//    private long speciesCount;
//    private GenusLinks links;
//
//
//
//}


//@Data
//public class Flower {
//    private String[] color;
//    private boolean conspicuous;
//
//
//}


//@Data
//class Foliage {
//    private Object texture;
//    private Object color;
//    private Object leafRetention;
//
//
//}
//
//@Data
//class FruitOrSeed {
//    private Object conspicuous;
//    private Object color;
//    private Object shape;
//    private Object seedPersistence;
//
//}

@Builder
@Data
class Growth {
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
  //    private Object growthMonths;
//    private Object bloomMonths;
//    private Object fruitMonths;
  private Double minimumPrecipitation;
  private Double maximumPrecipitation;
  private Double minimumRootDepth;
  //    private ImumTemperature minimumTemperature;
//    private ImumTemperature maximumTemperature;
  private long soilNutriments;
  private long soilSalinity;
//    private Object soilTexture;
//    private Object soilHumidity;

}


//@Data
//class ImumPrecipitation {
//    private Object mm;
//
//    public Object getMm() { return mm; }
//    public void setMm(Object value) { this.mm = value; }
//}

//@Data
//class ImumTemperature {
//    private long degF;
//    private long degC;
//
//}

//
//class MinimumRootDepth {
//    private Double cm;
//
//}
@Builder
@Data
class Images {
  private Integer id;
  //flower - empty - fruit - bark - leaf - habit - other
  private String partName;

  private String url;
  //relacion con mainSpecies
  //private Integer MainSpeciesId;


}
@Builder
@Data
class Specifications {
  private Object ligneousType;
  //    private Object growthForm;
  private String growthHabit;
  //    private Object growthRate;
  private Double averageHeight;
  private Double maximumHeight;
//    private Object nitrogenFixation;
//    private Object shapeAndOrientation;
//    private Object toxicity;

}

@Data
class Species {
  private long id;
  private String commonName;
  private String slug;
  private String scientificName;
  private String familyCommonName;
  private long genusID;
  private String imageURL;
  //    private String[] synonyms;
  private String genus;
  private String family;
//    private MainSpeciesLinks links;

}


