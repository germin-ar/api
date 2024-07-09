package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.UserNotFoundException;
import ar.germin.api.application.port.out.DeleteUserRepository;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserRegistrationUseCaseTest {

    @Test
    void testSignUp_UserAlreadyExistsInJdbcAndCognito() throws Exception {

        String username = "testuser";
        String password = "password";
        String email = "test@example.com";

        User existingUser = User.builder()
                .id(1)
                .username(username)
                .email(email)
                .build();

        GetUserRepository jdbcUserRepository = mock(GetUserRepository.class);
        GetUserRepository cognitoUserRepository = mock(GetUserRepository.class);
        SaveUserRepository saveJdbcUserRepository = mock(SaveUserRepository.class);
        SaveUserRepository saveCognitoUserRepository = mock(SaveUserRepository.class);
        DeleteUserRepository deleteUserRepository = mock(DeleteUserRepository.class);

        when(jdbcUserRepository.get(email)).thenReturn(existingUser);
        when(cognitoUserRepository.get(email)).thenReturn(existingUser);

        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(
                jdbcUserRepository,
                cognitoUserRepository,
                saveJdbcUserRepository,
                saveCognitoUserRepository,
                deleteUserRepository
        );


        User result = userRegistrationUseCase.signUp(username, password, email);


        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(username, result.getUsername());
        verify(deleteUserRepository, never()).delete(anyString());
        verify(saveJdbcUserRepository, never()).save(any(User.class));
        verify(saveCognitoUserRepository, never()).save(any(User.class));
    }


 /*
    @Test
    void testSignUp_UserDoesNotExistInAnyRepository() throws Exception {
        // Datos de prueba
        String username = "newuser";
        String password = "password";
        String email = "new@example.com";

        // Mock de GetUserRepository para JDBC que lanza UserNotFoundException
        GetUserRepository jdbcUserRepository = mock(GetUserRepository.class);
        when(jdbcUserRepository.get(email)).thenThrow(new UserNotFoundException("User not found"));

        // Mock de GetUserRepository para Cognito que lanza UserNotFoundException
        GetUserRepository cognitoUserRepository = mock(GetUserRepository.class);
        when(cognitoUserRepository.get(email)).thenThrow(new UserNotFoundException("User not found"));

        // Mock de SaveUserRepository para verificar llamadas sin implementación real
        SaveUserRepository saveJdbcUserRepository = mock(SaveUserRepository.class);

        // Mock de SaveUserRepository para verificar llamadas sin implementación real
        SaveUserRepository saveCognitoUserRepository = mock(SaveUserRepository.class);

        // Mock de DeleteUserRepository para verificar que no se llame a delete()
        DeleteUserRepository deleteUserRepository = mock(DeleteUserRepository.class);

        // Crear instancia del caso de uso con los mocks de los repositorios
        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(
                jdbcUserRepository,
                cognitoUserRepository,
                saveJdbcUserRepository,
                saveCognitoUserRepository,
                deleteUserRepository
        );

        // Ejecutar el método bajo prueba
        User result = userRegistrationUseCase.signUp(username, password, email);

        // Verificar resultados esperados
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(username, result.getUsername());

        // Verificar que deleteUserRepository.delete() nunca se llamó
        verify(deleteUserRepository, never()).delete(anyString());

        // Verificar que saveJdbcUserRepository.save() se llamó exactamente una vez con cualquier usuario
        verify(saveJdbcUserRepository, times(1)).save(any(User.class));

        // Verificar que saveCognitoUserRepository.save() se llamó exactamente una vez con cualquier usuario
        verify(saveCognitoUserRepository, times(1)).save(any(User.class));
    }




    @Test
    void testSignUp_UserExistsInJdbcButNotInCognito() throws Exception {
        // Datos de prueba
        String username = "testuser";
        String password = "password";
        String email = "test@example.com";

        // Usuario existente en JDBC
        User existingJdbcUser = User.builder()
                .id(1)
                .username(username)
                .email(email)
                .build();

        // Mock de GetUserRepository para JDBC que devuelve el usuario existente
        GetUserRepository jdbcUserRepository = mock(GetUserRepository.class);
        when(jdbcUserRepository.get(email)).thenReturn(existingJdbcUser);

        // Mock de GetUserRepository para Cognito que lanza UserNotFoundException
        GetUserRepository cognitoUserRepository = mock(GetUserRepository.class);
        when(cognitoUserRepository.get(email)).thenThrow(new UserNotFoundException("User not found"));

        // Mock de SaveUserRepository para verificar llamadas sin implementación real
        SaveUserRepository saveJdbcUserRepository = mock(SaveUserRepository.class);

        // Mock de SaveUserRepository para verificar llamadas sin implementación real
        SaveUserRepository saveCognitoUserRepository = mock(SaveUserRepository.class);

        // Mock de DeleteUserRepository para verificar llamadas sin implementación real
        DeleteUserRepository deleteUserRepository = mock(DeleteUserRepository.class);

        // Crear instancia del caso de uso con los mocks de los repositorios
        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(
                jdbcUserRepository,
                cognitoUserRepository,
                saveJdbcUserRepository,
                saveCognitoUserRepository,
                deleteUserRepository
        );

        // Ejecutar el método bajo prueba
        User result = userRegistrationUseCase.signUp(username, password, email);

        // Verificar resultados esperados
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(username, result.getUsername());

        // Verificar llamadas a repositorios
        verify(deleteUserRepository, times(1)).delete(email);
        verify(saveJdbcUserRepository, times(1)).save(any(User.class));
        verify(saveCognitoUserRepository, times(1)).save(any(User.class));
    }

     */
}
