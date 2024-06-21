package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.cropkindwise.CropKindwiseCheckHealthRequestModel;
import ar.germin.api.adapter.rest.models.cropkindwise.CropKindwiseCheckHealthResponseModel;
import ar.germin.api.adapter.rest.utils.RestUtils;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.HealthAIDetection;
import ar.germin.api.application.port.out.GetHealthSuggestionsRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
@Qualifier("cropkindwise")
public class CropKindwiseRestAdapter implements GetHealthSuggestionsRepository {
    private final RestClient restClient;
    private final GerminarConfiguration germinarConfiguration;

    @Autowired
    public CropKindwiseRestAdapter(GerminarConfiguration germinarConfiguration) {
        this.germinarConfiguration = germinarConfiguration;
        this.restClient = RestClient.builder()
                .baseUrl("https://crop.kindwise.com")
                .build();
    }

    public HealthAIDetection getHealthStatus(FileImage fileImage) {
        String base64 = this.getBase64(fileImage.getFilePath());

        CropKindwiseCheckHealthRequestModel requestModel = CropKindwiseCheckHealthRequestModel.builder()
                .images(List.of(base64))
                .latitude(-35.8275455f)
                .longitude(-58.6186088f)
                .similarImages(true)
                .build();

        log.info("body for call crop.kindwise: {}", requestModel);

        CropKindwiseCheckHealthResponseModel responseModel = this.restClient
                .post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/api/v1/identification")
                                .queryParam("language", "es")
                                .queryParam("details", "type,common_names,eppo_code,wiki_url,taxonomy")
                                .build())
                .header("Api-Key", this.germinarConfiguration.integrations().cropKindwise().apiKey())
                .body(requestModel)
                .retrieve()
                .body(CropKindwiseCheckHealthResponseModel.class);
        log.info("Response from crop.kindwise: {}", responseModel);

        return responseModel.toDomain();


    }

    private String getBase64(String imageUrl) {
        try {
            return RestUtils.fileUrlToBase64(imageUrl);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
