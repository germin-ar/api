package ar.germin.api.application.adapter.rest.models;

import ar.germin.api.adapter.rest.models.nominatim.AddressModel;
import ar.germin.api.adapter.rest.models.nominatim.NominatimReverseResponseModel;
import ar.germin.api.application.domain.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NominatimReverseResponseModelTest {

    @Test
    void nominatimReverseResponseModelToDomain() {
        NominatimReverseResponseModel responseModel = NominatimReverseResponseModel.builder()
                .address(AddressModel.builder()
                        .country("Argentina")
                        .countryCode("AR")
                        .build())
                .build();

        Address address = responseModel.toDomain();

        Assertions.assertEquals("Argentina", address.getCountry());
        Assertions.assertEquals("AR", address.getCountryCode());
    }
}
