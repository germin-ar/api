package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.port.in.UserRegistrationPortIn;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UsernameExistsException;

@Component
public class UserRegistrationUseCase implements UserRegistrationPortIn {
  private final GetUserRepository getJdbcUserRepository;
  private final GetUserRepository getCognitoUserRepository;
  private final SaveUserRepository saveJdbcUserRepository;
  private final SaveUserRepository saveCognitoUserRepository;


  @Autowired
  public UserRegistrationUseCase(@Qualifier("jdbc") GetUserRepository getJdbcUserRepository,
                                 @Qualifier("cognito") GetUserRepository getCognitoUserRepository,
                                 @Qualifier("jdbc") SaveUserRepository saveJdbcUserRepository,
                                 @Qualifier("cognito") SaveUserRepository saveCognitoUserRepository
  ) {

    this.getJdbcUserRepository = getJdbcUserRepository;
    this.getCognitoUserRepository = getCognitoUserRepository;
    this.saveJdbcUserRepository = saveJdbcUserRepository;
    this.saveCognitoUserRepository = saveCognitoUserRepository;
  }

  @Override
  public String signUp(String username, String password, String email) {
    if(userExistsInBothRepositories(email)) return "Ya hay un usuario registrado con ese mail";

    try {
      User userToSave=User.builder()
              .username(username)
              .email(email)
              .isConfirmed(false)
              .build();

      this.saveCognitoUserRepository.save(userToSave);

      this.saveJdbcUserRepository.save(userToSave);

      return "Sign up successful. Please check your email for confirmation.";

    } catch (UsernameExistsException e) {
      return "Username already exists.";
    } catch (Exception e) {
      return "An error occurred: " + e.getMessage();
    }
  }


  private boolean userExistsInBothRepositories(String email) {
    // Verificar en la base de datos
    User dbUser = this.getJdbcUserRepository.get(email);
    boolean existsInDb = dbUser != null && dbUser.getEmail() != null && !dbUser.getEmail().isEmpty();

    // Verificar en Cognito
    User cognitoUser = this.getCognitoUserRepository.get(email);
    boolean existsInCognito = cognitoUser != null && cognitoUser.getEmail() != null && cognitoUser.getEmail().equals(email);
    //TODO: Ver que pasa si solo existe en la base de datos...deberia agregarlo a cognito p borrarlo de la bd y dejar que se registre?¿
    return existsInDb || existsInCognito;
  }

}

