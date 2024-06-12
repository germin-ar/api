package ar.germin.api.adapter.rest;

import ar.germin.api.adapter.rest.models.NominatimReverseResponseModel;
import ar.germin.api.application.domain.Address;
import ar.germin.api.application.port.out.GetGeoRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NominatimRestAdapter implements GetGeoRepository {

    private final RestClient restClient;

    public NominatimRestAdapter() {
        this.restClient = RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .build();
    }

    @Override
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
