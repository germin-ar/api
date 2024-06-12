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
    ImageModel image;
    HealthModel health;


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
                                .plantData(PlantDataModel.builder()
                                        .fertilizer(candidate.getPlantCatalog().getFertilizer())
                                        .irrigation(candidate.getPlantCatalog().getWateringFrequency())
                                        .soil(candidate.getPlantCatalog().getSoil())
                                        .sunExposure(candidate.getPlantCatalog().getSunlight())
                                        .insecticide(candidate.getPlantCatalog().getInsecticide())
                                        .temperatureMax(candidate.getPlantCatalog().getTemperatureMax())
                                        .temperatureMin(candidate.getPlantCatalog().getTemperatureMin())
                                        .description(candidate.getPlantCatalog().getDescription())
                                        .height(candidate.getPlantCatalog().getMaxSize())
                                        .tips(candidate.getPlantCatalog().getTips())
                                        .build())
                                .build())
                        .toList())
                .image(ImageModel.builder()
                        .uuid(aiDetection.getFileImage().getId())
                        .url(aiDetection.getFileImage().getFilePath())
                        .build())
                .health(HealthModel.builder()
                        .isHealty(aiDetection.getHealthAIDetection().getIsHealthy())
                        .build())
                .build();
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record ImageModel(String uuid, String url) {
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record CandidateModel(Float score, SpecieModel specie, PlantDataModel plantData) {
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record SpecieModel(String scientificName, String genusName, String familyName, List<String> commonNames) {
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record PlantDataModel(String description,
                          Double height,
                          String fertilizer,
                          String irrigation,
                          String soil,
                          String sunExposure,
                          String insecticide,
                          Double temperatureMax,
                          Double temperatureMin,
                          String tips) {
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record HealthModel(Boolean isHealty) {
    }
}
