package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.adapter.controller.models.GardenResponseModel;
import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;


@Data
public class GardenModel {
    private Integer id;
    private String name;
    private UserModel id_user;

    public Garden toDomain(){
        return Garden.builder()
                .id(this.id)
                .name(this.name)
                .id_user(User.builder()
                        .id(id_user.getId())
                        .name(id_user.getName())
                        .email(id_user.getEmail())
                        .build())
                .build();
    }
}
@Data
class UserModel{
    Integer id;
    String name;
    String email;
}
