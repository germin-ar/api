package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.User;

public interface SaveUserRepository {
  void save(User user);
}
