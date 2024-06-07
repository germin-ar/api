package ar.germin.api.application.exceptions;

import org.springframework.web.ErrorResponseException;

public class ErrorPlantSaveException extends ApplicationException {
    public ErrorPlantSaveException(String message) {
        super(message);
    }
}
