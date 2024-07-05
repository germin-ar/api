package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.DiseaseCandidate;

public interface GetCandidateDiseasePlantsRepository {

    DiseaseCandidate get(String slugFormat);
}
