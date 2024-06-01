package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.Garden;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GardenResponseModel {
    Integer id;
    String name;
    UserModel user;
    List<PlantModel> plants;
    //imagenes

    public static GardenResponseModel fromDomain(Garden garden) {
        return GardenResponseModel.builder()
                .id(garden.getId())
                .name(garden.getName())
                .user(UserModel.builder()
                        .id(garden.getUser().getId())
                        .name(garden.getUser().getName())
                        .email(garden.getUser().getEmail())
                        .build())
                .plants(garden
                        .getPlants()
                        .stream()
                        .map(plant -> PlantModel.builder()
                                .id(plant.getId())
                                .name(plant.getName())
                                .modificationDate(plant.getModificationDate())
                                .creationDate(plant.getCreationDate())
                                .build())
                        .toList())
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
                      String name,
                      @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime creationDate,
                      @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime modificationDate) {

    }

}
