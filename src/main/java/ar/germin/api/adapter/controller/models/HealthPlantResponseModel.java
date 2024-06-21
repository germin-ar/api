package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.HealthAIDetection;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class HealthPlantResponseModel {
    Boolean isHealthy;

    public static HealthPlantResponseModel fromDomain(HealthAIDetection healthAIDetection) {
        return HealthPlantResponseModel.builder()
                .isHealthy(healthAIDetection.getIsHealthy())
                .build();
    }
}

