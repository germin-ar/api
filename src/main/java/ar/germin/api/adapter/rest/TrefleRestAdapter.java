package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.TreflePlantDetailSearchResponseModel;
import ar.germin.api.adapter.rest.models.TreflePlantSearchResponseModel;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.port.out.GetPlantDataRepository;
import ar.germin.api.application.port.out.GetPlantDetailDataRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Slf4j
@Component
public class TrefleRestAdapter implements GetPlantDataRepository, GetPlantDetailDataRepository {
    private final GerminarConfiguration germinarConfiguration;
    private final RestClient restClient;

    public TrefleRestAdapter(GerminarConfiguration germinarConfiguration) {
        this.germinarConfiguration = germinarConfiguration;
        this.restClient = RestClient.builder()
                .baseUrl("https://trefle.io")
                .build();
    }

    public void search(String scientificName) {
        try {
            TreflePlantSearchResponseModel result = this.restClient
                    .get()
                    .uri(uriBuilder -> {
                        URI uri = uriBuilder
                                .path("/api/v1/plants")
                                .queryParam("token", this.germinarConfiguration.integrations().trefle().apiKey())
                                .queryParam("filter[scientific_name]", scientificName.trim())
                                .build();

                        log.info("Calling trefle with uri: {}", uri);
                        return uri;
                    })
                    .retrieve()
                    .body(TreflePlantSearchResponseModel.class);

            log.info("repuesta trefle: {}", result);
        } catch (RuntimeException ex) {
            log.error("Error getting candidates", ex);
            throw ex;
        }
    }


    public PlantCatalog searchDetail(String scientificName) {
        try {
            TreflePlantDetailSearchResponseModel result = this.restClient
                    .get()
                    .uri(uriBuilder -> {
                        URI uri = uriBuilder
                                .path("/api/v1/plants")
                                .pathSegment(scientificName)
                                .queryParam("token", this.germinarConfiguration.integrations().trefle().apiKey())
                                .build();

                        log.info("Calling trefleDetail with uri: {}", uri);
                        return uri;
                    })
                    .retrieve()
                    .body(TreflePlantDetailSearchResponseModel.class);

            log.info("repuesta trefle: {}", result);

            return result.toDomain();
        } catch (RuntimeException ex) {
            log.error("Error getting candidates", ex);
            throw ex;
        }
    }


}
