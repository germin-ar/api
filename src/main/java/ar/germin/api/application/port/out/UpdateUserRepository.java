package ar.germin.api.application.port.out;

public interface UpdateUserRepository {
  void update(String email);

  void changeRole(String email, String freeUser);
}
