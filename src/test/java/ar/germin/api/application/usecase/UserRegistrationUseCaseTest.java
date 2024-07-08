package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.exceptions.UserNotFoundException;
import ar.germin.api.application.port.out.DeleteUserRepository;
import ar.germin.api.application.port.out.GetUserRepository;
import ar.germin.api.application.port.out.SaveUserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserRegistrationUseCaseTest {
/*
    @Test
    void testSignUp_UserAlreadyExistsInJdbcAndCognito() throws Exception {

        String username = "testuser";
        String password = "password";
        String email = "test@example.com";

        GetUserRepository jdbcUserRepository = new GetUserRepository() {
            @Override
            public User get(String email) {
                if ("test@example.com".equals(email)) {
                    return User.builder().id(1).username("testuser").email(email).build();
                } else {
                    throw new UserNotFoundException("User not found");
                }
            }
        };

        GetUserRepository cognitoUserRepository = new GetUserRepository() {
            @Override
            public User get(String email) {
                if ("test@example.com".equals(email)) {
                    return User.builder().id(1).username("testuser").email(email).build();
                } else {
                    throw new UserNotFoundException("User not found");
                }
            }
        };

        SaveUserRepository saveJdbcUserRepository = new SaveUserRepository() {
            @Override
            public void save(User user) {

            }
        };

        SaveUserRepository saveCognitoUserRepository = new SaveUserRepository() {
            @Override
            public void save(User user) {

            }
        };

        DeleteUserRepository deleteUserRepository = new DeleteUserRepository() {
            @Override
            public void delete(String email) {

            }
        };

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
    }

    @Test
    void testSignUp_UserDoesNotExistInAnyRepository() throws Exception {

        String username = "newuser";
        String password = "password";
        String email = "new@example.com";

        GetUserRepository jdbcUserRepository = new GetUserRepository() {
            @Override
            public User get(String email) {
                throw new UserNotFoundException("User not found");
            }
        };

        GetUserRepository cognitoUserRepository = new GetUserRepository() {
            @Override
            public User get(String email) {
                throw new UserNotFoundException("User not found");
            }
        };

        SaveUserRepository saveJdbcUserRepository = new SaveUserRepository() {
            @Override
            public void save(User user) {

            }
        };

        SaveUserRepository saveCognitoUserRepository = new SaveUserRepository() {
            @Override
            public void save(User user) {

            }
        };

        DeleteUserRepository deleteUserRepository = new DeleteUserRepository() {
            @Override
            public void delete(String email) {

            }
        };

        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(
                jdbcUserRepository,
                cognitoUserRepository,
                saveJdbcUserRepository,
                saveCognitoUserRepository,
                deleteUserRepository
        );


        User newUser = User.builder()
                .username(username)
                .email(email)
                .pass(password)
                .build();

        GetUserRepository finalJdbcUserRepository = new GetUserRepository() {
            @Override
            public User get(String email) {
                return newUser;
            }
        };


        UserRegistrationUseCase updatedUserRegistrationUseCase = new UserRegistrationUseCase(
                finalJdbcUserRepository,
                cognitoUserRepository,
                saveJdbcUserRepository,
                saveCognitoUserRepository,
                deleteUserRepository
        );


        User result = updatedUserRegistrationUseCase.signUp(username, password, email);


        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(username, result.getUsername());
    }
*/
}
