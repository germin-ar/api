package ar.germin.api.application.exceptions;

public class UserIsNotConfirmedException extends ApplicationException {
  public UserIsNotConfirmedException(String message) {
    super(message);
  }
}

