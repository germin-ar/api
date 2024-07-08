package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.User;

public interface GetUserRepository {
  User getByHash(String hash);

  User get(String email);
}
