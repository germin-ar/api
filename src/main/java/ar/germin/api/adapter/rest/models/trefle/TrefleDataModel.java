package ar.germin.api.adapter.rest.models.trefle;

import ar.germin.api.application.domain.PlantDataSuggestion;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrefleDataModel {
    Integer id;
    String commonName;
    String slug;
    String scientificName;
    Integer year;
    String bibliography;
    String author;
    String status;
    String rank;
    String familyCommonName;
    Integer genusId;
    String imageUrl;
    List<String> synonyms;
    String genus;
    String family;
    TrefleLinksModel links;
}
