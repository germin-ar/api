package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.ErrorConfirmAccountException;
import ar.germin.api.application.port.out.ConfirmUserRepository;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.UpdateRoleRepository;
import ar.germin.api.application.port.out.UpdateUserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserConfirmRegistrationUseCaseTest {
/*
    @Test
    void testConfirmSignUp_Success() {

        String email = "test@example.com";
        String confirmationCode = "123456";

        // Mock de GetUserRepository que devuelve un usuario específico para el email dado
        GetUserRepository userRepository = emailParam -> {
            if (email.equals(emailParam)) {
                return User.builder()
                        .id(1)
                        .email(email)
                        .name("John Doe")
                        .username("johndoe")
                        .pass("password")
                        .isConfirmed(true)
                        .build();
            }
            return null;
        };

        // Mock de ConfirmUserRepository que simula la confirmación de usuario
        ConfirmUserRepository confirmRepository = (username, code) -> {
            // Simulación de lógica de confirmación
            if ("123456".equals(code)) {

            } else {
                throw new ErrorConfirmAccountException("Código de confirmación inválido");
            }
        };

        // Mock de UpdateUserRepository simulado
        UpdateUserRepository updateUserRepository = emailParam -> {

            System.out.println("Usuario actualizado: " + emailParam);
        };

        // Mock de UpdateRoleRepository simulado
        UpdateRoleRepository updateRoleRepository = (username, role) -> {

            System.out.println("Rol actualizado para usuario " + username + " a " + role);
        };

        // Crear instancia del caso de uso con los mocks de repositorio
        UserConfirmRegistrationUseCase userConfirmRegistrationUseCase =
                new UserConfirmRegistrationUseCase(userRepository, confirmRepository,
                        updateUserRepository, updateRoleRepository);


        User confirmedUser = userConfirmRegistrationUseCase.confirmSignUp(email, confirmationCode);


        assertNotNull(confirmedUser);
        assertTrue(confirmedUser.getIsConfirmed(), "El usuario devuelto debe estar confirmado");

        // Verificar en el repositorio simulado
        User userFromRepository = userRepository.get(email);
        assertNotNull(userFromRepository);
        assertTrue(userFromRepository.getIsConfirmed(), "El estado de confirmación en el repositorio debe estar actualizado");
    }

    @Test
    void testConfirmSignUp_UserAlreadyConfirmed() {

        String email = "test@example.com";
        String confirmationCode = "123456";

        // Crear un GetUserRepository que devuelve un usuario ya confirmado
        GetUserRepository userRepository = emailParam -> {
            if (email.equals(emailParam)) {
                return User.builder()
                        .id(1)
                        .email(email)
                        .name("John Doe")
                        .username("johndoe")
                        .pass("password")
                        .isConfirmed(true) // Usuario ya confirmado
                        .build();
            }
            return null;
        };

        // Crear un ConfirmUserRepository simulado
        ConfirmUserRepository confirmRepository = (username, code) -> {
            throw new ErrorConfirmAccountException("Usuario ya confirmado");
        };

        // Crear un UpdateUserRepository simulado
        UpdateUserRepository updateUserRepository = emailParam -> {
            System.out.println("Usuario actualizado: " + emailParam);
        };

        // Crear un UpdateRoleRepository simulado
        UpdateRoleRepository updateRoleRepository = (username, role) -> {
            System.out.println("Rol actualizado para usuario " + username + " a " + role);
        };

        // Crear instancia del caso de uso con las implementaciones reales de repositorio
        UserConfirmRegistrationUseCase userConfirmRegistrationUseCase =
                new UserConfirmRegistrationUseCase(userRepository, confirmRepository,
                        updateUserRepository, updateRoleRepository);


        User confirmedUser = userConfirmRegistrationUseCase.confirmSignUp(email, confirmationCode);


        assertNotNull(confirmedUser);
        assertTrue(confirmedUser.getIsConfirmed());
    }
*/

}
