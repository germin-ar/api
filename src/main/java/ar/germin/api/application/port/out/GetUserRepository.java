package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.User;

import java.util.Optional;

public interface GetUserRepository {
  Optional<User> get(String email);
}
