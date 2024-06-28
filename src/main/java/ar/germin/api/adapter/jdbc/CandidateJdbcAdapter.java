package ar.germin.api.adapter.jdbc;

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
    private final SaveCandidateRepository saveCandidateRepository;

    @Autowired
    public CandidateJdbcAdapter(SaveCandidateRepository saveCandidateRepository, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.saveCandidateRepository = saveCandidateRepository;
    }

    @Override
    public Integer save(Candidate candidate) {
        // No es necesario convertir Candidate a CandidateModel si saveCandidateRepository.save espera un Candidate
        Integer result = saveCandidateRepository.save(candidate);
        return result;  // Retorna el resultado del método save de saveCandidateRepository
    }

    @Override
    public List<Candidate> getByFileImage(FileImage fileImage) {
        return List.of();  // Implementa la lógica para obtener candidatos por imagen de archivo
    }
}
