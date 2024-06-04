package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlantResponseModel {
    Integer id;
    String alias;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime modificationDate;
    String description;
    // TODO ver list -> List<FileImage> images;
    Boolean favorite;
    Double height;
    //TODO enum  ALTO BAJO MEDIO
    String sunExposure;
    List<NoteModel> notes;
    String nameGarden;
    String expo;

    public static PlantResponseModel fromDomain(Plant plant) {
        return PlantResponseModel.builder()
                .id(plant.getId())
                .alias(plant.getAlias())
                .creationDate(plant.getCreationDate())
                .modificationDate(plant.getModificationDate())
                .favorite(plant.getIsFavorite())
                .height(plant.getHeight())
                .notes(plant
                        .getNotes()
                        .stream()
                        .map(note -> NoteModel.builder()
                                .id(note.getId())
                                .observations(note.getObservations())
                                .creationDate(note.getCreationDate())
                                .modificationDate(note.getModificationDate())
                                .build())
                        .toList())
                .build();
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record NoteModel(Integer id,
                     String observations,
                     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime creationDate,
                     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime modificationDate) {

    }

}


