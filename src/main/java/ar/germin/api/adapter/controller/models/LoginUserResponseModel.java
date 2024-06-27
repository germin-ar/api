package ar.germin.api.adapter.controller.models;

import ar.germin.api.application.domain.UserSessionTokens;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginUserResponseModel {

  String idToken;
  String accessToken;
  String refreshToken;

  public static LoginUserResponseModel fromDomain(UserSessionTokens userSessionTokens) {
    return LoginUserResponseModel.builder()
            .idToken(userSessionTokens.getId())
            .accessToken(userSessionTokens.getAccess())
            .refreshToken(userSessionTokens.getRefresh())
            .build();
  }

}
