package ar.germin.api.application.usecase;

import ar.germin.api.application.port.out.LogoutUserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserLogoutUseCaseTest {

    @Test
    void testLogout() {
        // Arrange
        String accessToken = "valid-access-token";

        LogoutUserRepository fakeLogoutUserRepository = new LogoutUserRepository() {
            private String loggedOutToken;

            @Override
            public void logout(String accessToken) {
                loggedOutToken = accessToken;
            }

            public String getLoggedOutToken() {
                return loggedOutToken;
            }
        };

        UserLogoutUseCase userLogoutUseCase = new UserLogoutUseCase(fakeLogoutUserRepository);

        // Act & Assert
        assertDoesNotThrow(() -> userLogoutUseCase.logout(accessToken));
    }
}
