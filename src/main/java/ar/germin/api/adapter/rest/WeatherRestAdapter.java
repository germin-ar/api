package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.WeatherResponseModel;
import ar.germin.api.application.domain.HistoricWeather;
import ar.germin.api.application.port.out.GetWeatherRepository;
import ar.germin.api.configuration.GerminarConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class WeatherRestAdapter implements GetWeatherRepository {
    private final GerminarConfiguration germinarConfiguration;
    private final RestClient restClient;

    @Autowired
    public WeatherRestAdapter(GerminarConfiguration germinarConfiguration) {
        this.germinarConfiguration = germinarConfiguration;
        this.restClient = RestClient.builder()
                .baseUrl("https://api.weatherapi.com")
                .build();
    }

    public HistoricWeather getWeather(Float latitude, Float longitude) {
        return this.restClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("/v1/history.json")
                                .queryParam("key", this.germinarConfiguration.integrations().weather().apiKey())
                                .queryParam("q", String.format("%f,%f", latitude, longitude))
                                .queryParam("dt", LocalDate.now().minusDays(299).format(DateTimeFormatter.ofPattern("YYYY-MM-dd")))
                                .build())
                .retrieve()
                .body(WeatherResponseModel.class)
                .toDomain();
    }
}
