package ar.germin.api.adapter.jdbc.models;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserModel {
  String name;
  Boolean isConfirmed;
  String email;
  //String username;

  public User toDomain() {
    return User.builder()
            .name(this.name)
            .isConfirmed(this.isConfirmed)
            .email(this.email)
            .build();
  }
}