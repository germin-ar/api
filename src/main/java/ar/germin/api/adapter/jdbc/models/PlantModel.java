package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.Plant;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlantModel {
    private Integer id;
    private String alias;
    private Integer idGarden;
    private LocalDateTime modificationDate;
    private LocalDateTime creationDate;
    private Boolean favorite;
    private Double height;
    private String notes;
    private LocalDateTime plantingDate;

    public static List<Plant> toDomainFromList(List<PlantModel> plantModels) {
        return plantModels
                .stream()
                .map(PlantModel::toDomain)
                .toList();
    }

    public Plant toDomain() {
        return Plant.builder()
                .id(id)
                .alias(alias)
                .idGarden(idGarden)
                .modificationDate(modificationDate)
                .creationDate(creationDate)
                .isFavorite(favorite)
                .height(height)
                .notes(notes)
                .plantingDate(plantingDate)
                .build();
    }
}
