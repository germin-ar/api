package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Address {
    String country;
    String countryCode;
}
