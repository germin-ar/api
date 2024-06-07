package ar.germin.api.application.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class User {
    Integer id;
    String name;
    String email;
    // TODO password  ver cognito? String password;
    // TODO favoritos, preferencia alimentaria, notificaciones
    //TODO tutoriales guardados,ubicacion geografica,foto,suscripcion
}
