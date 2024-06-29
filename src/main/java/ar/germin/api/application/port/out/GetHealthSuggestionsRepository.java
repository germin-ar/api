package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.HealthAIDetection;

public interface GetHealthSuggestionsRepository {
    HealthAIDetection getHealthStatus(FileImage fileImage);
}
