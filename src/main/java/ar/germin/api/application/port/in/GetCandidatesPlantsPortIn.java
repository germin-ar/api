package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.AIDetection;
//import reactor.core.publisher.Mono;

public interface GetCandidatesPlantsPortIn {
    AIDetection get(String id);
}
