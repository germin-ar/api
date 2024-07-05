package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.UserLogoutPortIn;
import ar.germin.api.application.port.out.LogoutUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserLogoutUseCase implements UserLogoutPortIn {
  private final LogoutUserRepository logoutUserRepository;

  public UserLogoutUseCase(LogoutUserRepository logoutUserRepository) {
    this.logoutUserRepository = logoutUserRepository;
  }

  @Override
  public void logout(String accessToken) {
     this.logoutUserRepository.logout(accessToken);
  }
}
