package ar.germin.api.adapter.rest.models;

import lombok.Data;

import java.util.List;

@Data
public class TreflePlantSearchResponseModel {
    private List<DataModel> data;
}

@Data
class DataModel {
    private Long id;
    private String commonName;
    private String slug;
    private String scientificName;
    private Long year;
    private String bibliography;
    private String author;
    private String status;
    private String rank;
    private Object familyCommonName;
    private Long genusID;
    private String imageURL;
    private List<String> synonyms;
    private String genus;
    private String family;
}
