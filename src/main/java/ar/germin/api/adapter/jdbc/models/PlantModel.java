package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.adapter.controller.models.PlantResponseModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import lombok.Data;


import java.time.LocalDateTime;


@Data
public class PlantModel {
    private Integer id;
    private String alias;
    private Integer idGarden;
    private LocalDateTime modificationDate;
    private Boolean isFavorite;
    private Double height;
    private String notes;
    private LocalDateTime plantingDate;

    public Plant toDomain(){
        return Plant.builder()
                .id(id)
                .alias(alias)
                .idGarden(idGarden)
                .modificationDate(modificationDate)
                .isFavorite(isFavorite)
                .height(height)
                .notes(notes)
                .plantingDate(plantingDate)
                .build();
    }
}
