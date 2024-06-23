package ar.germin.api.application.usecase;

import ar.germin.api.adapter.cognito.ManagmentCognitoAdapter;
import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.ErrorConfirmAccountException;
import ar.germin.api.application.port.in.UserConfirmRegistrationPortIn;

import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.UpdateCognitoUserRepository;
import ar.germin.api.application.port.out.UpdateRoleRepository;
import ar.germin.api.application.port.out.UpdateUserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UserConfirmRegistrationUseCase implements UserConfirmRegistrationPortIn {

  private final UpdateCognitoUserRepository updateCognitoUserRepository;
  private final UpdateUserRepository updateJdbcUserRepository;
  private final GetUserRepository getJdbcUserRepository;
  private final UpdateRoleRepository updateRoleRepository;
  public UserConfirmRegistrationUseCase(
          @Qualifier("jdbc") GetUserRepository getJdbcUserRepository,
          UpdateCognitoUserRepository updateCognitoUserRepository,
          UpdateUserRepository updateJdbcUserRepository, UpdateRoleRepository updateRoleRepository) {
    this.updateCognitoUserRepository = updateCognitoUserRepository;
    this.updateJdbcUserRepository = updateJdbcUserRepository;
    this.getJdbcUserRepository = getJdbcUserRepository;
    this.updateRoleRepository = updateRoleRepository;
  }

  @Override
  public String confirmSignUp(String email, String confirmationCode) {
    try {
      User user = getUserByEmail(email);
      if (user.getIsConfirmed()) {
        return "User already confirmed";
      }
      updateUserRepositories(user, confirmationCode);
      this.updateRoleRepository.addUserToGroup(user.getName(),"FREE");
      return "Confirm account successful";
    } catch (ErrorConfirmAccountException ex) {
      throw new ErrorConfirmAccountException("Error confirming account for email " + email);
    }
  }

  private User getUserByEmail(String email) {
    return this.getJdbcUserRepository.get(email)
            .orElseThrow(() -> new ErrorConfirmAccountException("User not found"));
  }

  private void updateUserRepositories(User user, String confirmationCode) {
    this.updateJdbcUserRepository.update(user.getEmail());
    this.updateCognitoUserRepository.update(user.getName(), confirmationCode);
  }

}
