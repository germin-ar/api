package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.port.in.UserRegistrationPortIn;
import ar.germin.api.application.port.out.DeleteUserRepository;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UsernameExistsException;

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
  public String signUp(String username, String password, String email) {
    boolean existsInDb = userExistsInRepository(getJdbcUserRepository, email);
    boolean existsInCognito = userExistsInRepository(getCognitoUserRepository, email);

    if(existsInDb && !existsInCognito){
      this.deleteUserRepository.delete(email);
    }
    if (existsInDb && existsInCognito) {
      return "Ya hay un usuario registrado con ese mail";
    }
    try {
      if (!existsInDb) {
        this.saveJdbcUserRepository.save(User.builder()
                .name(username)
                .username(username)
                .email(email)
                .isConfirmed(false)
                .build());
      }
      if (!existsInCognito) {
        this.saveCognitoUserRepository.save(User.builder()
                .username(username)
                .email(email)
                .pass(password)
                .build());
      }

      return "Sign up successful. Please check your email for confirmation.";

    } catch (UsernameExistsException e) {
      return "Username already exists.";
    } catch (Exception e) {
      return "An error occurred: " + e.getMessage();
    }
  }

  private boolean userExistsInRepository(GetUserRepository getUserRepository, String email) {
    return getUserRepository.get(email).isPresent();
  }


}

