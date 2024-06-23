package ar.germin.api.application.port.in;

import java.util.Map;

public interface UserLoginPortIn {
  Map<String,String> login(String username,String password);
}
