package ar.germin.api.application.exceptions;

public class PlantsNotFoundException extends RuntimeException {
    public PlantsNotFoundException(String message) {
        super(message);
    }
}