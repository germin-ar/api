package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.PlantPhoto;

public interface SaveCandidateDiseasePlantsRepository {
    Integer save(DiseaseCandidate diseaseCandidate);
}
