package ar.germin.api.application.exceptions;

public class UserNotFoundException   extends ApplicationException {
  public UserNotFoundException(String message) {
    super(message);
  }

}
