package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.HealthAIDetection;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class HealthPlantResponseModel {
    Boolean isHealthy;
    String name;
    String scientificNameDisease;
    String type;
    String commonName;
    String kingdomTaxonomy;
    String entityId;
    String classTaxonomy;
    String genusTaxonomy;
    String orderTaxonomy;
    String familyTaxonomy;
    String phylumTaxonomy;
    String wikiUrls;
    List<HealthPlantResponseModel>candidates;


    public static HealthPlantResponseModel fromDomain(HealthAIDetection healthAIDetection) {
        return HealthPlantResponseModel.builder()
                .isHealthy(healthAIDetection.getIsHealthy())
                .candidates(Optional
                        .ofNullable(healthAIDetection.getCandidates()).orElse(Collections.emptyList())
                        .stream()
                        .map(candidate -> HealthPlantResponseModel.builder()
                                .scientificNameDisease(candidate.getScientificNameDisease())
                                .classTaxonomy(candidate.getClassTaxonomy())
                                .commonName(candidate.getCommonNames())
                                .entityId(candidate.getEntityId())
                                .genusTaxonomy(candidate.getGenusTaxonomy())
                                .familyTaxonomy(candidate.getFamilyTaxonomy())
                                .kingdomTaxonomy(candidate.getKingdomTaxonomy())
                                .name(candidate.getName())
                                .orderTaxonomy(candidate.getOrderTaxonomy())
                                .phylumTaxonomy(candidate.getPhylumTaxonomy())
                                .type(candidate.getType())
                                .wikiUrls(candidate.getWikiUrls())
                        .build())
                        .toList())
                .build();
    }
    public static List<HealthPlantResponseModel> fromDomainList(List<HealthAIDetection>candidates) {
        return candidates
                .stream()
                .map(HealthPlantResponseModel::fromDomain)
                .toList();
    }
}
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record CandidateModel(List<CandidateModel>candidates){
};


