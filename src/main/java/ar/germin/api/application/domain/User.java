package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class User {
    Integer id;
    String name;
    String email;
    String password;
    // TODO favoritos, preferencia alimentaria, notificaciones
}
