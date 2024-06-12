package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.PlantCatalog;

public interface SavePlantCatalogRepository {
    void save(PlantCatalog plantCatalog);
}
