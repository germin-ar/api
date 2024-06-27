package ar.germin.api.application.port.out;

public interface ConfirmUserRepository {
  void confirm(String username, String confirmationCode);
}
