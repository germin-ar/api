package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.Plant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlantResponseModel {
    Integer id;
    String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime modificationDate;
    String description;
    // TODO ver list -> List<FileImage> images;
    Boolean favorite;
    Double height;
    //TODO enum  ALTO BAJO MEDIO
    String sunExposure;
    String notes;
    String nameGarden;
    String expo;

    public static PlantResponseModel fromDomain(Plant plant){
        return PlantResponseModel.builder()
                .id(plant.getId())
                .name(plant.getAlias())
                .creationDate(plant.getCreationDate())
                .modificationDate(plant.getModificationDate())
                .description(plant.getDescription())
                .favorite(plant.getFavorite())
                .height(plant.getHeight())
                .sunExposure(plant.getSunExposure())
                .notes(plant.getNotes())
                .build();
    }

}


