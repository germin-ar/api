package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserSessionTokens {

  String id;
  String access;
  String refresh;

}
