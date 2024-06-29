package ar.germin.api.adapter.cognito;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class CognitoConfig {

  @Value("${aws.accessKeyId}")
  private String accessKeyId;

  @Value("${aws.secretKey}")
  private String secretKey;


  @Bean
  public CognitoIdentityProviderClient cognitoClient() {
    return CognitoIdentityProviderClient.builder()
            .region(Region.US_EAST_1) // Cambia a la región donde se encuentra tu User Pool
            .credentialsProvider(StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKeyId, secretKey)))
            .build();
  }
}


