package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.PlantNetResponseModel;
import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class PlantNetRestAdapter implements GetAIDetectionRepository {
    private final GerminarConfiguration germinarConfiguration;
    private final RestClient restClient;

    public PlantNetRestAdapter(GerminarConfiguration germinarConfiguration) {
        this.germinarConfiguration = germinarConfiguration;
        this.restClient = RestClient.builder().baseUrl("https://my-api.plantnet.org").build();
    }

    @Override
    public AIDetection getByFileImage(FileImage fileImage) {
        try {
            return this.restClient.get().uri(uriBuilder ->
                            uriBuilder
                                    .path("/v2/identify/all")
                                    .queryParam("images", fileImage.getFilePath())
                                    .queryParam("api-key", germinarConfiguration.integrations().plantnet().apiKey())
                                    .queryParam("lang", "es")
                                    .build())
                    .retrieve()
                    .body(PlantNetResponseModel.class)
                    .toDomain(fileImage.getId());
        } catch (RuntimeException ex) {
            log.error("Error getting candidates", ex);
            throw ex;
        }
    }
}
