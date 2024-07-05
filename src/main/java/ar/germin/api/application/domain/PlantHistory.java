package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class PlantHistory {
    Integer id;
    Integer idPlant;
    String notes;
    Double height;
    String alias;
    String url_image;
    String modified_at;
    Integer idDiseases;
}
