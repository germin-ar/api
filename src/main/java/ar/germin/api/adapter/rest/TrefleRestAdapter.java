package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.trefle.TreflePlantDetailSearchResponseModel;
import ar.germin.api.adapter.rest.models.trefle.TreflePlantSearchResponseModel;
import ar.germin.api.adapter.rest.models.trefle.TrefleSuggestionsResponseModel;
import ar.germin.api.application.domain.Address;
import ar.germin.api.application.domain.HistoricWeather;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.domain.PlantDataSuggestion;
import ar.germin.api.application.port.out.GetPlantDataRepository;
import ar.germin.api.application.port.out.GetPlantDetailDataRepository;
import ar.germin.api.application.port.out.GetPlantSuggestionRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
public class TrefleRestAdapter implements GetPlantDataRepository, GetPlantDetailDataRepository, GetPlantSuggestionRepository {
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


    @Override
    public List<PlantDataSuggestion> getSuggestions(HistoricWeather historicWeather, Address address, Integer sunExposure, Integer page) {
        TrefleSuggestionsResponseModel responseModel = this.restClient
                .get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .path("/api/v1/plants")
                            .queryParam("token", this.germinarConfiguration.integrations().trefle().apiKey())
                            .queryParam("range[atmospheric_humidity]", historicWeather.getAvgHumidity() / 10)
                            .queryParam("range[minimum_precipitation]", historicWeather.getTotalprecipMm())
                            .queryParam("range[maximum_precipitation]", historicWeather.getTotalprecipMm())
                            .queryParam("range[minimum_temperature]", historicWeather.getMinTemperature())
                            .queryParam("range[maximum_temperature]", historicWeather.getMaxTemperature())
                            .queryParam("filter_not[light]", sunExposure)
                            .queryParam("region", address.getCountry())
                            .queryParam("page", page)
                            .build();

                    log.info("Calling trefleDetail with uri: {}", uri);
                    return uri;
                })
                .retrieve()
                .body(TrefleSuggestionsResponseModel.class);
        log.info("Response from Trefle: [{}]", responseModel);

        return responseModel.toDomain();
    }
}
