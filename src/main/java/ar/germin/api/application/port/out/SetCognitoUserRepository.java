package ar.germin.api.application.port.out;

import ar.germin.api.adapter.controller.models.LogoutResponse;

public interface SetCognitoUserRepository {
  LogoutResponse logout(String accessToken);
}
