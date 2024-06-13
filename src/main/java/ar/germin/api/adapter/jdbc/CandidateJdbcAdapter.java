package ar.germin.api.adapter.jdbc;

import ar.germin.api.adapter.jdbc.models.CandidateModel;
import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.port.out.GetCandidateRepository;
import ar.germin.api.application.port.out.SaveCandidateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CandidateJdbcAdapter implements SaveCandidateRepository, GetCandidateRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CandidateJdbcAdapter(SqlReader sqlReader,
                                NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Integer save(Candidate candidate) {

        CandidateModel candidateModel = CandidateModel.fromDomain(candidate);
        return 1;
        //TODO retorna 1?
    }

    @Override
    public List<Candidate> getByFileImage(FileImage fileImage) {
        return List.of();
    }
}
