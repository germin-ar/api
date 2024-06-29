package ar.germin.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Component
public class ApiConfiguration {

    private final GerminarConfiguration germinarConfiguration;

    @Autowired
    public ApiConfiguration(GerminarConfiguration germinarConfiguration) {
        this.germinarConfiguration = germinarConfiguration;
    }

    @Bean
    public RestTemplate getRestTemplate() throws NoSuchAlgorithmException, KeyManagementException {

        // Create SSL context to trust all certificates
        SSLContext sslContext = SSLContext.getInstance("TLS");

        // Define trust managers to accept all certificates
        TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
            // Method to check client's trust - accepting all certificates
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
            }

            // Method to check server's trust - accepting all certificates
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
            }

            // Method to get accepted issuers - returning an empty array
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        // Initialize SSL context with the defined trust managers
        sslContext.init(null, trustManagers, null);

        // Disable SSL verification for RestTemplate

        // Set the default SSL socket factory to use the custom SSL context
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        // Set the default hostname verifier to allow all hostnames
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        // Create a RestTemplate with a custom request factory

        // Build RestTemplate with SimpleClientHttpRequestFactory
        RestTemplate restTemplate = new RestTemplateBuilder().requestFactory(SimpleClientHttpRequestFactory.class)
                .rootUri("https://trefle.io")
                .build();

        return restTemplate; // Return the configured RestTemplate
    }

    @Bean
    @Primary
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public CognitoIdentityProviderClient cognitoClient() {
        return CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_1) // Cambia a la región donde se encuentra tu User Pool
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(this.germinarConfiguration.aws().accessKeyId(),
                                this.germinarConfiguration.aws().secretKey())))
                .build();
    }
}
