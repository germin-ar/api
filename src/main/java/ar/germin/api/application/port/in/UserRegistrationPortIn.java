package ar.germin.api.application.port.in;

public interface UserRegistrationPortIn {
  String signUp(String username, String password, String email);
}
