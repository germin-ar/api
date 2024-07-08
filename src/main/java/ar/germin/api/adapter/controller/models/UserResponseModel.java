package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseModel {
  String email;
  Boolean isConfirmed;
  String name;
  String rol;

  public static UserResponseModel fromDomain(User user) {

    return UserResponseModel.builder()
            .email(user.getEmail())
            .isConfirmed(user.getIsConfirmed())
            .name(user.getName())
            .rol(user.getRol())
            .build();
  }

}
