package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.ComputeVisionRequestModel;
import ar.germin.api.adapter.rest.models.computevision.ComputeVisionResponseModel;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.SpacePlanting;
import ar.germin.api.application.port.out.GetSpacePlantingRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class ComputeVisionRestAdapter implements GetSpacePlantingRepository {
    private final GerminarConfiguration germinarConfiguration;
    private final RestClient restClient;

    @Autowired
    public ComputeVisionRestAdapter(GerminarConfiguration germinarConfiguration) {
        this.germinarConfiguration = germinarConfiguration;
        this.restClient = RestClient.builder()
                .baseUrl(this.germinarConfiguration.computeVision().baseUrl())
                .build();
    }

    @Override
    public SpacePlanting getSpacePlanting(FileImage fileImage) {
        ComputeVisionResponseModel computeVisionResponseModel = restClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/vision/v2.1/analyze")
                        .queryParam("visualFeatures", "Categories,Description,Color")
                        .queryParam("language", "es")
                        .build()
                )
                .header("Ocp-Apim-Subscription-Key", germinarConfiguration.computeVision().subscriptionKey())
                .body(ComputeVisionRequestModel.builder().url(fileImage.getFilePath()).build())
                .retrieve()
                .body(ComputeVisionResponseModel.class);

        log.info("Response from compute vision for image [{}] -> {}", fileImage.getFilePath(), computeVisionResponseModel);
        return computeVisionResponseModel.toDomain();
    }
}
