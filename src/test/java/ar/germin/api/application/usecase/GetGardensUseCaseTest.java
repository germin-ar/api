package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.GetGardensPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

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
                .alias("Tomate")
                .build();
        Plant plant2 = Plant.builder()
                .alias("Lechuga")
                .build();

        List<Plant> mockPlants = List.of(plant1, plant2);

        when(getGardenRepository.getByUserId(2)).thenReturn(List.of(garden));
        when(getPlantRepository.getByIdGardenAndIdUser(1, 2)).thenReturn(mockPlants);

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> result = useCase.getGardensByUser(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.get(0).getPlants().size());
        Assertions.assertEquals(1, result.get(0).getPlants().stream().filter(p -> p.getAlias().equals("Tomate")).count());
        Assertions.assertEquals(1, result.get(0).getPlants().stream().filter(p -> p.getAlias().equals("Lechuga")).count());
    }

    @Test
    void testGetPlantsWithoutGardenByUserReturnsCorrectNumberOfPlants() {
        Plant plant1 = Plant.builder().build();
        Plant plant2 = Plant.builder().build();

        when(getPlantRepository.getByIdUser(2)).thenReturn(List.of(plant1, plant2));

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> result = useCase.getGardensByUser(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.get(0).getPlants().size());
        Assertions.assertEquals(plant1, result.get(0).getPlants().get(0));
        Assertions.assertEquals(plant2, result.get(0).getPlants().get(1));

    }

    @Test
    void testGetGardensByUserWithoutPlantsReturnsEmptyList() {
        Garden garden = Garden.builder().build();

        when(getGardenRepository.getByUserId(2)).thenReturn(List.of(garden));
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(null);

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository);

        List<Garden> gardensResult = useCase.getGardensByUser(2);

        Assertions.assertNotNull(gardensResult);
        Assertions.assertEquals(1, gardensResult.size());
        Assertions.assertEquals(Collections.emptyList(), gardensResult.get(0).getPlants());
    }
}
