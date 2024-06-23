package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.PlantHistory;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlantHistoryModel {
    Integer id;
    Integer idPlant;
    String notes;
    Double height;
    String alias;
    String urlImage;
    LocalDateTime modifiedAt;
    Integer idDiseases;

    public static List<PlantHistory> toDomainList(List<PlantHistoryModel> plantHistoryModels) {
        return plantHistoryModels
                .stream()
                .map(PlantHistoryModel::toDomain)
                .toList();
    }

    public PlantHistory toDomain() {
        return PlantHistory.builder()
                .idPlant(this.getIdPlant())
                .notes(this.getNotes())
                .height(this.getHeight())
                .alias(this.getAlias())
                .urlImage(this.getUrlImage())
                .modifiedAt(this.getModifiedAt())
                .idDiseases(this.getIdDiseases())
                .build();
    }
}
