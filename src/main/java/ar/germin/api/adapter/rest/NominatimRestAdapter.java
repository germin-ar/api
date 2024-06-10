package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.NominatimReverseResponseModel;
import ar.germin.api.application.domain.Address;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NominatimRestAdapter {

    private final RestClient restClient;

    public NominatimRestAdapter() {
        this.restClient = RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .build();
    }

    public Address getAddress(Float latitude, Float longitude) {
        return this.restClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("/reverse")
                                .queryParam("format", "json")
                                .queryParam("lat", latitude)
                                .queryParam("lon", longitude)
                                .build())
                .retrieve()
                .body(NominatimReverseResponseModel.class)
                .toDomain();
    }
}
