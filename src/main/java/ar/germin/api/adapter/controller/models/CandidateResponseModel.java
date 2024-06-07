package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.AIDetection;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CandidateResponseModel {
    String id;
    String language;
    List<CandidateModel> candidates;

    public static CandidateResponseModel fromDomain(AIDetection aiDetection) {
        return CandidateResponseModel.builder()
                .id(aiDetection.getId())
                .language(aiDetection.getLanguage())
                .candidates(aiDetection.getCandidates().stream()
                        .map(candidate -> CandidateModel.builder()
                                .score(candidate.getScore())
                                .specie(SpecieModel.builder()
                                        .scientificName(candidate.getSpecie().getScientificNameWithoutAuthor())
                                        .genusName(candidate.getSpecie().getGenus().getScientificNameWithoutAuthor())
                                        .familyName(candidate.getSpecie().getFamily().getScientificNameWithoutAuthor())
                                        .commonNames(candidate.getSpecie().getCommonNames())
                                        .build())
                                .build())
                        .toList())
                .build();
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record CandidateModel(Float score, SpecieModel specie) {
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record SpecieModel(String scientificName, String genusName, String familyName, List<String> commonNames) {
    }
}
