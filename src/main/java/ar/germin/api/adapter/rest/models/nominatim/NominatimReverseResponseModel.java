package ar.germin.api.adapter.rest.models.nominatim;

import ar.germin.api.application.domain.Address;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NominatimReverseResponseModel {
    Integer placeId;
    String lat;
    String lon;
    String type;
    Integer placeRank;
    Double importance;
    String addresstype;
    String name;
    String displayName;
    AddressModel address;

    public Address toDomain() {
        return Address.builder()
                .country(this.getAddress().getCountry())
                .countryCode(this.getAddress().getCountryCode())
                .build();
    }

}

