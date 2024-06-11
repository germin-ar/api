package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.SaveGardenPortIn;
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

        SaveGardenPortIn useCase = new SaveGardenUseCase(saveGardenRepository);

        Boolean result = useCase.save(1, "GardenName");

        Assertions.assertTrue(result);
    }

    @Test
    void testSaveGardenWithNullNameReturnsFalse() {
        when(saveGardenRepository.save(1, null)).thenReturn(false);

        SaveGardenPortIn useCase = new SaveGardenUseCase(saveGardenRepository);

        Boolean result = useCase.save(1, null);

        Assertions.assertFalse(result);
    }

    @Test
    void testSaveGardenWithEmptyNameReturnsFalse() {
        when(saveGardenRepository.save(1, "")).thenReturn(false);

        SaveGardenPortIn useCase = new SaveGardenUseCase(saveGardenRepository);

        Boolean result = useCase.save(1, "");

        Assertions.assertFalse(result);
    }

    @Test
    void testSaveGardenWithNullUserIdReturnsFalse() {
        when(saveGardenRepository.save(null, "GardenName")).thenReturn(false);

        SaveGardenPortIn useCase = new SaveGardenUseCase(saveGardenRepository);

        Boolean result = useCase.save(null, "GardenName");

        Assertions.assertFalse(result);
    }

}
