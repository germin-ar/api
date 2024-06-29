package ar.germin.api.application.exceptions;

public class UserExistsException  extends ApplicationException {
  public UserExistsException(String message) {
    super(message);
  }
}
