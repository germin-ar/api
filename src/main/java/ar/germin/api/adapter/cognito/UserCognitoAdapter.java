package ar.germin.api.adapter.cognito;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.domain.UserSessionTokens;
import ar.germin.api.application.exceptions.UserNotFoundException;
import ar.germin.api.application.exceptions.*;
import ar.germin.api.application.port.out.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
  @Value("${cognito.clientId}")
  private String clientId;

  @Value("${cognito.clientSecret}")
  private String clientSecret;

  @Value("${cognito.userPoolId}")
  private String userPoolId;

  private final CognitoIdentityProviderClient cognitoClient;

  public UserCognitoAdapter(CognitoIdentityProviderClient cognitoClient) {
    this.cognitoClient = cognitoClient;
  }

  //TODO: replicar
  @Override
  public User get(String email) {
    try {
      ListUsersRequest listUsersRequest = ListUsersRequest.builder()
              .userPoolId(userPoolId)
              .filter("email=\"" + email + "\"")
              .build();
      ListUsersResponse listUsersResponse = cognitoClient.listUsers(listUsersRequest);
      List<UserType> users = listUsersResponse.users();

      if (users.isEmpty()) {
        throw new UserNotFoundException("There is no user registered with that email.");
      }

      UserType cognitoUser = users.get(0);

      return User.builder()
              .username(cognitoUser.username())
              .email(cognitoUser.attributes()
                      .stream()
                      .filter(attr -> attr.name().equals("email"))
                      .findFirst()
                      .map(AttributeType::value)
                      .orElse(null)
              )
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
              .clientId(clientId)
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
      String secretHash = calculateSecretHash(username);

      ConfirmSignUpRequest confirmSignUpRequest = ConfirmSignUpRequest.builder()
              .clientId(clientId)
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
      SecretKeySpec secretKeySpec = new SecretKeySpec(clientSecret.getBytes(), "HmacSHA256");
      mac.init(secretKeySpec);
      mac.update(username.getBytes());
      mac.update(clientId.getBytes());
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
              .clientId(clientId)
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
}
