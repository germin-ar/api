package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class HealthAIDetection {
    List<DiseaseCandidate> candidates;
    Boolean isHealthy;
}
