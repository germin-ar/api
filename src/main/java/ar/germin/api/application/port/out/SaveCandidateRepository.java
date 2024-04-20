package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Candidate;

import java.util.List;

public interface SaveCandidateRepository {
    List<Candidate> save(List<Candidate> candidates);
}
