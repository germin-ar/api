package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.FileImage;

import java.util.List;

public interface GetCandidateRepository {
    List<Candidate> getByFileImage(FileImage fileImage);
}
