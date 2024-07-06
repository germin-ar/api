package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.PlantCatalog;

import java.util.List;

public interface GetPlantCatalogRepository {

    PlantCatalog getPlantCatalog(String scientificName);
    List<PlantCatalog> getPlantsCatalogs(Double minTemperature, Double maxTemperature, String luz, String temporada, Integer squareCentimeters);

    List<PlantCatalog> getPlantsCatalogsPlace(String light, Integer place, String temporada);
}
