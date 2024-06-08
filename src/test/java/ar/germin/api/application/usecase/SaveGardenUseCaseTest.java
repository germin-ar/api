package ar.germin.api.application.usecase;

import ar.germin.api.application.port.out.SaveGardenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaveGardenUseCaseTest {

    private final SaveGardenRepository saveGardenRepository = mock(SaveGardenRepository.class);

    @Test
    void testSaveGardenWithValidIdAndNameReturnsTrue() {
        when(saveGardenRepository.save(1, "GardenName")).thenReturn(true);

        SaveGardenUseCase useCase = new SaveGardenUseCase(saveGardenRepository);

        Boolean candidateResults = useCase.save(1, "GardenName");

        Assertions.assertTrue(candidateResults);
    }

    @Test
    void testSaveGardenWithNullNameReturnsFalse() {
        when(saveGardenRepository.save(1, null)).thenReturn(false);

        SaveGardenUseCase useCase = new SaveGardenUseCase(saveGardenRepository);

        Boolean candidateResults = useCase.save(1, null);

        Assertions.assertFalse(candidateResults);
    }

    @Test
    void testSaveGardenWithEmptyNameReturnsFalse() {
        when(saveGardenRepository.save(1, "")).thenReturn(false);

        SaveGardenUseCase useCase = new SaveGardenUseCase(saveGardenRepository);

        Boolean candidateResults = useCase.save(1, "");

        Assertions.assertFalse(candidateResults);
    }

    @Test
    void testSaveGardenWithNullUserIdReturnsFalse() {
        when(saveGardenRepository.save(null, "GardenName")).thenReturn(false);

        SaveGardenUseCase useCase = new SaveGardenUseCase(saveGardenRepository);

        Boolean candidateResults = useCase.save(null, "GardenName");

        Assertions.assertFalse(candidateResults);
    }

}
