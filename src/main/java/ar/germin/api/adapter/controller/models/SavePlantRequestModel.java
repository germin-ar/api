package ar.germin.api.adapter.controller.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SavePlantRequestModel(String alias,
                                    Double height,
                                    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX") LocalDateTime plantingDate,
                                    Integer idGarden,
                                    Boolean isFavorite,
                                    String imageUrl
) {

}

