package ar.germin.api.adapter.controller.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogoutResponse {
  Boolean isLogout;
  String message;
}
