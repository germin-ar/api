package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.GetGardensPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GetGardensUseCaseTest {

    private final GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
    private final GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
    private final GetPlantPhotosRepository getPlantPhotosRepository = mock(GetPlantPhotosRepository.class);

    @Test
    void testGetGardensByUserReturnsEmptyListWhenNoGardensExists() {
        when(getGardenRepository.getByUserId(2)).thenReturn(Collections.emptyList());
        when(getPlantRepository.getByIdGardenAndIdUser(anyInt(), eq(2))).thenReturn(Collections.emptyList());

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository, getPlantPhotosRepository);

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

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository, getPlantPhotosRepository);

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

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository, getPlantPhotosRepository);

        List<Garden> result = useCase.getGardensByUser(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.get(0).getPlants().size());
        Assertions.assertEquals(1, result.get(0).getPlants().stream().filter(p -> p.getAlias().equals("Tomate")).count());
        Assertions.assertEquals(1, result.get(0).getPlants().stream().filter(p -> p.getAlias().equals("Lechuga")).count());
    }

    @Test
    void testGetPlantsWithoutGardenByUserReturnsCorrectNumberOfPlants() {
        // Plantas sin jardín
        Plant plant1 = Plant.builder()
                .id(1)
                .alias("Plant 1")
                .photos(null) // Asignar photos como null si no tienes fotos para las plantas en este test
                .build();

        Plant plant2 = Plant.builder()
                .id(2)
                .alias("Plant 2")
                .photos(null) // Asignar photos como null si no tienes fotos para las plantas en este test
                .build();

        // Configurar el comportamiento del repositorio mock
        when(getPlantRepository.getByIdUser(2)).thenReturn(List.of(plant1, plant2));

        // Instanciar el caso de uso
        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository, getPlantPhotosRepository);

        // Ejecutar el método a probar
        List<Garden> result = useCase.getGardensByUser(2);

        // Verificaciones
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.get(0).getPlants().size());

        // Verificar que las fotos sean null o vacías
        for (Plant plant : result.get(0).getPlants()) {
            Assertions.assertTrue(plant.getPhotos() == null || plant.getPhotos().isEmpty());
        }

        // Verificar que se haya llamado al método getByIdUser del repositorio mock
        verify(getPlantRepository, times(1)).getByIdUser(2);
    }

    @Test
    void testGetGardensByUserWithoutPlantsReturnsEmptyList() {
        Garden garden = Garden.builder().build();

        when(getGardenRepository.getByUserId(2)).thenReturn(List.of(garden));
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(null);

        GetGardensPortIn useCase = new GetGardensUseCase(getPlantRepository, getGardenRepository, getPlantPhotosRepository);

        List<Garden> result = useCase.getGardensByUser(2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(Collections.emptyList(), result.get(0).getPlants());
    }
}
