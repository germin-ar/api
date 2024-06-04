package ar.germin.api.adapter.controller.models;


import ar.germin.api.application.domain.Note;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NoteResponseModel {
    Integer id;
    PlantModel plant;
    UserModel user;
    String observations;
    LocalDateTime creationDate;
    LocalDateTime modificationDate;

    public static NoteResponseModel fromDomain(Note note) {
        return NoteResponseModel.builder()
                .id(note.getId())
                .plant(PlantModel.builder()
                        .id(note.getPlant().getId())
                        .alias(note.getPlant().getAlias())
                        .creationDate(note.getPlant().getCreationDate())
                        .modificationDate(note.getPlant().getModificationDate())
                        .build())
                .user(NoteResponseModel.UserModel.builder()
                        .id(note.getUser().getId())
                        .name(note.getUser().getName())
                        .email(note.getUser().getEmail())
                        .build())
                .observations(note.getObservations())
                .creationDate(note.getCreationDate())
                .modificationDate(note.getModificationDate())
                .build();
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record UserModel(Integer id,
                     String name,
                     String email) {

    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record PlantModel(Integer id,
                      String alias,
                      @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime creationDate,
                      @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime modificationDate) {

    }

}
