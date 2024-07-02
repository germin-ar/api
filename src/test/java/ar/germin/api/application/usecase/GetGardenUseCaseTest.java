package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.exceptions.GardenNotFoundException;
import ar.germin.api.application.port.in.GetGardenPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class GetGardenUseCaseTest {

    @Test
    void testGetGardenByIdReturnGarden() {
        // Arrange
        GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
        GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
        GetPlantPhotosRepository getPlantPhotosRepository = mock(GetPlantPhotosRepository.class);

        Garden garden = Garden.builder()
                .id(5)
                .idUser(2)
                .build();

        when(getGardenRepository.getById(5)).thenReturn(garden);
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(Collections.emptyList());

        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository, getPlantPhotosRepository);


        Garden result = useCase.get(5, 2);


        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.getId());


        verify(getGardenRepository, times(1)).getById(5);
        verify(getPlantRepository, times(1)).getByIdGardenAndIdUser(5, 2);

        verifyNoMoreInteractions(getPlantPhotosRepository);
    }

    @Test
    void testGetGardenByIdReturnGardenAndCorrectNumberOfPlants() {

        GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
        GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
        GetPlantPhotosRepository getPlantPhotosRepository = mock(GetPlantPhotosRepository.class);

        Garden garden = Garden.builder()
                .id(5)
                .idUser(2)
                .build();

        Plant plant1 = Plant.builder().id(1).photos(Collections.emptyList()).build();
        Plant plant2 = Plant.builder().id(2).photos(Collections.emptyList()).build();

        when(getGardenRepository.getById(5)).thenReturn(garden);
        when(getPlantRepository.getByIdGardenAndIdUser(5, 2)).thenReturn(List.of(plant1, plant2));
        when(getPlantPhotosRepository.getByPlantId(1)).thenReturn(Collections.emptyList());
        when(getPlantPhotosRepository.getByPlantId(2)).thenReturn(Collections.emptyList());

        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository, getPlantPhotosRepository);


        Garden result = useCase.get(5, 2);


        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.getId());
        Assertions.assertNotNull(result.getPlants());
        Assertions.assertEquals(2, result.getPlants().size());
        Assertions.assertEquals(plant1, result.getPlants().get(0));
        Assertions.assertEquals(plant2, result.getPlants().get(1));


        verify(getGardenRepository, times(1)).getById(5);
        verify(getPlantRepository, times(1)).getByIdGardenAndIdUser(5, 2);
        verify(getPlantPhotosRepository, times(1)).getByPlantId(1);
        verify(getPlantPhotosRepository, times(1)).getByPlantId(2);
        verifyNoMoreInteractions(getGardenRepository, getPlantRepository, getPlantPhotosRepository);

    }
    @Test
    void testGetGardenByIdWithoutPlantsReturnGardenAndCorrectNumberOfPlants() {
        // Arrange
        GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
        GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
        GetPlantPhotosRepository getPlantPhotosRepository = mock(GetPlantPhotosRepository.class);
        GetGardenPortIn getGardenUseCase = new GetGardenUseCase(getGardenRepository, getPlantRepository, getPlantPhotosRepository);

        Garden garden = Garden.builder()
                .id(1)
                .name("My Garden")
                .idUser(2)
                .build();

        Plant plant1 = Plant.builder()
                .id(1)
                .alias("Rose")
                .description("Red rose")
                .build();

        Plant plant2 = Plant.builder()
                .id(2)
                .alias("Tulip")
                .description("Yellow tulip")
                .build();

        List<Plant> plants = List.of(plant1, plant2);

        when(getGardenRepository.getById(1)).thenReturn(garden);
        when(getPlantRepository.getByIdGardenAndIdUser(1, 2)).thenReturn(plants);


        Garden retrievedGarden = getGardenUseCase.get(1, 2);


        Assertions.assertNotNull(retrievedGarden);
        Assertions.assertEquals(2, retrievedGarden.getPlants().size());
    }
        @Test
    void testGetGardenByIdThrowsExceptionWhenGardenNotFoundForUser() {
        // Arrange
        GetGardenRepository getGardenRepository = mock(GetGardenRepository.class);
        GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
        GetPlantPhotosRepository getPlantPhotosRepository = mock(GetPlantPhotosRepository.class);

        int gardenId = 5;
        Garden garden = Garden.builder()
                .id(gardenId)
                .idUser(999) // Id de usuario diferente al esperado
                .build();

        when(getGardenRepository.getById(gardenId)).thenReturn(garden);

        GetGardenPortIn useCase = new GetGardenUseCase(getGardenRepository, getPlantRepository, getPlantPhotosRepository);

        // Act & Assert
        Assertions.assertThrows(GardenNotFoundException.class, () -> {
            useCase.get(gardenId, 2);
        });

        // Verify
        verify(getGardenRepository, times(1)).getById(gardenId);
        verifyNoMoreInteractions(getPlantRepository, getPlantPhotosRepository);
    }
}
