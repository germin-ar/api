package ar.germin.api.application.port.in;

import ar.germin.api.adapter.controller.models.LogoutResponse;

public interface UserLogoutPortIn {
  LogoutResponse logout(String accessToken);
}
