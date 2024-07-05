package ar.germin.api.application.port.out;

import java.util.List;

public interface GetRoleRepository {
  List<String> listUserGroups(String username);
}
