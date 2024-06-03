package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class Plant {
    Integer id;
    String alias;
    LocalDateTime creationDate;
    LocalDateTime modificationDate;
    LocalDateTime plantingDate;
    // TODO ver list -> List<FileImage> images;
    Boolean isFavorite;
    Double height;
    //TODO enum  ALTO BAJO MEDIO
    //TODO frecuencia de riego
    // String sunExposure;
    // TODO normalizar -> notes
    String notes;
}
