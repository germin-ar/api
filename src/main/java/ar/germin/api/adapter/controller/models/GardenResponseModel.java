package ar.germin.api.adapter.controller.models;

import ar.germin.api.adapter.jdbc.models.GardenModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Specie;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GardenResponseModel {
    Integer id;
    String name;
    List<Specie> plants;
    //imagenes

    public static GardenResponseModel fromDomain(Garden garden){
        return GardenResponseModel.builder()
                .id(garden.getId())
                .name(garden.getName())
                .build();
    }

    /*@Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record Gar {

    }*/

}
