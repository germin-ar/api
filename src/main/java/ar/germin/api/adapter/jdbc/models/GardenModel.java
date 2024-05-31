package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.User;
import lombok.Data;


@Data
public class GardenModel {
    Integer id;
    String name;
    User id_user;

    public Garden toDomain(){
        return Garden.builder()
                .id(this.id)
                .name(this.name)
                .id_user(this.id_user)
                .build();
    }
}
