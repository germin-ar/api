package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.UserRegistrationPortIn;
import ar.germin.api.application.port.out.GetUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationUseCase implements UserRegistrationPortIn {
    private GetUserRepository getJdbcUserRepository;
    private GetUserRepository getCognitoUserRepository;

    @Autowired
    public UserRegistrationUseCase(@Qualifier("jdbc") GetUserRepository getJdbcUserRepository,
                                   @Qualifier("cognito") GetUserRepository getCognitoUserRepository) {
    }
}
