package ar.germin.api.application.port.out;

public interface SetRoleRepository {
  void set(String username, String groupName);
}
