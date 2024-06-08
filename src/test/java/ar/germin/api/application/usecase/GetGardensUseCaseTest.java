package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.User;
import ar.germin.api.application.port.in.GetGardensPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetGardensUseCaseTest {

    private final GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
    private final GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);

    @Test
    void testGetGardensByUserReturnsEmptyListWhenNoGardensExists() {
        when(getGardenRepository.getByUserId(2)).thenReturn(Collections.emptyList());
        when(getPlantRepository.getByIdUser(2)).thenReturn(Collections.emptyList());

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> gardens = useCase.getGardensByUser(2);

        Assertions.assertEquals(0, gardens.size());
    }

    @Test
    void testGetGardensByUserReturnsCorrectNumberOfGardens() {
        Garden garden1 = mock(Garden.class);
        Garden garden2 = mock(Garden.class);
        List<Garden> mockGardens = new ArrayList<>();
        mockGardens.add(garden1);
        mockGardens.add(garden2);

        when(getGardenRepository.getByUserId(2)).thenReturn(mockGardens);

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> gardens = useCase.getGardensByUser(2);

        Assertions.assertNotNull(gardens);
        Assertions.assertEquals(2, gardens.size());
    }

    @Test
    void testGetGardensByUserWithPlantsReturnsCorrectNumberOfPlants() {
        User user = mock(User.class);

        Garden garden = mock(Garden.class);
        List<Garden> mockGardens = new ArrayList<>();
        mockGardens.add(garden);

        Plant plant1 = mock(Plant.class);
        Plant plant2 = mock(Plant.class);
        List<Plant> mockPlants = new ArrayList<>();
        mockPlants.add(plant1);
        mockPlants.add(plant2);

        when(user.getId()).thenReturn(2);
        when(garden.getId()).thenReturn(5);
        when(getGardenRepository.getByUserId(2)).thenReturn(mockGardens);
        when(garden.withPlants(mockPlants)).thenReturn(mockGardens.get(0));
        when(mockGardens.get(0).getPlants()).thenReturn(mockPlants);
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(mockPlants);

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> gardensResult = useCase.getGardensByUser(2);

        Assertions.assertNotNull(gardensResult);
        Assertions.assertEquals(2, gardensResult.get(0).getPlants().size());
    }

    @Test
    void testGetGardensByUserWithPlantsWithoutGardenReturnsCorrectNumberOfPlants() {
        User user = mock(User.class);

        Plant plant1 = mock(Plant.class);
        Plant plant2 = mock(Plant.class);
        List<Plant> mockPlants = new ArrayList<>();
        mockPlants.add(plant1);
        mockPlants.add(plant2);

        when(user.getId()).thenReturn(2);
        when(plant1.getId()).thenReturn(8);
        when(plant2.getId()).thenReturn(9);
        when(plant1.getIdGarden()).thenReturn(null);
        when(plant2.getIdGarden()).thenReturn(null);
        when(getPlantRepository.getByIdUser(2)).thenReturn(mockPlants);

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> gardensResult = useCase.getGardensByUser(2);

        Assertions.assertNotNull(gardensResult);
        Assertions.assertEquals(2, gardensResult.get(0).getPlants().size());
    }

    @Test
    void testGetGardensByUserWithoutPlantsReturnsEmptyList() {
        User user = mock(User.class);

        Garden garden = mock(Garden.class);
        List<Garden> mockGardens = new ArrayList<>();
        mockGardens.add(garden);

        List<Plant> mockPlants = new ArrayList<>();

        when(user.getId()).thenReturn(2);
        when(garden.getId()).thenReturn(5);
        when(getGardenRepository.getByUserId(2)).thenReturn(mockGardens);
        when(garden.withPlants(mockPlants)).thenReturn(mockGardens.get(0));
        when(mockGardens.get(0).getPlants()).thenReturn(mockPlants);
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(mockPlants);

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> gardensResult = useCase.getGardensByUser(2);

        Assertions.assertNotNull(gardensResult);
        Assertions.assertEquals(Collections.emptyList(), gardensResult.get(0).getPlants());
    }
}
