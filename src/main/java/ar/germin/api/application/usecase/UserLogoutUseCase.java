package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.UserLogoutPortIn;
import ar.germin.api.application.port.out.SetCognitoUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserLogoutUseCase implements UserLogoutPortIn {
  private SetCognitoUserRepository setCognitoUserRepository;

  public UserLogoutUseCase(SetCognitoUserRepository setCognitoUserRepository) {
    this.setCognitoUserRepository = setCognitoUserRepository;
  }

  @Override
  public String logout(String accessToken) {
    return this.setCognitoUserRepository.logout(accessToken);
  }
}
