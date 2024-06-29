package ar.germin.api.adapter.rest.models.cropkindwise;

import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.HealthAIDetection;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
                        .disease()
                        .suggestions()
                        .stream()
                        .filter(suggestion -> !suggestion.name().equals("healthy"))
                        .map(suggestion -> DiseaseCandidate.builder()
                                .name(suggestion.name())
                                .scientificNameDisease(suggestion.scientificName())
                                .commonNames(Optional
                                        .ofNullable(suggestion.details().commonNames())
                                        .map(names -> String.join(",", names))
                                        .orElse(Strings.EMPTY))
                                .entityId(suggestion.details().entityId())
                                .language(suggestion.details().language())
                                .eppoCode(suggestion.details().eppoCode())
                                .type(suggestion.details().type())
                                .wikiUrls(suggestion.details().wikiUrl())
                                .kingdomTaxonomy(Optional.ofNullable(suggestion.details().taxonomy()).map(TaxonomyModel::kingdom).orElse(Strings.EMPTY))
                                .classTaxonomy(Optional.ofNullable(suggestion.details().taxonomy()).map(TaxonomyModel::className).orElse(Strings.EMPTY))
                                .orderTaxonomy(Optional.ofNullable(suggestion.details().taxonomy()).map(TaxonomyModel::order).orElse(Strings.EMPTY))
                                .familyTaxonomy(Optional.ofNullable(suggestion.details().taxonomy()).map(TaxonomyModel::family).orElse(Strings.EMPTY))
                                .phylumTaxonomy(Optional.ofNullable(suggestion.details().taxonomy()).map(TaxonomyModel::phylum).orElse(Strings.EMPTY))
                                .genusTaxonomy(Optional.ofNullable(suggestion.details().taxonomy()).map(TaxonomyModel::genus).orElse(Strings.EMPTY))
                                .build())
                        .toList())
                .isHealthy(!this
                        .getResult()
                        .disease()
                        .suggestions()
                        .stream()
                        .filter(suggestionModel -> suggestionModel.name().equals("healthy"))
                        .toList()
                        .isEmpty())
                .build();
    }

}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record CropModel(List<SuggestionModel> suggestions) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record DetailsModel(List<String> commonNames, String type, TaxonomyModel taxonomy, String eppoCode, String wikiUrl,
                    String language, String entityId) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record DiseaseModel(List<SuggestionModel> suggestions) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record InputModel(Float latitude, Float longitude, List<String> images, String datetime) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record IsPlantModel(Double probability, Double threshold, Boolean binary) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record ResultModel(IsPlantModel isPlant, DiseaseModel disease, CropModel crop) {
}


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record SuggestionModel(String id, String name, Double probability, DetailsModel details, String scientificName) {
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record TaxonomyModel(@JsonProperty("class") String className, String genus, String order, String family, String phylum,
                     String kingdom) {
}


