package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Genus {
    String scientificName;
    String scientificNameWithoutAuthor;
    String scientificNameAuthorship;
}
