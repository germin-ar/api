package ar.germin.api.adapter.cognito;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.CognitoSignUpException;
import ar.germin.api.application.exceptions.HashCalculatedExeption;
import ar.germin.api.application.exceptions.UserNotFoundException;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import ar.germin.api.application.port.out.UpdateCognitoUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("cognito")
@Slf4j
public class UserCognitoAdapter implements GetUserRepository, SaveUserRepository, UpdateCognitoUserRepository {
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

  @Override
  public Optional<User> get(String email) {
    try {
      ListUsersRequest listUsersRequest = ListUsersRequest.builder()
              .userPoolId(userPoolId)
              .filter("email=\"" + email + "\"")
              .build();
      ListUsersResponse listUsersResponse = cognitoClient.listUsers(listUsersRequest);
      List<UserType> users = listUsersResponse.users();

      if (users.isEmpty()) {
        return Optional.empty();
      }

      UserType cognitoUser = users.get(0);

      return Optional.ofNullable(User.builder()
              .username(cognitoUser.username())
              .email(cognitoUser.attributes()
                      .stream()
                      .filter(attr -> attr.name().equals("email"))
                      .findFirst()
                      .map(AttributeType::value)
                      .orElse(null)
              )
              .build());
    } catch (CognitoIdentityProviderException ex) {
      log.error("Error getting user from Cognito", ex);
      throw new UserNotFoundException();
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
  public String update(String username, String confirmationCode) {
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
      return "Confirmacion exitosa";
    } catch (CognitoIdentityProviderException ex) {
      return "Confirmation failed: " + ex.awsErrorDetails().errorMessage();
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

}
