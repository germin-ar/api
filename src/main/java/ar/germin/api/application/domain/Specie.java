package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

@Builder
@Value
public class Specie {
    //TODO riego
    String scientificName;
    String scientificNameWithoutAuthor;
    String scientificNameAuthorship;
    Genus genus;
    Family family;
    List<String> commonNames;
    Integer idGbif;
    String idPowo;

    public String toSlugFormat() {
        return this.getScientificNameWithoutAuthor().toLowerCase().replaceAll(" ", "-").replace("×", "x");
    }
}
