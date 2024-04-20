package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class Specie {
    String scientificName;
    String scientificNameWithoutAuthor;
    String scientificNameAuthorship;
    Genus genus;
    Family family;
    List<String> commonNames;
    String idGbif;
    String idPowo;
}
