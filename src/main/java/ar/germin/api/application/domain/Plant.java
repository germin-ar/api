package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    String sunExposure;
    String notes;
}
