package ar.germin.api.application.domain;

import lombok.Builder;

@Builder
public class Address {
    String country;
    String countryCode;
}
