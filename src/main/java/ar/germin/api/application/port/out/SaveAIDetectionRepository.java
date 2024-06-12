package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.AIDetection;

public interface SaveAIDetectionRepository {
    Integer save(AIDetection aiDetection);
}
