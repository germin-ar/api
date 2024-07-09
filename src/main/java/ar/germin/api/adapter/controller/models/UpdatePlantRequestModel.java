package ar.germin.api.adapter.controller.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdatePlantRequestModel(String alias,
                                      Double height,
                                      //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime plantingDate,
                                      Integer idGarden,
                                      Boolean isFavorite,
                                      String imageUrl,
                                      String notes
) {

}

