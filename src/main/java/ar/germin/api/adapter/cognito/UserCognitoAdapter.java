package ar.germin.api.adapter.cognito;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.domain.UserSessionTokens;
import ar.germin.api.application.exceptions.CognitoSignUpException;
import ar.germin.api.application.exceptions.HashCalculatedExeption;
import ar.germin.api.application.exceptions.UserConfirmationException;
import ar.germin.api.application.exceptions.UserLogoutException;
import ar.germin.api.application.exceptions.UserNotFoundException;
import ar.germin.api.application.port.out.ConfirmUserRepository;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.LogoutUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import ar.germin.api.application.port.out.UserLoginRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Qualifier("cognito")
@Slf4j
public class UserCognitoAdapter implements GetUserRepository, SaveUserRepository, ConfirmUserRepository, LogoutUserRepository, UserLoginRepository {
    private final GerminarConfiguration germinarConfiguration;
    private final CognitoIdentityProviderClient cognitoClient;

    @Autowired
    public UserCognitoAdapter(GerminarConfiguration germinarConfiguration, CognitoIdentityProviderClient cognitoClient) {
        this.germinarConfiguration = germinarConfiguration;
        this.cognitoClient = cognitoClient;
    }

    @Override
    public User get(String email) throws UserNotFoundException{
        try {
            ListUsersRequest listUsersRequest = ListUsersRequest.builder()
                    .userPoolId(this.germinarConfiguration.cognito().userPoolId())
                    .filter("email=\"" + email + "\"")
                    .build();
            ListUsersResponse listUsersResponse = cognitoClient.listUsers(listUsersRequest);
            List<UserType> users = listUsersResponse.users();

            if (users.isEmpty()) {
                log.warn("No users found with email: " + email);
                throw new UserNotFoundException("There is no user from cognito registered with that email.");
            }

            UserType cognitoUser = users.get(0);
            boolean isConfirmed = cognitoUser.attributes()
                    .stream()
                    .filter(attr -> attr.name().equals("email_verified"))
                    .findFirst()
                    .map(AttributeType::value)
                    .map(Boolean::parseBoolean)
                    .orElse(false);

            String hashUser = cognitoUser.attributes()
                    .stream()
                    .filter(attributeType -> attributeType.name().equals("sub"))
                    .findFirst()
                    .map(AttributeType::value)
                    .orElseThrow(() -> new RuntimeException("Atributo 'sub' no encontrado"));


            return User.builder()
                    .username(cognitoUser.username())
                    .email(cognitoUser.attributes()
                            .stream()
                            .filter(attr -> attr.name().equals("email"))
                            .findFirst()
                            .map(AttributeType::value)
                            .orElse(null)
                    )
                    .hash(hashUser)
                    .rol(cognitoUser.enabled()?getUserRole(cognitoUser.username()):"")
                    .isConfirmed(isConfirmed)
                    .build();
        } catch (CognitoIdentityProviderException ex) {

            log.error("Error getting user from Cognito", ex);
            throw new UserNotFoundException("Error getting user from Cognito");
        }
    }


    @Override
    public void save(User user) {
        try {
            String secretHash = calculateSecretHash(user.getUsername());

            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .clientId(this.germinarConfiguration.cognito().clientId())
                    .username(user.getUsername())
                    .password(user.getPass())
                    .secretHash(secretHash)
                    .userAttributes(AttributeType.builder()
                            .name("email")
                            .value(user.getEmail())
                            .build())
                    .build();
            SignUpResponse signUpResult = cognitoClient.signUp(signUpRequest);
            log.info("Confirmation success:{}", signUpResult);
        } catch (CognitoIdentityProviderException ex) {
            throw new CognitoSignUpException("Failed to sign up user: " + ex.awsErrorDetails().errorMessage());
        }
    }

    @Override
    public void confirm(String username, String confirmationCode) {
        try {
            //deberia de tener un servicio que haga las validaciones
            //TODO:User user =  this.get()
            String secretHash = calculateSecretHash(username);

            ConfirmSignUpRequest confirmSignUpRequest = ConfirmSignUpRequest.builder()
                    .clientId(this.germinarConfiguration.cognito().clientId())
                    .username(username)
                    .confirmationCode(confirmationCode)
                    .secretHash(secretHash)
                    .build();

            ConfirmSignUpResponse confirmSignUpResponse = cognitoClient.confirmSignUp(confirmSignUpRequest);
            log.info("Confirmation code response: {}", confirmSignUpResponse);
        } catch (CognitoIdentityProviderException ex) {
            throw new UserConfirmationException("Confirmation failed");
        }
    }

    private String calculateSecretHash(String username) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.germinarConfiguration.cognito().clientSecret().getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            mac.update(username.getBytes());
            mac.update(this.germinarConfiguration.cognito().clientId().getBytes());
            byte[] rawHmac = mac.doFinal();
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new HashCalculatedExeption("Failed to calculate secret hash");
        }
    }

    @Override
    public void logout(String accessToken) {
        try {
            GlobalSignOutRequest signOutRequest = GlobalSignOutRequest.builder()
                    .accessToken(accessToken)
                    .build();

            GlobalSignOutResponse signOutResponse = cognitoClient.globalSignOut(signOutRequest);
            //log.info("signupResponse: {}",signOutResponse);

            log.info("Logout successful");

        } catch (CognitoIdentityProviderException e) {
            log.info("Logout failed: {}", e.awsErrorDetails().errorMessage());
            throw new UserLogoutException("Logout failed: Access Token has been revoked");
        }
    }

    @Override
    public UserSessionTokens login(String username, String password) {
        try {
            String secretHash = calculateSecretHash(username);

            Map<String, String> authParams = new HashMap<>();
            authParams.put("USERNAME", username);
            authParams.put("PASSWORD", password);
            authParams.put("SECRET_HASH", secretHash);

            InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                    .clientId(this.germinarConfiguration.cognito().clientId())
                    .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .authParameters(authParams)
                    .build();

            InitiateAuthResponse authResponse = cognitoClient.initiateAuth(authRequest);

            AuthenticationResultType authResult = authResponse.authenticationResult();
            if (authResult == null) {
                throw new RuntimeException("No authentication result returned");
            }
            return UserSessionTokens.builder()
                    .id(authResult.idToken())
                    .access(authResult.accessToken())
                    .refresh(authResult.refreshToken())
                    .build();
        } catch (CognitoIdentityProviderException e) {
            log.error("Login failed: {}", e.awsErrorDetails().errorMessage(), e);
            throw new RuntimeException("Login failed: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    private String getUserRole(String username) {

        AdminListGroupsForUserRequest listGroupsForUserRequest = AdminListGroupsForUserRequest.builder()
                .userPoolId(this.germinarConfiguration.cognito().userPoolId())
                .username(username)
                .build();
        AdminListGroupsForUserResponse listGroupsForUserResponse = cognitoClient.adminListGroupsForUser(listGroupsForUserRequest);
        List<GroupType> groups = listGroupsForUserResponse.groups();

        if (groups.isEmpty()) {
            return "";
            //throw new RuntimeException("User does not belong to any group");
        }
        // Asumiendo que el usuario tiene un solo rol
        return groups.get(0).groupName();
    }
}
