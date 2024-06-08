package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.TreflePlantSearchResponseModel;
import ar.germin.api.application.port.out.GetPlantDataRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URLEncoder;

@Slf4j
@Component
public class TrefleRestAdapter implements GetPlantDataRepository {
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

}
