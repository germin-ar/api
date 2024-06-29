package ar.germin.api.application.port.out;

public interface DeleteUserRepository {
  void delete(String email);
}
