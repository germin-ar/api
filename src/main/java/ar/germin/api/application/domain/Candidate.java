package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Builder
@Value
public class Candidate {
    @With
    String id;
    Float score;
    Specie specie;
    Boolean selectedByUser;
}
