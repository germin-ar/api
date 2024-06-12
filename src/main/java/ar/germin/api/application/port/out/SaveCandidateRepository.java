package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Candidate;

public interface SaveCandidateRepository {
    Integer save(Candidate candidate);
}
