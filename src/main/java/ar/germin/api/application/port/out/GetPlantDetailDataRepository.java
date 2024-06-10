package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.PlantCatalog;

public interface GetPlantDetailDataRepository {
    PlantCatalog searchDetail(String scientificNameWithoutAuthor);
}
