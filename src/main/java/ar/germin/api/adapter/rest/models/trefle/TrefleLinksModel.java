package ar.germin.api.adapter.rest.models.trefle;

import lombok.Data;

@Data
public class TrefleLinksModel {
    String self;
    String plant;
    String genus;
    String first;
    String prev;
    String next;
    String last;
}
