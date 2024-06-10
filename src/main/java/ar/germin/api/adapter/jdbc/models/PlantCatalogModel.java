package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.PlantCatalog;
import lombok.Data;

@Data
public class PlantCatalogModel {
    private Integer id;
    private String scientificName;
    private String description;

    public PlantCatalog toDomain() {
        return PlantCatalog.builder()
                .id(id)
                .description(description)
                .scientificName(scientificName)
                .build();
    }
}
