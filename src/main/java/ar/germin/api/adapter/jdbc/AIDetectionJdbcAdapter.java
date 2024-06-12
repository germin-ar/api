package ar.germin.api.adapter.jdbc;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.application.port.out.SaveAIDetectionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Qualifier("jdbc")
public class AIDetectionJdbcAdapter implements SaveAIDetectionRepository, GetAIDetectionRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AIDetectionJdbcAdapter(SqlReader sqlReader, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public AIDetection getByFileImage(FileImage fileImage) {
        return null;
    }

    @Override
    public Integer save(AIDetection aiDetection) {
        return 0;
    }
}
