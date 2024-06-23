package ar.germin.api.application.usecase;

import ar.germin.api.application.exceptions.HashCalculatedExeption;
import ar.germin.api.application.port.in.UserLoginPortIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class UserLoginUseCase implements UserLoginPortIn {

  @Value("${cognito.clientId}")
  private String clientId;

  @Value("${cognito.clientSecret}")
  private String clientSecret;

  @Value("${cognito.userPoolId}")
  private String userPoolId;

  private final CognitoIdentityProviderClient cognitoClient;

  public UserLoginUseCase(CognitoIdentityProviderClient cognitoClient) {
    this.cognitoClient = cognitoClient;
  }

  @Override
  public Map<String, String> login(String username, String password) {
    //interactuar con cognito
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

      Map<String, String> tokens = new HashMap<>();
      tokens.put("idToken", authResult.idToken());
      tokens.put("accessToken", authResult.accessToken());
      tokens.put("refreshToken", authResult.refreshToken());
      //TODO:cambiarle el rol cuando cambie la suscripcion
      //TODO:hacer el servicio para cambios de roles
      //roleManagementService.assignUserRole(username, ROLE.FREE_USER.name());



      return tokens;
    } catch (CognitoIdentityProviderException e) {
      log.error("Login failed: {}", e.awsErrorDetails().errorMessage(), e);
      throw new RuntimeException("Login failed: " + e.awsErrorDetails().errorMessage(), e);
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
