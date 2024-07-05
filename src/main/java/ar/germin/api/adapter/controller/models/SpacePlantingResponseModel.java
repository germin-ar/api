package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.SpacePlanting;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SpacePlantingResponseModel(String spaceName) {

    public static SpacePlantingResponseModel fromDomain(SpacePlanting spacePlanting) {
        return SpacePlantingResponseModel.builder()
                .spaceName(spacePlanting.getNames().stream().findAny().orElse(""))
                .build();
    }
}
