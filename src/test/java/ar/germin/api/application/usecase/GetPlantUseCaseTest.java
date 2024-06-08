package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.User;
import ar.germin.api.application.port.in.GetPlantPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetPlantUseCaseTest {

    private final GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
    private final GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);

    @Test
    void testGetPlantByUserIdAndPlantIdReturnsPlant() {
        User user = mock(User.class);

        Plant plant = mock(Plant.class);

        when(user.getId()).thenReturn(2);
        when(plant.getId()).thenReturn(5);
        when(getPlantRepository.getByIdUserAndIdPlant(2, 5)).thenReturn(plant);

        GetPlantPortIn useCase = new GetPlantUseCase(getPlantRepository);

        Plant plantResult = useCase.get(2, 5);

        Assertions.assertNotNull(plantResult);
        Assertions.assertEquals(5, plantResult.getId());
    }

    @Test
    void testGetPlantByUserIdAndPlantIdReturnsNullWhenNoPlantsExists() {
        User user = mock(User.class);

        when(user.getId()).thenReturn(2);
        when(getPlantRepository.getByIdUserAndIdPlant(2, null)).thenReturn(null);

        GetPlantPortIn useCase = new GetPlantUseCase(getPlantRepository);

        Plant plantResult = useCase.get(2, null);

        Assertions.assertNull(plantResult);
    }

    @Test
    void testGetPlantByUserIdAndPlantIdReturnsNullWhenUserDoesntExist() {
        Plant plant = mock(Plant.class);

        when(plant.getId()).thenReturn(5);
        when(getPlantRepository.getByIdUserAndIdPlant(null, 5)).thenReturn(null);

        GetPlantPortIn useCase = new GetPlantUseCase(getPlantRepository);

        Plant plantResult = useCase.get(null, 5);

        Assertions.assertNull(plantResult);
    }

}
