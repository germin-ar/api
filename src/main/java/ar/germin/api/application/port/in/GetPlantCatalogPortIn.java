package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.PlantCatalog;

import java.util.List;

public interface GetPlantCatalogPortIn {
    PlantCatalog getPlantCatalog(String scientificName);
    List<PlantCatalog> getPlantsCatalog(Float latitude, Float longitude, Integer sunExposure, Integer squareMeters);

    List<PlantCatalog> getPlantsCatalogPlace(String luz, String temporada, String espacio);
}
