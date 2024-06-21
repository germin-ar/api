package ar.germin.api.adapter.cognito;

import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("cognito")
public class UserCognitoAdapter implements GetUserRepository, SaveUserRepository {
}
