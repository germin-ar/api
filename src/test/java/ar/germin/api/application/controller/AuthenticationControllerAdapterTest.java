package ar.germin.api.application.controller;

import ar.germin.api.adapter.controller.AuthenticationControllerAdapter;
import ar.germin.api.adapter.controller.models.ConfirmSignupRequestModel;
import ar.germin.api.adapter.controller.models.LoginUserRequestModel;
import ar.germin.api.adapter.controller.models.LoginUserResponseModel;
import ar.germin.api.adapter.controller.models.SignupRequestModel;
import ar.germin.api.adapter.controller.models.UserResponseModel;
import ar.germin.api.application.domain.User;
import ar.germin.api.application.domain.UserSessionTokens;
import ar.germin.api.application.port.in.*;
import ar.germin.api.application.port.in.getUserPortIn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthenticationControllerAdapterTest {

    @Test
    public void testSignUp() throws Exception {
        // Creación de instancias directas de los puertos de entrada
        UserRegistrationPortIn registrationPortIn = new UserRegistrationPortIn() {
            @Override
            public User signUp(String username, String password, String email) throws Exception {
                // Simulamos una respuesta de usuario registrado
                return User.builder()
                        .name("username")
                        .email("email@example.com")
                        .build();
            }
        };

        UserConfirmRegistrationPortIn confirmRegistrationPortIn = new UserConfirmRegistrationPortIn() {
            @Override
            public User confirmSignUp(String email, String confirmationCode) {
                // Simulamos una confirmación de registro exitosa
                return User.builder()
                        .name("username")
                        .email("email@example.com")
                        .build();
            }
        };

        UserLoginPortIn loginPortIn = new UserLoginPortIn() {
            @Override
            public UserSessionTokens login(String username, String password) {
                return null;
            }
        };

        UserLogoutPortIn logoutPortIn = new UserLogoutPortIn() {
            @Override
            public void logout(String username) {
            }
        };

        getUserPortIn getUserPortIn = new getUserPortIn() {
            @Override
            public User getByHash(String hash) {
                return User.builder()
                        .name("username")
                        .email("email@example.com")
                        .build();
            }
        };

        AuthenticationControllerAdapter controller = new AuthenticationControllerAdapter(
                registrationPortIn,
                confirmRegistrationPortIn,
                loginPortIn,
                logoutPortIn,
                getUserPortIn);

        // Datos de prueba
        SignupRequestModel signupRequestModel = new SignupRequestModel("username", "password", "email@example.com");

        // Llamada al método del controlador
        UserResponseModel response = controller.signUp(signupRequestModel);

        // Verificación de la respuesta
        assertEquals("username", response.getName());
        assertEquals("email@example.com", response.getEmail());
    }

    @Test
    public void testConfirmSignUp() {
        // Creación de instancias directas de los puertos de entrada
        UserRegistrationPortIn registrationPortIn = new UserRegistrationPortIn() {
            @Override
            public User signUp(String username, String password, String email) throws Exception {
                // Simulamos una respuesta de usuario registrado
                return User.builder()
                        .name("username")
                        .email("email@example.com")
                        .build();
            }
        };

        UserConfirmRegistrationPortIn confirmRegistrationPortIn = new UserConfirmRegistrationPortIn() {
            @Override
            public User confirmSignUp(String email, String confirmationCode) {
                // Simulamos una confirmación de registro exitosa
                return User.builder()
                        .name("username")
                        .email("email@example.com")
                        .build();
            }
        };

        UserLoginPortIn loginPortIn = new UserLoginPortIn() {
            @Override
            public UserSessionTokens login(String username, String password) {
                return null;
            }
        };

        UserLogoutPortIn logoutPortIn = new UserLogoutPortIn() {
            @Override
            public void logout(String username) {
            }
        };

        getUserPortIn getUserPortIn = new getUserPortIn() {
            @Override
            public User getByHash(String hash) {
                return User.builder()
                        .name("username")
                        .email("email@example.com")
                        .build();
            }
        };

        AuthenticationControllerAdapter controller = new AuthenticationControllerAdapter(
                registrationPortIn,
                confirmRegistrationPortIn,
                loginPortIn,
                logoutPortIn,
                getUserPortIn);

        // Datos de prueba
        ConfirmSignupRequestModel confirmRequestModel = new ConfirmSignupRequestModel("email@example.com", "123456");

        // Llamada al método del controlador
        UserResponseModel response = controller.confirmSignUp(confirmRequestModel);

        // Verificación de la respuesta
        assertEquals("username", response.getName());
        assertEquals("email@example.com", response.getEmail());
    }

    @Test
    public void testLogin() {
        UserRegistrationPortIn userRegistrationPortIn = mock(UserRegistrationPortIn.class);
        UserConfirmRegistrationPortIn userConfirmRegistrationPortIn = mock(UserConfirmRegistrationPortIn.class);
        UserLoginPortIn userLoginPortIn = mock(UserLoginPortIn.class);
        UserLogoutPortIn userLogoutPortIn = mock(UserLogoutPortIn.class);
        getUserPortIn getUserPortIn = mock(getUserPortIn.class);

        AuthenticationControllerAdapter controller = new AuthenticationControllerAdapter(
                userRegistrationPortIn,
                userConfirmRegistrationPortIn,
                userLoginPortIn,
                userLogoutPortIn,
                getUserPortIn);

        UserSessionTokens userSessionTokens = UserSessionTokens.builder()
                .id("123")
                .access("accessToken")
                .refresh("refreshToken")
                .build();

        when(userLoginPortIn.login(anyString(), anyString()))
                .thenReturn(userSessionTokens);

        LoginUserRequestModel loginUserRequestModel = new LoginUserRequestModel("test@example.com", "password");
        LoginUserResponseModel response = controller.login(loginUserRequestModel);

        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void testLogout() {
        UserRegistrationPortIn userRegistrationPortIn = mock(UserRegistrationPortIn.class);
        UserConfirmRegistrationPortIn userConfirmRegistrationPortIn = mock(UserConfirmRegistrationPortIn.class);
        UserLoginPortIn userLoginPortIn = mock(UserLoginPortIn.class);
        UserLogoutPortIn userLogoutPortIn = mock(UserLogoutPortIn.class);
        getUserPortIn getUserPortIn = mock(getUserPortIn.class);

        AuthenticationControllerAdapter controller = new AuthenticationControllerAdapter(
                userRegistrationPortIn,
                userConfirmRegistrationPortIn,
                userLoginPortIn,
                userLogoutPortIn,
                getUserPortIn);


        controller.logout("Bearer token");
    }
}
