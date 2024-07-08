package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.User;
import ar.germin.api.application.port.out.GetUserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetUserUseCaseTest {

    @Test
    public void testGetByHash() {
        // Arrange
        GetUserRepository getUserJdbcRepository = mock(GetUserRepository.class);
        GetUserRepository getUserCognitoRepository = mock(GetUserRepository.class);
        GetUSerUseCase getUserUseCase = new GetUSerUseCase(getUserJdbcRepository, getUserCognitoRepository);

        String userHash = "sampleHash";
        User expectedUser = User.builder().name("testUser").build();

        when(getUserCognitoRepository.getByHash(userHash)).thenReturn(expectedUser);

        // Act
        User actualUser = getUserUseCase.getByHash(userHash);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(getUserCognitoRepository).getByHash(userHash);
        verifyNoInteractions(getUserJdbcRepository);
    }


}
