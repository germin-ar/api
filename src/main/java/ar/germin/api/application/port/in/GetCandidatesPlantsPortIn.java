package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.AIDetection;

public interface GetCandidatesPlantsPortIn {
    AIDetection get(String id);
}
