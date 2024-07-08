package ar.germin.api.application.exceptions;

public class UserCognitoNotFoundException extends ApplicationException {
  public UserCognitoNotFoundException(String message) {
    super(message);
  }
}

