package ar.germin.api.adapter.rest.models.plantid;

import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.HealthAIDetection;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlantIdCheckHealthResponseModel {
    String accessToken;
    String modelVersion;
    Object customId;
    InputModel input;
    ResultModel result;
    String status;
    Boolean slaCompliantClient;
    Boolean slaCompliantSystem;
    Double created;
    Double completed;

    public HealthAIDetection toDomain() {
        return HealthAIDetection.builder()
                .candidates(this.getResult()
                        .disease()
                        .suggestions()
                        .stream()
                        .map(suggestion -> DiseaseCandidate.builder()
                                .score(suggestion.probability())
                                .name(suggestion.name())
                                .build())
                        .toList())
                .isHealthy(this.getResult().isHealthy().binary())
                .build();
    }
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record DetailsModel(String language, String entityId) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record DiseaseModel(List<SuggestionModel> suggestions) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record InputModel(Double latitude, Double longitude, Boolean similarImages, String health, List<String> images,
                  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX") LocalDateTime datetime) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record IsHealthyModel(Boolean binary, Double threshold, Double probability) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record IsPlantModel(Double probability, Double threshold, Boolean binary) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record ResultModel(DiseaseModel disease, IsHealthyModel isHealthy, IsPlantModel isPlant) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record SimilarImageModel(String id, String url, Double similarity, String urlSmall, String licenseName,
                         String licenseUrl, String citation) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record SuggestionModel(String id, String name, Double probability, List<SimilarImageModel> similarImages,
                       DetailsModel details) {
}

