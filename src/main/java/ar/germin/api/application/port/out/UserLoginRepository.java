package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.UserSessionTokens;

public interface UserLoginRepository {
  UserSessionTokens login(String username, String password);
}
