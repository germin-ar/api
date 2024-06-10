package ar.germin.api.application.exceptions;

public class PlantCatalogNotFoundException extends ApplicationException {

    public PlantCatalogNotFoundException() {
        super("PlantCatalog not found");
    }
}
