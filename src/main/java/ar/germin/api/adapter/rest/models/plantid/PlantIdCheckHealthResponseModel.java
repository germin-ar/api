package ar.germin.api.adapter.rest.models.plantid;

import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.HealthAIDetection;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
                        .getDisease()
                        .getSuggestions()
                        .stream()
                        .map(suggestion -> DiseaseCandidate.builder()
                                .id(suggestion.getId())
                                .score(suggestion.getProbability())
                                .name(suggestion.getName())
                                .build())
                        .toList())
                .isHealthy(this.getResult().getIsHealthy().getBinary())
                .build();
    }
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class DetailsModel {
    String language;
    String entityId;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class DiseaseModel {
    List<SuggestionModel> suggestions;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class InputModel {
    Double latitude;
    Double longitude;
    Boolean similarImages;
    String health;
    List<String> images;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
    LocalDateTime datetime;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class IsHealthyModel {
    Boolean binary;
    Double threshold;
    Double probability;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class IsPlantModel {
    Double probability;
    Double threshold;
    Boolean binary;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class ResultModel {
    DiseaseModel disease;
    IsHealthyModel isHealthy;
    IsPlantModel isPlant;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class SimilarImageModel {
    String id;
    String url;
    Double similarity;
    String urlSmall;
    String licenseName;
    String licenseUrl;
    String citation;
}

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class SuggestionModel {
    String id;
    String name;
    Double probability;
    List<SimilarImageModel> similarImages;
    DetailsModel details;
}

