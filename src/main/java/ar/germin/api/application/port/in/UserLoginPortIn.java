package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.UserSessionTokens;

public interface UserLoginPortIn {
  UserSessionTokens login(String username, String password);
}
