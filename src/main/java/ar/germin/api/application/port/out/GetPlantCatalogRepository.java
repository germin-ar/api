package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.PlantCatalog;

public interface GetPlantCatalogRepository {

    PlantCatalog getPlantCatalog(String scientificName);
}
