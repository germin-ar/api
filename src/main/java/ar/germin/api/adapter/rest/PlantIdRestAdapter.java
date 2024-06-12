package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.plantid.PlantIdCheckHealthRequestModel;
import ar.germin.api.adapter.rest.models.plantid.PlantIdCheckHealthResponseModel;
import ar.germin.api.adapter.rest.utils.RestUtils;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.HealthAIDetection;
import ar.germin.api.application.port.out.GetHealthSuggestionsRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
public class PlantIdRestAdapter implements GetHealthSuggestionsRepository {
    private final RestClient restClient;
    private final GerminarConfiguration germinarConfiguration;

    @Autowired
    public PlantIdRestAdapter(GerminarConfiguration germinarConfiguration) {
        this.germinarConfiguration = germinarConfiguration;
        this.restClient = RestClient.builder()
                .baseUrl("https://plant.id")
                .build();
    }

    public HealthAIDetection getHealthStatus(FileImage fileImage) {
        String base64 = this.getBase64(fileImage.getFilePath());

        PlantIdCheckHealthRequestModel plantIdCheckHealthRequestModel = PlantIdCheckHealthRequestModel.builder()
                .images(List.of(base64))
                .latitude(-35.8275455f)
                .longitude(-58.6186088f)
                .similarImages(true)
                .build();

        log.info("body for call plant.id: {}", plantIdCheckHealthRequestModel);
        PlantIdCheckHealthResponseModel response = this.restClient
                .post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/api/v3/health_assessment")
                                .build())
                .header("Api-Key", this.germinarConfiguration.integrations().plantId().apiKey())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(plantIdCheckHealthRequestModel)
                .retrieve()
                .body(PlantIdCheckHealthResponseModel.class);

        log.info("Response from plant.id: {}", response);

        return response.toDomain();
    }

    private String getBase64(String imageUrl) {
        try {
            return RestUtils.fileUrlToBase64(imageUrl);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
