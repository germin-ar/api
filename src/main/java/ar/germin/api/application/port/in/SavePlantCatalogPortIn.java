package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.PlantCatalog;

public interface SavePlantCatalogPortIn {
    void save(PlantCatalog plantCatalog);
}
