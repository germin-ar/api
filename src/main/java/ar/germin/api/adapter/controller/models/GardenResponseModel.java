package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.Garden;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Builder
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GardenResponseModel {
    Integer id;
    String name;
    List<PlantModel> plants;
    //imagenes

    public static GardenResponseModel fromDomain(Garden garden) {
        return GardenResponseModel.builder()
                .id(garden.getId())
                .name(garden.getName())
                .plants(Optional
                        .ofNullable(garden.getPlants()).orElse(Collections.emptyList())
                        .stream()
                        .map(plant -> PlantModel.builder()
                                .id(plant.getId())
                                .alias(plant.getAlias())
                                .modificationDate(plant.getModificationDate())
                                .creationDate(plant.getCreationDate())
                                .build())
                        .toList())
                .build();
    }

    public static List<GardenResponseModel> fromDomainList(List<Garden> gardens) {
        return gardens
                .stream()
                .map(GardenResponseModel::fromDomain)
                .toList();
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record PlantModel(Integer id,
                      String alias,
                      @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime creationDate,
                      @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime modificationDate) {

    }

}
