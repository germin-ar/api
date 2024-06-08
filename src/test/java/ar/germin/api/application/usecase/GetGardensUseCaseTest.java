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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetGardensUseCaseTest {

    private final GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
    private final GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);

    @Test
    void testGetGardensByUserReturnsEmptyListWhenNoGardensExists() {
        when(getGardenRepository.getByUserId(2)).thenReturn(Collections.emptyList());
        when(getPlantRepository.getByIdGardenAndIdUser(anyInt(), eq(2))).thenReturn(Collections.emptyList());

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> gardens = useCase.getGardensByUser(2);

        Assertions.assertEquals(0, gardens.size());
    }

    @Test
    void testGetGardensByUserReturnsCorrectNumberOfGardens() {
        Garden garden1 = Garden.builder()
                .id(1)
                .name("Jardín de rosas")
                .build();

        Garden garden2 = Garden.builder()
                .id(2)
                .name("Terraza")
                .build();

        List<Garden> gardens = List.of(garden1, garden2);

        when(getGardenRepository.getByUserId(2)).thenReturn(gardens);

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> result = useCase.getGardensByUser(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1, result.stream().filter(g -> g.getName().equals("Jardín de rosas")).count());
        Assertions.assertEquals(1, result.stream().filter(g -> g.getName().equals("Terraza")).count());
    }

    @Test
    void testGetGardensByUserWithPlantsReturnsCorrectNumberOfPlants() {

        Garden garden = Garden.builder()
                .id(1)
                .build();

        Plant plant1 = Plant.builder()
                .build();
        Plant plant2 = Plant.builder()
                .build();

        List<Plant> mockPlants = List.of(plant1, plant2);

        when(getGardenRepository.getByUserId(2)).thenReturn(List.of(garden));
        when(getPlantRepository.getByIdGardenAndIdUser(1, 2)).thenReturn(mockPlants);

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> result = useCase.getGardensByUser(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.get(0).getPlants().size());
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
