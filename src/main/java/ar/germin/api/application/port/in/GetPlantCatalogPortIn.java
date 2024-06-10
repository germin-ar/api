package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.PlantCatalog;

public interface GetPlantCatalogPortIn {
    PlantCatalog getPlantCatalog(String scientificName);

}
