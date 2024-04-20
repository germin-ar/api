package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Family {
    String scientificName;
    String scientificNameWithoutAuthor;
    String scientificNameAuthorship;
}
