package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class PlantPhoto {
    Integer id;
    Integer idPlant;
    LocalDateTime uploadedAt;
    String url;
}
