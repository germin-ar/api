package ar.germin.api.application.port.out;

public interface UpdateCognitoUserRepository {
  String update(String username, String confirmationCode);
}
