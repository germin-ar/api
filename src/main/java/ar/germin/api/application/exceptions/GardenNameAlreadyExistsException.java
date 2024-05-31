package ar.germin.api.application.exceptions;

public class GardenNameAlreadyExistsException extends ApplicationException {
    public GardenNameAlreadyExistsException(String message) {
        super(message);
    }
}
