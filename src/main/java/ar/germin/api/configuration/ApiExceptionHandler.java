package ar.germin.api.configuration;

import ar.germin.api.application.exceptions.ApplicationException;
import ar.germin.api.application.exceptions.DiseaseCandidateNotFoundException;
import ar.germin.api.application.exceptions.FileImageNotFoundException;
import ar.germin.api.application.exceptions.GardenNotFoundException;
import ar.germin.api.application.exceptions.PlantNotFoundException;
import ar.germin.api.application.exceptions.UserCognitoNotFoundException;
import ar.germin.api.application.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            PlantNotFoundException.class,
            UserNotFoundException.class,
            GardenNotFoundException.class,
            DiseaseCandidateNotFoundException.class,
            FileImageNotFoundException.class,
            UserCognitoNotFoundException.class,
    })
    public ProblemDetail handleNotFoundException(ApplicationException ex, ServerWebExchange exchange) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, ex, exchange);
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleDefault(RuntimeException ex, ServerWebExchange exchange) {
        return buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex, exchange);
    }

    private ProblemDetail buildProblemDetail(HttpStatus httpStatus,
                                             Exception ex,
                                             ServerWebExchange exchange) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setType(exchange.getRequest().getURI());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        return problemDetail;
    }
}
