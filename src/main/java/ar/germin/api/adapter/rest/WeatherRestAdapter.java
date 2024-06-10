package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.WeatherResponseModel;
import ar.germin.api.application.domain.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class WeatherRestAdapter {

    private final RestClient restClient;

    public WeatherRestAdapter() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.weatherapi.com")
                .build();
    }

    public Weather getWeather(Float latitude, Float longitude) {
        return this.restClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("/v1/current.json")
                                .queryParam("key", "")
                                .queryParam("q", String.format("%f,%f", latitude, longitude))
                                .build())
                .retrieve()
                .body(WeatherResponseModel.class)
                .toDomain();
    }
}
