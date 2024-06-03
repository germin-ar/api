package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
@Builder
@Value
public class Plant {
    Integer id;
    String name;
    LocalDateTime creationDate;
    LocalDateTime modificationDate;
    String description;
    // TODO ver list -> List<FileImage> images;
    Boolean favorite;
    Double height;
    //TODO enum  ALTO BAJO MEDIO
    //TODO frecuencia de riego
    String sunExposure;
    String notes;
}
