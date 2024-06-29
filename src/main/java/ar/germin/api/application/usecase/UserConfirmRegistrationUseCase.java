package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.ErrorConfirmAccountException;
import ar.germin.api.application.port.in.UserConfirmRegistrationPortIn;
import ar.germin.api.application.port.out.ConfirmUserRepository;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.UpdateRoleRepository;
import ar.germin.api.application.port.out.UpdateUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserConfirmRegistrationUseCase implements UserConfirmRegistrationPortIn {
  private final ConfirmUserRepository confirmUserRepository;
  private final UpdateUserRepository updateJdbcUserRepository;
  private final GetUserRepository getJdbcUserRepository;
  private final UpdateRoleRepository updateRoleRepository;

  public UserConfirmRegistrationUseCase(
          @Qualifier("jdbc") GetUserRepository getJdbcUserRepository,
          ConfirmUserRepository confirmUserRepository,
          UpdateUserRepository updateJdbcUserRepository,
          UpdateRoleRepository updateRoleRepository) {
    this.confirmUserRepository = confirmUserRepository;
    this.updateJdbcUserRepository = updateJdbcUserRepository;
    this.getJdbcUserRepository = getJdbcUserRepository;
    this.updateRoleRepository = updateRoleRepository;
  }

  @Override
  public User confirmSignUp(String email, String confirmationCode) {
    try {
      User user = getJdbcUserRepository.get(email);
      if (!user.getIsConfirmed()) {
        this.updateRoleRepository.addUserToGroup(user.getName(), "FREE_USER");
        updateUserRepositories(user, confirmationCode);
        log.info("Confirm account successful");
        return this.getJdbcUserRepository.get(email);
      } else {
        return this.getJdbcUserRepository.get(email);
//        throw new UserIsConfirmedException("User already confirmed");
      }
    } catch (ErrorConfirmAccountException ex) {
      throw new ErrorConfirmAccountException("Error confirming account for email " + email);
    }
  }

  private void updateUserRepositories(User user, String confirmationCode) {
    this.confirmUserRepository.confirm(user.getName(), confirmationCode);
    this.updateJdbcUserRepository.update(user.getEmail());
  }

}
