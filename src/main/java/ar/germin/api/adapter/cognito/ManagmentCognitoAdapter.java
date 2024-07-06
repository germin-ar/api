package ar.germin.api.adapter.cognito;

import ar.germin.api.application.port.out.DeleteRoleRepository;
import ar.germin.api.application.port.out.GetRoleRepository;
import ar.germin.api.application.port.out.SetRoleRepository;
import ar.germin.api.application.port.out.UpdateRoleRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminAddUserToGroupRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminAddUserToGroupResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminListGroupsForUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminListGroupsForUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminRemoveUserFromGroupRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminRemoveUserFromGroupResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GroupType;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ManagmentCognitoAdapter implements SetRoleRepository, GetRoleRepository, DeleteRoleRepository,
        UpdateRoleRepository {

    private final GerminarConfiguration germinarConfiguration;
    private final CognitoIdentityProviderClient cognitoClient;

    @Autowired
    public ManagmentCognitoAdapter(GerminarConfiguration germinarConfiguration,
                                   CognitoIdentityProviderClient cognitoClient) {
        this.germinarConfiguration = germinarConfiguration;
        this.cognitoClient = cognitoClient;
    }

    @Override
    public void set(String username, String groupName) {
        try {
            List<String> existingGroups = listUserGroups(username);

            // Si el usuario ya pertenece a algún grupo, elimínalo
            if (!existingGroups.isEmpty()) {
                for (String group : existingGroups) {
                    removeUserFromGroup(username, group);
                }
            }

            // Agrega al usuario al nuevo grupo
            addUserToGroup(username, groupName);

        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to assign role to user: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    @Override
    public List<String> listUserGroups(String username) {
        try {
            AdminListGroupsForUserRequest listGroupsRequest = AdminListGroupsForUserRequest.builder()
                    .userPoolId(this.germinarConfiguration.cognito().userPoolId())
                    .username(username)
                    .build();

            AdminListGroupsForUserResponse listGroupsResponse = cognitoClient.adminListGroupsForUser(listGroupsRequest);

            return listGroupsResponse.groups().stream()
                    .map(GroupType::groupName)
                    .collect(Collectors.toList());

        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to list user groups: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    @Override
    public void removeUserFromGroup(String username, String groupName) {
        try {
            AdminRemoveUserFromGroupRequest removeUserFromGroupRequest = AdminRemoveUserFromGroupRequest.builder()
                    .userPoolId(this.germinarConfiguration.cognito().userPoolId())
                    .username(username)
                    .groupName(groupName)
                    .build();

            AdminRemoveUserFromGroupResponse removeUserFromGroupResponse = cognitoClient.adminRemoveUserFromGroup(removeUserFromGroupRequest);

        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to remove user from group: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    @Override
    public void addUserToGroup(String username, String groupName) {
        try {
            AdminAddUserToGroupRequest addUserToGroupRequest = AdminAddUserToGroupRequest.builder()
                    .userPoolId(this.germinarConfiguration.cognito().userPoolId())
                    .username(username)
                    .groupName(groupName)
                    .build();

            AdminAddUserToGroupResponse addUserToGroupResponse = cognitoClient.adminAddUserToGroup(addUserToGroupRequest);
        } catch (CognitoIdentityProviderException e) {
            throw new RuntimeException("Failed to add user to group: " + e.awsErrorDetails().errorMessage(), e);
        }
    }
}
