package ar.germin.api.adapter.rest.models.cropkindwise;

import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.HealthAIDetection;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

import java.util.List;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CropKindwiseCheckHealthResponseModel {
    String accessToken;
    String modelVersion;
    String customId;
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
                                .name(suggestion.getName())
                                .build())
                        .toList())
                .isHealthy(!this
                        .getResult()
                        .getDisease()
                        .getSuggestions()
                        .stream()
                        .filter(suggestionModel -> suggestionModel.getName().equals("healthy"))
                        .toList()
                        .isEmpty())
                .build();
    }

}

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class CropModel {
    List<SuggestionModel> suggestions;
}

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class DetailsModel {
    List<String> commonNames;
    String type;
    TaxonomyModel taxonomy;
    String eppoCode;
    String wikiUrl;
    String language;
    String entityId;
}

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class DiseaseModel {
    List<SuggestionModel> suggestions;
}

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class InputModel {
    Float latitude;
    Float longitude;
    List<String> images;
    String datetime;
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record IsPlantModel(Double probability, Double threshold, Boolean binary) {
}

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class ResultModel {
    IsPlantModel isPlant;
    DiseaseModel disease;
    CropModel crop;
}


@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class SuggestionModel {
    String id;
    String name;
    Double probability;
    DetailsModel details;
    String scientificName;
}

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class TaxonomyModel {
    @JsonProperty("class")
    String className;
    String genus;
    String order;
    String family;
    String phylum;
    String kingdom;
}


