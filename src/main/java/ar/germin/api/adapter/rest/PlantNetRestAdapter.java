package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.PlantNetResponseModel;
import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PlantNetRestAdapter implements GetAIDetectionRepository {
    private final GerminarConfiguration germinarConfiguration;
    private final WebClient webClient;

    public PlantNetRestAdapter(GerminarConfiguration germinarConfiguration,
                               WebClient.Builder webClientBuilder) {
        this.germinarConfiguration = germinarConfiguration;
        this.webClient = webClientBuilder
                .baseUrl("https://my-api.plantnet.org")
                .build();
    }

    @Override
    public Mono<AIDetection> getByFileImage(FileImage fileImage) {
        try {
            return this.webClient
                    .get()
                    .uri(uriBuilder ->
                            uriBuilder
                                    .path("/v2/identify/all")
                                    .queryParam("images", fileImage.getFilePath())
                                    .queryParam("api-key", germinarConfiguration.integrations().plantnet().apiKey())
                                    .queryParam("lang", "es")
                                    .build())
                    .retrieve()
                    .bodyToMono(PlantNetResponseModel.class)
                    .map(response -> {
                        log.info("Response: {}", response);

                        return response.toDomain(fileImage.getId());
                    });
        } catch (RuntimeException ex) {
            log.error("Error getting candidates", ex);
            throw ex;
        }
    }
}
