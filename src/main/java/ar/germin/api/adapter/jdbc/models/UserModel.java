package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserModel {
    Integer id;
    String name;
    Boolean isConfirmed;
    String email;
    String hash;
    String rol;
    //String username;

    public User toDomain() {
        return User.builder()
                .id(this.getId())
                .name(this.getName())
                .isConfirmed(this.getIsConfirmed())
                .email(this.getEmail())
                .build();
    }
}
