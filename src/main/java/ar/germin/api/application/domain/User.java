package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class User {
    Integer id;
    String pass;
    String name;
    Boolean isConfirmed;
    String email;
    String username;
    String rol;
    String hash;
    // TODO favoritos, preferencia alimentaria, notificaciones
    //TODO tutoriales guardados,ubicacion geografica,foto,suscripcion
}
