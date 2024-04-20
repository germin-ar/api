package ar.germin.api.adapter.jdbc;

import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.port.out.GetCandidateRepository;
import ar.germin.api.application.port.out.SaveCandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CandidateJdbcAdapter implements SaveCandidateRepository, GetCandidateRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CandidateJdbcAdapter(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Candidate> save(List<Candidate> candidates) {
        return List.of();
    }

    @Override
    public List<Candidate> getByFileImage(FileImage fileImage) {
        return List.of();
    }
}
