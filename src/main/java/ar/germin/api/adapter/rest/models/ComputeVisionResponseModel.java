package ar.germin.api.adapter.rest.models;

import ar.germin.api.application.domain.SpacePlanting;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.stream.Stream;

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

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record LandmarksModel(String name,
                      Double confidence) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record DetailModel(List<LandmarksModel> landmarks) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record CategoriesModel(String name,
                       Double score,
                       DetailModel detail) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record ColorModel(String dominantColorForeground,
                  String dominantColorBackground,
                  List<String> dominantColors,
                  String accentColor,
                  Boolean isBwImg,
                  Boolean isBWImg) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record CaptionsModel(String text,
                     Double confidence) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record DescriptionModel(List<String> tags,
                        List<CaptionsModel> captions) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record MetadataModel(Integer height,
                     Integer width,
                     String format) {
}
