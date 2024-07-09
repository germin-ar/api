package ar.germin.api.adapter.rest.models.computevision;

import ar.germin.api.application.domain.SpacePlanting;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.util.List;
import java.util.stream.Stream;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ComputeVisionResponseModel(List<CategoriesModel> categories,
                                         ColorModel color,
                                         DescriptionModel description,
                                         String requestId,
                                         MetadataModel metadata) {
    public SpacePlanting toDomain() {
        List<String> names = Stream.concat(
                        this.description().captions().stream().map(CaptionsModel::text),
                        Stream.empty())
                .toList();
        return SpacePlanting.builder()
                .names(names)
                .build();
    }
}

