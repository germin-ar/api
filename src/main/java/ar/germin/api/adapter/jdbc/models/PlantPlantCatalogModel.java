package ar.germin.api.adapter.jdbc.models;


import ar.germin.api.application.domain.Plant;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlantPlantCatalogModel {
    Integer plantId;
    String plantAlias;
    Integer plantIdGarden;
    LocalDateTime plantModificationDate;
    String plantDescription;
    Boolean plantFavorite;
    Double plantHeight;
    String plantNotes;
    LocalDateTime plantPlantingDate;
    String pcFamilyName;
    String pcGenus;
    String pcWateringFrecuency;
    String pcDescription;

    String pcCommonName;
    String pcScientificName;

    String pcSunlight;

    public Plant toDomain() {
        return Plant.builder()
                .id(plantId)
                .alias(plantAlias)
                .idGarden(plantIdGarden)
                .modificationDate(plantModificationDate)
                .description(plantDescription)
                .isFavorite(plantFavorite)
                .height(plantHeight)
                .notes(plantNotes)
                .plantingDate(plantPlantingDate)
                .pcCommonName(pcCommonName)
                .pcScientificName(pcScientificName)
                .pcFamilyname(pcFamilyName)
                .pcGenus(pcGenus)
                .pcWateringFrecuency(pcWateringFrecuency)
                .pcSunExposure(pcSunlight)
                .pcDescription(pcDescription)
                .build();
    }
}
