package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.UserExistsException;
import ar.germin.api.application.exceptions.UserNotFoundException;
import ar.germin.api.application.port.in.UserRegistrationPortIn;
import ar.germin.api.application.port.out.DeleteUserRepository;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UsernameExistsException;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class UserRegistrationUseCase implements UserRegistrationPortIn {
    private final GetUserRepository getJdbcUserRepository;
    private final GetUserRepository getCognitoUserRepository;
    private final SaveUserRepository saveJdbcUserRepository;
    private final SaveUserRepository saveCognitoUserRepository;
    private final DeleteUserRepository deleteUserRepository;


    @Autowired
    public UserRegistrationUseCase(@Qualifier("jdbc") GetUserRepository getJdbcUserRepository,
                                   @Qualifier("cognito") GetUserRepository getCognitoUserRepository,
                                   @Qualifier("jdbc") SaveUserRepository saveJdbcUserRepository,
                                   @Qualifier("cognito") SaveUserRepository saveCognitoUserRepository, DeleteUserRepository deleteUserRepository
    ) {

        this.getJdbcUserRepository = getJdbcUserRepository;
        this.getCognitoUserRepository = getCognitoUserRepository;
        this.saveJdbcUserRepository = saveJdbcUserRepository;
        this.saveCognitoUserRepository = saveCognitoUserRepository;
        this.deleteUserRepository = deleteUserRepository;
    }



    @Override
    public User signUp(String username, String password, String email) throws Exception {
        Boolean existsInDb = userExistsInRepository(getJdbcUserRepository, email);
        Boolean existsInCognito = userExistsInRepository(getCognitoUserRepository, email);

        if (existsInDb && !existsInCognito) {
            this.deleteUserRepository.delete(email);
        }
        if (existsInDb && existsInCognito) {
            log.info("There's already a user registered with that email.");
            return this.getJdbcUserRepository.get(email);
        }
        try {
            if (!existsInCognito) {
                this.saveCognitoUserRepository.save(User.builder()
                        .username(username)
                        .email(email)
                        .pass(password)
                        .build());
            }

            // Crear un ScheduledExecutorService
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            // Programar la ejecución del método get después de 3 segundos
            Future<User> userCognitoFuture = scheduler.schedule(() -> this.getCognitoUserRepository.get(email), 1, TimeUnit.SECONDS);

            // Esperar a que la tarea programada se complete y obtener el resultado
            User userCognito = userCognitoFuture.get();

            scheduler.shutdown();

            if (!existsInDb) {
                this.saveJdbcUserRepository.save(User.builder()
                        .name(username)
                        .username(username)
                        .email(email)
                        .hash(userCognito.getHash())
                        .rol(userCognito.getRol())
                        .isConfirmed(userCognito.getIsConfirmed())
                        .build());
            }
            log.info("Sign up successful. Please check your email for confirmation.");
            return this.getJdbcUserRepository.get(email);
        } catch (UsernameExistsException e) {
            throw new UserExistsException("Username qqalready exists.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private Boolean userExistsInRepository(GetUserRepository getUserRepository, String email){
        try {
            getUserRepository.get(email);
            return true;
        } catch (UserNotFoundException ex) {
            return false;
        }

    }


}

