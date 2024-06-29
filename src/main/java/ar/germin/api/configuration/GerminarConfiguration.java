package ar.germin.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "germinar")
public record GerminarConfiguration(Integrations integrations, Cognito cognito, Aws aws) {

    public record Integrations(Perenual perenual,
                               Plantnet plantnet,
                               FreeImage freeImage,
                               Trefle trefle,
                               Weather weather,
                               PlantId plantId) {
    }

    public record Cognito(String clientId, String clientSecret, String userPoolId) {}

    public record Aws(String accessKeyId, String secretKey) {}

    public record Perenual(String apiKey) {
    }

    public record Plantnet(String apiKey) {
    }

    public record FreeImage(String apiKey) {
    }

    public record Trefle(String apiKey) {
    }

    public record Weather(String apiKey) {
    }

    public record PlantId(String apiKey) {
    }
}


