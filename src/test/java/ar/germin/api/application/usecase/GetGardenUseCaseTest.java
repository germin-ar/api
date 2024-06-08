package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.GetGardenPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetGardenUseCaseTest {

    private final GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
    private final GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);

    @Test
    void testGetGardenByIdReturnGarden() {
        Garden garden = Garden.builder()
                .id(5)
                .build();

        when(getGardenRepository.getById(5)).thenReturn(garden);

        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository);

        Garden result = useCase.get(5, 2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.getId());
    }

    @Test
    void testGetGardenByIdReturnGardenAndCorrectNumberOfPlants() {
        Garden garden = Garden.builder()
                .id(5)
                .build();

        Plant plant1 = Plant.builder().build();
        Plant plant2 = Plant.builder().build();

        when(getGardenRepository.getById(5)).thenReturn(garden);
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(List.of(plant1, plant2));

        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository);

        Garden result = useCase.get(5, 2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.getId());
        Assertions.assertNotNull(result.getPlants());
        Assertions.assertEquals(2, result.getPlants().size());
        Assertions.assertEquals(plant1, result.getPlants().get(0));
        Assertions.assertEquals(plant2, result.getPlants().get(1));
    }

    @Test
    void testGetGardenByIdWithoutPlantsReturnGardenAndCorrectNumberOfPlants() {
        Garden garden = Garden.builder()
                .id(5)
                .build();

        when(getGardenRepository.getById(5)).thenReturn(garden);
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(Collections.emptyList());

        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository);

        Garden result = useCase.get(5, 2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.getId());
        Assertions.assertEquals(Collections.emptyList(), result.getPlants());
    }


//    @Test
//    void testGetGardenByIdReturnsNullWhenNoGardensExists() {
//        GardenNotFoundException exception = new GardenNotFoundException();
//        when(getGardenRepository.getById(2)).thenThrow(exception);
//        when(getPlantRepository.getByIdGardenAndIdUser(2, 1)).thenThrow(exception);
//
//        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository);
//
//        Garden result;
//        try {
//            result = useCase.get(5, 2);
//        } catch (GardenNotFoundException e) {
//            result = null;
//        }
//            Assertions.assertNull(result);
//    }

}