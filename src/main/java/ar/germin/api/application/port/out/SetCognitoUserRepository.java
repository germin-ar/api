package ar.germin.api.application.port.out;

public interface SetCognitoUserRepository {
  String logout(String accessToken);
}
