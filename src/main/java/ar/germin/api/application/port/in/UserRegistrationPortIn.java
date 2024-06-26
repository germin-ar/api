package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.User;

public interface UserRegistrationPortIn {
  User signUp(String username, String password, String email) throws Exception;
}
