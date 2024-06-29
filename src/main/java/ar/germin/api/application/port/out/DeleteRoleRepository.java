package ar.germin.api.application.port.out;

public interface DeleteRoleRepository {
  void removeUserFromGroup(String username, String groupName);
}
