package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.Garden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GardenModel {
    private Integer id;
    private String name;
    private Boolean isActive;

    public Garden toDomain() {
        return Garden.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static List<Garden> toDomainFromModelList(List<GardenModel> gardenModels) {
        return gardenModels.stream().map(GardenModel::toDomain).toList();
    }

    public static List<Garden> toDomainFromModelListGardens(List<GardenModel> gardenModels) {
        return gardenModels.stream()
                .map(GardenModel::toDomain)
                .toList();
    }
}
