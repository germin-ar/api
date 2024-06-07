package ar.germin.api.adapter.controller.models;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record  PlantRequestModel(Integer id,
                                String alias,
                                Double height,
                                @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime plantingDate,
                                String notes,
                                Integer idGarden,
                                Boolean isActive,
                                Boolean isFavorite,
                                 Integer idUser
                                ) {
//    public Plant toDomain() {
//        return Plant.builder()
//                .id(this.id)
//                .alias(this.alias)
//                .plantingDate(this.plantingDate)
//                .height(this.height)
//                .notes(this.notes)
//                .isFavorite(this.isFavorite)
//                .build();
//            }
}

