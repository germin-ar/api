package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class PlantHistory {
    Integer id;
    Integer idPlant;
    String notes;
    Double height;
    String alias;
    String urlImage;
    LocalDateTime modifiedAt;
    Integer idDiseases;
}
