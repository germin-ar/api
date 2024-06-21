package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.PlantHistory;
import lombok.Data;

import java.util.List;

@Data
public class PlantHistoryModel {
    Integer id;
    Integer idPlant;
    String notes;
    Double height;
    String alias;
    String urlImage;
    String modifiedAt;
    Integer idDiseases;

    public static List<PlantHistory> toDomainList(List<PlantHistoryModel> plantHistoryModels){
        return plantHistoryModels.stream().map(PlantHistoryModel::toDomain).toList();
    }

    public PlantHistory toDomain(){
        return PlantHistory.builder()
                .idPlant(idPlant)
                .notes(notes)
                .height(height)
                .alias(alias)
                .url_image(urlImage)
                .modified_at(modifiedAt)
                .idDiseases(idDiseases)
                .build();
    }
}
