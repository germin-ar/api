package ar.germin.api.adapter.rest.models.nominatim;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddressModel {
    String city;
    String stateDistrict;
    String state;
    String postcode;
    String country;
    String countryCode;
}
