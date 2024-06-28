package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class DiseaseCandidate {
    String id;
    Double score;
    String name;
    String scientificNameDisease;
    String type;
    String eppoCode;
    String commonNames;
    String kingdomTaxonomy;
    String entityId;
    String classTaxonomy;
    String genusTaxonomy;
    String orderTaxonomy;
    String familyTaxonomy;
    String phylumTaxonomy;
    String language;
    String wikiUrls;
    String slug;


    public String toSlugFormat() {
        return this.getScientificNameDisease()
                .toLowerCase()
                .replaceAll(" ", "-")
                .replace("×", "x");
    }

}
