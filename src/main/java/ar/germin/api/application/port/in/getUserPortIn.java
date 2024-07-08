package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.User;

public interface getUserPortIn {
  User getByHash(String hash);
}
