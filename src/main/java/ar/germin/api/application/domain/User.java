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
    //Role role;
    // TODO password  ver cognito? String password;
    // TODO favoritos, preferencia alimentaria, notificaciones
    //TODO tutoriales guardados,ubicacion geografica,foto,suscripcion
}
