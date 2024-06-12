package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.PlantPhoto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlantPhotoModel {
    Integer id;
    LocalDateTime uploadedAt;
    String url;
    Integer idPlant;

    public static List<PlantPhoto> toDomainList(List<PlantPhotoModel> plantPhotoModels) {
        return plantPhotoModels.stream().map(PlantPhotoModel::toDomain).toList();
    }

    public PlantPhoto toDomain() {
        return PlantPhoto.builder()
                .id(id)
                .uploadedAt(uploadedAt)
                .url(url)
                .idPlant(idPlant)
                .build();
    }
}
