package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.GetPlantPortIn;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetPlantUseCaseTest {

    private final GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
    private final GetPlantPhotosRepository getPlantPhotosRepository = mock(GetPlantPhotosRepository.class);

    /*@Test
    void testGetPlantByUserIdAndPlantIdReturnsPlant() {
        Plant plant = Plant.builder()
                .id(5)
                .alias("Tomate")
                .build();

        when(getPlantRepository.getByIdUserAndIdPlant(2, 5)).thenReturn(plant);

        GetPlantPortIn useCase = new GetPlantUseCase(getPlantRepository, getPlantPhotosRepository);

        Plant result = useCase.get(2, 5);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.getId());
        Assertions.assertEquals("Tomate", result.getAlias());
    }

    @Test
    void testGetPlantByUserIdAndPlantIdReturnsNullWhenNoPlantsExists() {
        when(getPlantRepository.getByIdUserAndIdPlant(2, null)).thenReturn(null);

        GetPlantPortIn useCase = new GetPlantUseCase(getPlantRepository, getPlantPhotosRepository);

        Plant result = useCase.get(2, null);

        Assertions.assertNull(result);
    }

    @Test
    void testGetPlantByUserIdAndPlantIdReturnsNullWhenUserDoesntExist() {
        when(getPlantRepository.getByIdUserAndIdPlant(null, 5)).thenReturn(null);

        GetPlantPortIn useCase = new GetPlantUseCase(getPlantRepository, getPlantPhotosRepository);

        Plant result = useCase.get(null, 5);

        Assertions.assertNull(result);
    }*/

}
