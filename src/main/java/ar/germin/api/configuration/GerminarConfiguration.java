package ar.germin.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "germinar")
public record GerminarConfiguration(Integrations integrations) {

    public record Integrations(Perenual perenual, Plantnet plantnet, FreeImage freeImage, Trefle trefle) {
    }

    public record Perenual(String apiKey) {
    }

    public record Plantnet(String apiKey) {
    }

    public record FreeImage(String apiKey) {
    }

    public record Trefle(String apiKey) {
    }
}


