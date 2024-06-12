package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.Candidate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidateModel {
    String id;
    Float score;
    String specieScientificName;
    String specieScientificNameWithoutAuthor;
    String specieScientificNameAuthorship;
    Integer specieIdGbif;
    String specieIdPowo;
    String specieCommonNames;
    String genusScientificName;
    String genusScientificNameWithoutAuthor;
    String genusScientificNameAuthorship;
    String familyScientificName;
    String familyScientificNameWithoutAuthor;
    String familyScientificNameAuthorship;
    Boolean selectedByUser;

    public static CandidateModel fromDomain(Candidate candidate) {
        return CandidateModel.builder()
                .id(candidate.getId())
                .score(candidate.getScore())
                .specieScientificName(candidate.getSpecie().getScientificName())
                .specieScientificNameWithoutAuthor(candidate.getSpecie().getScientificNameWithoutAuthor())
                .specieScientificNameAuthorship(candidate.getSpecie().getScientificNameAuthorship())
                .specieIdGbif(candidate.getSpecie().getIdGbif())
                .specieIdPowo(candidate.getSpecie().getIdPowo())
                .specieCommonNames(
                        String
                                .join(",",
                                        candidate
                                                .getSpecie()
                                                .getCommonNames()))
                .genusScientificName(candidate.getSpecie().getGenus().getScientificName())
                .genusScientificNameWithoutAuthor(candidate.getSpecie().getGenus().getScientificNameWithoutAuthor())
                .genusScientificNameAuthorship(candidate.getSpecie().getGenus().getScientificNameAuthorship())
                .familyScientificName(candidate.getSpecie().getFamily().getScientificName())
                .familyScientificNameWithoutAuthor(candidate.getSpecie().getFamily().getScientificNameWithoutAuthor())
                .familyScientificNameAuthorship(candidate.getSpecie().getFamily().getScientificNameAuthorship())
                .selectedByUser(candidate.getSelectedByUser())
                .build();
    }
}
