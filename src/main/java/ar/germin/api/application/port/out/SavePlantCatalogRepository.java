package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.PlantCatalog;

public interface SavePlantCatalogRepository {
   Integer save(PlantCatalog plantCatalog);
}
