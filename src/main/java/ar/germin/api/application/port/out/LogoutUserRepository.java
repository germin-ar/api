package ar.germin.api.application.port.out;

public interface LogoutUserRepository {
  void logout(String accessToken);
}
