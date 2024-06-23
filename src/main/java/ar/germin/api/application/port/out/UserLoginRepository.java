package ar.germin.api.application.port.out;

import java.util.Map;

public interface UserLoginRepository {
  Map<String, String> login(String username, String password);
}
