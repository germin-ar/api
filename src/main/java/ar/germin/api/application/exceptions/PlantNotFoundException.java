package ar.germin.api.application.exceptions;

public class PlantNotFoundException extends ApplicationException{
    public PlantNotFoundException()  {
        super("Plant not found");
    }
}
