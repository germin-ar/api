package ar.germin.api.application.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
@EqualsAndHashCode
@AllArgsConstructor
public class Plant {
    Integer id;
    String alias;
    LocalDateTime creationDate;
    LocalDateTime modificationDate;
    LocalDateTime plantingDate;
    // TODO ver list -> List<FileImage> images;
    Boolean isFavorite;
    Double height;
    //TODO enum  ALTO BAJO MEDIO
    //TODO frecuencia de riego
    // String sunExposure;
    String notes;
    Integer idGarden;
    String description;
    Integer idUser;


    //
    String pcFamilyname;
    String pcGenus;
    String pcWateringFrecuency;
    String pcDescription;


    String pcCommonName;
    String pcScientificName;
    @With
    List<PlantPhoto> photos;

    String pcSunExposure;

    String gardenName;
    @With
    List<PlantHistory> history;
}
