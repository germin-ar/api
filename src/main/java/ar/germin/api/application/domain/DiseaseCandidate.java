package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DiseaseCandidate {
    String id;
    String name;
    Double score;
}
