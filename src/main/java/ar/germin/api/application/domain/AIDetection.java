package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class AIDetection {
    String id;
    String language;
    List<Candidate> candidates;
}
