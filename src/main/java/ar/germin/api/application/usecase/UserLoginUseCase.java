package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.domain.UserSessionTokens;
import ar.germin.api.application.exceptions.UserNotFoundException;
import ar.germin.api.application.port.in.UserLoginPortIn;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import ar.germin.api.application.port.out.UserLoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserLoginUseCase implements UserLoginPortIn {

  private final UserLoginRepository userLoginRepository;
  private final GetUserRepository getJdbcUserRepository;
  private final GetUserRepository getCognitoUserRepository;
  private final SaveUserRepository saveJdbcUserRepository;


  public UserLoginUseCase(UserLoginRepository userLoginRepository,
                          @Qualifier("jdbc") GetUserRepository getJdbcUserRepository,
                          @Qualifier("cognito") GetUserRepository getCognitoUserRepository,
                          @Qualifier("jdbc") SaveUserRepository saveJdbcUserRepository) {
    this.userLoginRepository = userLoginRepository;
    this.getJdbcUserRepository = getJdbcUserRepository;
    this.getCognitoUserRepository = getCognitoUserRepository;
    this.saveJdbcUserRepository = saveJdbcUserRepository;
  }

  @Override
  public UserSessionTokens login(String email, String password) {
    User user = this.getCognitoUserRepository.get(email);
    Boolean existsInDb = userExistsInRepository(getJdbcUserRepository, email);
    Boolean existsInCognito = userExistsInRepository(getCognitoUserRepository, email);

    if (Boolean.TRUE.equals(!existsInDb) && Boolean.TRUE.equals(existsInCognito)) {
      this.saveJdbcUserRepository.save(user);
    }
    return this.userLoginRepository.login(user.getUsername(), password);
  }

  private Boolean userExistsInRepository(GetUserRepository getUserRepository, String email) {
    try {
      getUserRepository.get(email);
      return true;
    } catch (UserNotFoundException ex) {
      return false;
    }

  }
}
