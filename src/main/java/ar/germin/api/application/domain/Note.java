package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class Note {
    Integer id;
    Plant plant;
    User user;
    String observations;
    LocalDateTime creationDate;
    LocalDateTime modificationDate;
}