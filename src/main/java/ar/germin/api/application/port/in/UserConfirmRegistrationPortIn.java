package ar.germin.api.application.port.in;

public interface UserConfirmRegistrationPortIn {
  String confirmSignUp(String email, String confirmationCode);
}
