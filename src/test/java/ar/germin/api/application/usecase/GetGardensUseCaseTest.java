package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.port.in.GetGardensPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetGardensUseCaseTest {

    private final GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
    private final GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);

    @Test
    void testGetGarden() {
        when(getGardenRepository.getByUserId(2)).thenReturn(Collections.emptyList());
        when(getPlantRepository.getByIdUser(2)).thenReturn(Collections.emptyList());

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> gardens = useCase.getGardensByUser(2);

        Assertions.assertEquals(0, gardens.size());
    }
}
