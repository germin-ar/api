package ar.germin.api.application.exceptions;

public class GardenNotFoundException extends ApplicationException {

    public GardenNotFoundException() {
        super("Garden not found");
    }
}
