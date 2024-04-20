package ar.germin.api.adapter.rest.models;

import ar.germin.api.application.domain.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class PlantNetResponseModel {
    private QueryModel query;
    private String language;
    private String preferedReferential;
    private String bestMatch;
    private List<ResultModel> results;
    private String version;
    private Integer remainingIdentificationRequests;

    public AIDetection toDomain(String id) {
        return AIDetection.builder()
                .id(id)
                .language("es")
                .candidates(getResults()
                        .stream()
                        .map(ResultModel::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }
}

@Data
class QueryModel {
    private String project;
    private List<String> images;
    private List<String> organs;
    private Boolean includeRelatedImages;
    private Boolean noReject;
}

@Data
class ResultModel {
    private Double score;
    private SpeciesModel species;
    private Gbif gbif;
    private Powo powo;

    public Candidate toDomain() {
        return Candidate.builder()
                .id(UUID.randomUUID().toString())
                .selectedByUser(false)
                .score(score.floatValue())
                .specie(Specie.builder()
                        .scientificName(species.getScientificName())
                        .scientificNameWithoutAuthor(species.getScientificNameWithoutAuthor())
                        .scientificNameAuthorship(species.getScientificNameAuthorship())
                        .genus(Genus.builder()
                                .scientificNameWithoutAuthor(species.getGenus().getScientificNameWithoutAuthor())
                                .scientificNameAuthorship(species.getGenus().getScientificNameAuthorship())
                                .scientificName(species.getGenus().getScientificName())
                                .build())
                        .family(Family.builder()
                                .scientificNameWithoutAuthor(species.getFamily().getScientificNameWithoutAuthor())
                                .scientificNameAuthorship(species.getFamily().getScientificNameAuthorship())
                                .scientificName(species.getFamily().getScientificName())
                                .build())
                        .commonNames(species.getCommonNames())
                        .idGbif(gbif.getId())
                        .idPowo(powo.getId())
                        .build())
                .build();
    }
}

@Data
class SpeciesModel {
    private String scientificNameWithoutAuthor;
    private String scientificNameAuthorship;
    private GenusModel genus;
    private FamilyModel family;
    private List<String> commonNames;
    private String scientificName;
}

@Data
class GenusModel {
    private String scientificNameWithoutAuthor;
    private String scientificNameAuthorship;
    private String scientificName;
}

@Data
class FamilyModel {
    String scientificNameWithoutAuthor;
    String scientificNameAuthorship;
    String scientificName;
}

@Data
class Gbif {
    String id;
}

@Data
class Powo {
    private String id;
}


