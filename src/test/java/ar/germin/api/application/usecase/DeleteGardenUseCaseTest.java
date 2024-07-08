package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.exceptions.ErrorGardenSaveException;
import ar.germin.api.application.port.in.DeleteGardenPortIn;
import ar.germin.api.application.port.in.DeleteGardenPortIn.Params;
import ar.germin.api.application.port.out.DeleteGardenRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteGardenUseCaseTest {

    @Test
    public void testDeleteGardenWithoutPlants() {
        // Arrange
        DeleteGardenRepository deleteGardenRepository = mock(DeleteGardenRepository.class);
        GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
        DeleteGardenUseCase deleteGardenUseCase = new DeleteGardenUseCase(deleteGardenRepository, getPlantRepository);

        DeleteGardenPortIn.Params params = DeleteGardenPortIn.Params.builder()
                .id(1)
                .idUser(1)
                .build();


        when(getPlantRepository.getByIdGardenAndIdUser(params.getId(), params.getIdUser()))
                .thenReturn(Collections.emptyList());

        // Act
        deleteGardenUseCase.delete(params);

        // Assert
        verify(deleteGardenRepository).delete(params.getId());
    }

    @Test
    public void testDeleteGardenWithPlants() {
        // Arrange
        DeleteGardenRepository deleteGardenRepository = mock(DeleteGardenRepository.class);
        GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
        DeleteGardenUseCase deleteGardenUseCase = new DeleteGardenUseCase(deleteGardenRepository, getPlantRepository);

        Params params = Params.builder()
                .id(1)
                .idUser(1)
                .build();

        Plant plant = Plant.builder()
                .id(10)
                .alias("Rose")
                .description("Red rose")
                .build();

        List<Plant> plants = Collections.singletonList(plant);
        when(getPlantRepository.getByIdGardenAndIdUser(params.getId(), params.getIdUser()))
                .thenReturn(plants);

        // Act & Assert
        assertThrows(ErrorGardenSaveException.class, () -> deleteGardenUseCase.delete(params));
        verify(deleteGardenRepository, never()).delete(params.getId());
    }

}
