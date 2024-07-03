package ar.germin.api.application.port.out;

public interface UpdateRoleRepository {
  void addUserToGroup(String username, String groupName);
}
