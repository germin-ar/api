package ar.germin.api.adapter.controller.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NoteRequestModel {
    Integer id;
    Integer plantId;
    Integer userId;
    String observations;
    LocalDateTime creationDate;
    LocalDateTime modificationDate;
}
