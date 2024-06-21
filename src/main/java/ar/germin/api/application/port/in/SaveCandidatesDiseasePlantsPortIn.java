package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.FileImage;

public interface SaveCandidatesDiseasePlantsPortIn {
    Integer save(DiseaseCandidate diseaseCandidate);
}
