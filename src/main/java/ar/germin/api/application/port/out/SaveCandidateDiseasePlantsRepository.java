package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.DiseaseCandidate;

public interface SaveCandidateDiseasePlantsRepository {
    Integer save(DiseaseCandidate diseaseCandidate);
}
