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
        when(saveGardenRepository.save(1, "GardenName")).thenReturn(1);

        SaveGardenUseCase useCase = new SaveGardenUseCase(saveGardenRepository);

        Integer candidateResults = useCase.save(1, "GardenName");

        Assertions.assertEquals(1, candidateResults);
    }

    @Test
    void testSaveGardenWithNullNameReturnsFalse() {
        when(saveGardenRepository.save(1, null)).thenReturn(null);

        SaveGardenUseCase useCase = new SaveGardenUseCase(saveGardenRepository);

        Integer candidateResults = useCase.save(1, null);

        Assertions.assertNull(candidateResults);
    }

    @Test
    void testSaveGardenWithEmptyNameReturnsFalse() {
        when(saveGardenRepository.save(1, "")).thenReturn(1);

        SaveGardenUseCase useCase = new SaveGardenUseCase(saveGardenRepository);

        Integer candidateResults = useCase.save(1, "");

        Assertions.assertEquals(1, candidateResults);
    }

    @Test
    void testSaveGardenWithNullUserIdReturnsFalse() {
        when(saveGardenRepository.save(null, "GardenName")).thenReturn(1);

        SaveGardenUseCase useCase = new SaveGardenUseCase(saveGardenRepository);

        Integer candidateResults = useCase.save(null, "GardenName");

        Assertions.assertEquals(1, candidateResults);
    }

}
