package ar.germin.api.adapter.rest.models.computevision;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CategoriesModel(String name,
                       Double score,
                       DetailModel detail) {
}
