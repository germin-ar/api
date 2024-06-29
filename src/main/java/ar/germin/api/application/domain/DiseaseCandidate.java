package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class DiseaseCandidate {
    @With
    Integer id;
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

    public String toSlugFormat() {
        return this.getScientificNameDisease()
                .toLowerCase()
                .replaceAll(" ", "-")
                .replace("×", "x");
    }

}
