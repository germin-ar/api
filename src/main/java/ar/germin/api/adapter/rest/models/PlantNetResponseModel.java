package ar.germin.api.adapter.rest.models;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.Family;
import ar.germin.api.application.domain.Genus;
import ar.germin.api.application.domain.Specie;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.Optional;
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
                        .idGbif(Optional.ofNullable(gbif).map(Gbif::getId).orElse(-1))
                        .idPowo(Optional.ofNullable(powo).map(Powo::getId).orElse(Strings.EMPTY))
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
    Integer id;
}

@Data
class Powo {
    private String id;
}


