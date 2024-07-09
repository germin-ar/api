package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.domain.UserSessionTokens;
import ar.germin.api.application.exceptions.UserNotFoundException;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import ar.germin.api.application.port.out.UserLoginRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserLoginUseCaseTest {

    @Test
    void testLogin_UserExistsInCognito() {
        // Arrange
        String email = "test@example.com";
        String password = "password";

        User cognitoUser = User.builder()
                .id(1)
                .username("johndoe")
                .email(email)
                .build();

        UserSessionTokens tokens = UserSessionTokens.builder()
                .id("1")
                .access("accessToken")
                .refresh("refreshToken")
                .build();

        UserLoginRepository userLoginRepository = mock(UserLoginRepository.class);
        GetUserRepository getJdbcUserRepository = mock(GetUserRepository.class);
        GetUserRepository getCognitoUserRepository = mock(GetUserRepository.class);
        SaveUserRepository saveJdbcUserRepository = mock(SaveUserRepository.class);

        when(getCognitoUserRepository.get(email)).thenReturn(cognitoUser);
        when(getJdbcUserRepository.get(email)).thenThrow(new UserNotFoundException("User not found"));
        when(userLoginRepository.login(cognitoUser.getUsername(), password)).thenReturn(tokens);

        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(
                userLoginRepository,
                getJdbcUserRepository,
                getCognitoUserRepository,
                saveJdbcUserRepository
        );

        // Act
        UserSessionTokens result = userLoginUseCase.login(email, password);

        // Assert
        verify(saveJdbcUserRepository, times(1)).save(cognitoUser);
        assertEquals("1", result.getId());
        assertEquals("accessToken", result.getAccess());
        assertEquals("refreshToken", result.getRefresh());
    }
    @Test
    void testLogin_UserDoesNotExistInCognito() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password";

        UserLoginRepository userLoginRepository = mock(UserLoginRepository.class);
        GetUserRepository getJdbcUserRepository = mock(GetUserRepository.class);
        GetUserRepository getCognitoUserRepository = mock(GetUserRepository.class);
        SaveUserRepository saveJdbcUserRepository = mock(SaveUserRepository.class);

        when(getCognitoUserRepository.get(email)).thenThrow(new UserNotFoundException("User not found"));

        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(
                userLoginRepository,
                getJdbcUserRepository,
                getCognitoUserRepository,
                saveJdbcUserRepository
        );

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userLoginUseCase.login(email, password);
        });

        verify(saveJdbcUserRepository, never()).save(any(User.class));
        verify(userLoginRepository, never()).login(anyString(), anyString());
    }
}

