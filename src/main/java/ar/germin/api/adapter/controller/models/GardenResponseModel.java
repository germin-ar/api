package ar.germin.api.adapter.controller.models;

import ar.germin.api.adapter.jdbc.models.GardenModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Specie;
import ar.germin.api.application.domain.User;
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
    UserModel id_user;
    List<Specie> plants;
    //imagenes

    public static GardenResponseModel fromDomain(Garden garden){
        return GardenResponseModel.builder()
                .id(garden.getId())
                .name(garden.getName())
                .id_user(UserModel.builder()
                        .id(garden.getId_user().getId())
                        .name(garden.getId_user().getName())
                        .email(garden.getId_user().getEmail())
                        .build())
                .build();
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record UserModel(Integer id, String name, String email) {

    }

}
