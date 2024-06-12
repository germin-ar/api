package ar.germin.api.adapter.rest.models;

import ar.germin.api.application.domain.Address;
import lombok.Data;

@Data
public class NominatimReverseResponseModel {
    public Integer place_id;
    public String lat;
    public String lon;
    public String type;
    public Integer placeRank;
    public Double importance;
    public String addresstype;
    public String name;
    public String displayName;
    public AddressModel address;

    public Address toDomain() {
        return Address.builder()
                .country(this.getAddress().getCountry())
                .countryCode(this.getAddress().getCountryCode())
                .build();
    }

}

@Data
class AddressModel {
    public String city;
    public String stateDistrict;
    public String state;
    public String postcode;
    public String country;
    public String countryCode;
}
