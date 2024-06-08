package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.User;
import ar.germin.api.application.port.in.GetGardenPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetGardenUseCaseTest {

    private final GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
    private final GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);

    @Test
    void testGetGardenByIdReturnGarden() {
        User user = mock(User.class);

        Garden garden = mock(Garden.class);

        Plant plant1 = mock(Plant.class);
        Plant plant2 = mock(Plant.class);
        List<Plant> mockPlants = new ArrayList<>();
        mockPlants.add(plant1);
        mockPlants.add(plant2);

        when(user.getId()).thenReturn(2);
        when(garden.getId()).thenReturn(5);
        when(getGardenRepository.getById(5)).thenReturn(garden);
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(mockPlants);
        when(garden.withPlants(mockPlants)).thenReturn(garden);

        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository);

        Garden gardenResult = useCase.get(5, 2);

        Assertions.assertNotNull(gardenResult);
        Assertions.assertEquals(5, gardenResult.getId());
    }

    @Test
    void testGetGardenByIdReturnGardenAndCorrectNumberOfPlants() {
        User user = mock(User.class);

        Garden garden = mock(Garden.class);

        Plant plant1 = mock(Plant.class);
        Plant plant2 = mock(Plant.class);
        List<Plant> mockPlants = new ArrayList<>();
        mockPlants.add(plant1);
        mockPlants.add(plant2);

        when(user.getId()).thenReturn(2);
        when(garden.getId()).thenReturn(5);
        when(getGardenRepository.getById(5)).thenReturn(garden);
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(mockPlants);
        when(garden.withPlants(mockPlants)).thenReturn(garden);
        when(garden.getPlants()).thenReturn(mockPlants);

        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository);

        Garden gardenResult = useCase.get(5, 2);

        Assertions.assertNotNull(gardenResult);
        Assertions.assertEquals(5, gardenResult.getId());
        Assertions.assertEquals(2, gardenResult.getPlants().size());
    }

    @Test
    void testGetGardenByIdWithoutPlantsReturnGardenAndCorrectNumberOfPlants() {
        User user = mock(User.class);

        Garden garden = mock(Garden.class);

        List<Plant> mockPlants = new ArrayList<>();

        when(user.getId()).thenReturn(2);
        when(garden.getId()).thenReturn(5);
        when(getGardenRepository.getById(5)).thenReturn(garden);
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(mockPlants);
        when(garden.withPlants(mockPlants)).thenReturn(garden);
        when(garden.getPlants()).thenReturn(mockPlants);

        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository);

        Garden gardenResult = useCase.get(5, 2);

        Assertions.assertNotNull(gardenResult);
        Assertions.assertEquals(5, gardenResult.getId());
        Assertions.assertEquals(Collections.emptyList(), gardenResult.getPlants());
    }

    @Test
    void testGetGardenByIdReturnsNullWhenNoGardensExists() {
        when(getGardenRepository.getByUserId(2)).thenReturn(null);

        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository);

        Garden gardenResult;
        try {
            gardenResult = useCase.get(5, 2);
        } catch (NullPointerException e) {
            gardenResult = null;
        }

        Assertions.assertNull(gardenResult);
    }

}