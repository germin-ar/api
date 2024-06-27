package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.UserSessionTokens;
import ar.germin.api.application.port.in.UserLoginPortIn;
import ar.germin.api.application.port.out.UserLoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserLoginUseCase implements UserLoginPortIn {

  private final UserLoginRepository userLoginRepository;

  public UserLoginUseCase(UserLoginRepository userLoginRepository) {
    this.userLoginRepository = userLoginRepository;
  }

  @Override
  public UserSessionTokens login(String username, String password) {
    return this.userLoginRepository.login(username, password);
  }
}
