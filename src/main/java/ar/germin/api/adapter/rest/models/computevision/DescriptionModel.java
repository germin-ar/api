package ar.germin.api.adapter.rest.models.computevision;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DescriptionModel(List<String> tags,
                               List<CaptionsModel> captions) {
}
