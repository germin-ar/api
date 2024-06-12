package ar.germin.api.adapter.jdbc.models;


import ar.germin.api.application.domain.Plant;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

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
    String pcFamily;
    String pcGenus;
    String pcIrrigation;
    String pcDescription;

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
                .pcFamily(pcFamily)
                .pcGenus(pcGenus)
                .pcIrrigation(pcIrrigation)
                .pcDescription(pcDescription)
                .build();
    }
}