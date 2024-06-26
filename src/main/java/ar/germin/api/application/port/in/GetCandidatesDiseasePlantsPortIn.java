package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.HealthAIDetection;

public interface GetCandidatesDiseasePlantsPortIn {
    HealthAIDetection get(Integer id);
}
