package ar.germin.api.application.usecase;

class UserLoginUseCaseTest {
/*
    @Test
    void testLogin_UserExistsInCognito() {

        String email = "test@example.com";
        String password = "password";

        User cognitoUser = User.builder()
                .id(1)
                .username("johndoe")
                .email("email@email.com")
                .build();


        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(
                new UserLoginRepository() {
                    @Override
                    public UserSessionTokens login(String username, String password) {
                        return UserSessionTokens.builder()
                                .id("1")
                                .access("accessToken")
                                .refresh("refreshToken")
                                .build();

                    }
                },
                new GetUserRepository() {
                    @Override
                    public User get(String email) {
                        throw new UserNotFoundException("User not found");
                    }
                },
                new GetUserRepository() {
                    @Override
                    public User get(String email) {
                        if ("test@example.com".equals(email)) {
                            return cognitoUser;
                        } else {
                            throw new UserNotFoundException("User not found");
                        }
                    }
                },
                new SaveUserRepository() {
                    @Override
                    public void save(User user) {
                        // No es necesario para este caso de prueba
                    }
                }
        );


        UserSessionTokens result = userLoginUseCase.login(email, password);
        assertEquals("1", result.getId());
        assertEquals("accessToken", result.getAccess());
        assertEquals("refreshToken", result.getRefresh());
    }

    @Test
    void testLogin_UserDoesNotExistInCognito() {

        String email = "nonexistent@example.com";
        String password = "password";


        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(
                new UserLoginRepository() {
                    @Override
                    public UserSessionTokens login(String username, String password) {
                        throw new UserNotFoundException("User not found");
                    }
                },
                new GetUserRepository() {
                    @Override
                    public User get(String email) {
                        throw new UserNotFoundException("User not found");
                    }
                },
                new GetUserRepository() {
                    @Override
                    public User get(String email) {
                        throw new UserNotFoundException("User not found");
                    }
                },
                new SaveUserRepository() {
                    @Override
                    public void save(User user) {
                        // No es necesario para este caso de prueba
                    }
                }
        );


        assertThrows(UserNotFoundException.class, () -> {
            userLoginUseCase.login(email, password);
        });
    }

 */
}

