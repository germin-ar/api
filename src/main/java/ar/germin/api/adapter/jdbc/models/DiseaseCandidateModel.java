package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.DiseaseCandidate;
import lombok.Data;

@Data
public class DiseaseCandidateModel {
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
    String slug;

    public DiseaseCandidate toDomain(){
        return DiseaseCandidate.builder()
                .id(this.id)
                .score(this.score)
                .name(this.name)
                .scientificNameDisease(scientificNameDisease)
                .type(this.type)
                .eppoCode(this.eppoCode)
                .commonNames(this.commonNames)
                .kingdomTaxonomy(this.kingdomTaxonomy)
                .entityId(this.entityId)
                .classTaxonomy(this.classTaxonomy)
                .genusTaxonomy(this.genusTaxonomy)
                .orderTaxonomy(this.orderTaxonomy)
                .familyTaxonomy(this.familyTaxonomy)
                .phylumTaxonomy(this.phylumTaxonomy)
                .language(this.language)
                .wikiUrls(this.wikiUrls)
                .slug(this.slug)
                .build();
    }
}
