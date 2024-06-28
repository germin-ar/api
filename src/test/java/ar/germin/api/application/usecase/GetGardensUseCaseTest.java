package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.GetGardensPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetGardensUseCaseTest {

    private final GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
    private final GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
    private final GetPlantPhotosRepository getPlantPhotosRepositor = mock(GetPlantPhotosRepository.class);
    @InjectMocks
    private GetGardensUseCase getGardensUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetGardensByUserReturnsEmptyListWhenNoGardensExists() {
        when(getGardenRepository.getByUserId(2)).thenReturn(Collections.emptyList());
        when(getPlantRepository.getByIdGardenAndIdUser(anyInt(), eq(2))).thenReturn(Collections.emptyList());

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository, getPlantPhotosRepositor);

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

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository, getPlantPhotosRepositor);

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

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository, getPlantPhotosRepositor);

        List<Garden> result = useCase.getGardensByUser(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.get(0).getPlants().size());
        Assertions.assertEquals(1, result.get(0).getPlants().stream().filter(p -> p.getAlias().equals("Tomate")).count());
        Assertions.assertEquals(1, result.get(0).getPlants().stream().filter(p -> p.getAlias().equals("Lechuga")).count());
    }

    @Test
    void testGetPlantsWithoutGardenByUserReturnsCorrectNumberOfPlants() {
        // Mock data
        Plant plant1 = Plant.builder().id(1).build();
        Plant plant2 = Plant.builder().id(2).build();

        List<Plant> plantsWithoutGarden = List.of(plant1, plant2);
        when(getPlantRepository.getByIdUser(2)).thenReturn(plantsWithoutGarden);
        when(getGardenRepository.getByUserId(2)).thenReturn(new ArrayList<>()); // No gardens associated

        // Call the method under test
        List<Garden> result = getGardensUseCase.getGardensByUser(2);

        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty(), "Expected non-empty list of gardens");

        // Assert that the last garden in the list is the fake garden
        Garden fakeGarden = result.get(result.size() - 1);

        // Check if all plants from plantsWithoutGarden are present in the fake garden
        for (Plant expectedPlant : plantsWithoutGarden) {
            boolean found = false;
            for (Plant actualPlant : fakeGarden.getPlants()) {
                if (expectedPlant.getId().equals(actualPlant.getId())) {
                    found = true;
                    break;
                }
            }
            Assertions.assertTrue(found, "Expected plant " + expectedPlant.getId() + " in the fake garden");
        }

        Assertions.assertEquals(plantsWithoutGarden.size(), fakeGarden.getPlants().size(),
                "Expected correct number of plants in the fake garden");
    }
    @Test
    void testGetGardensByUserWithoutPlantsReturnsEmptyList() {
        Garden garden = Garden.builder().build();

        when(getGardenRepository.getByUserId(2)).thenReturn(List.of(garden));
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(null);

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository, getPlantPhotosRepositor);

        List<Garden> result = useCase.getGardensByUser(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(Collections.emptyList(), result.get(0).getPlants());
    }
}
