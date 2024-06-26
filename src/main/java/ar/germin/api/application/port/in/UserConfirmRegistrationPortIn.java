package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.User;

public interface UserConfirmRegistrationPortIn {
  User confirmSignUp(String email, String confirmationCode);
}
