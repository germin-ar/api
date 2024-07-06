package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.DeletePlantPortIn;
import ar.germin.api.application.port.out.DeletePlantRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;



import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class DeletePlantUseCaseTest {

    @Test
    void testDeletePlant() {
        // Arrange
        GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
        DeletePlantRepository deletePlantRepository = mock(DeletePlantRepository.class);
        DeletePlantUseCase deletePlantUseCase = new DeletePlantUseCase(getPlantRepository, deletePlantRepository);

        // Create Params using builder()
        DeletePlantUseCase.Params params = DeletePlantUseCase.Params.builder()
                .idUser(1)
                .id(10)
                .build();

        // Create Plant using builder()
        Plant plant = Plant.builder()
                .id(10)
                .alias("Rose")
                .description("Red rose")
                .build();

        // Stub the behavior of getPlantRepository
        when(getPlantRepository.getByIdUserAndIdPlant(1, 10)).thenReturn(plant);

        // Act
        deletePlantUseCase.delete(params);

        // Assert
        verify(getPlantRepository).getByIdUserAndIdPlant(1, 10);
        verify(deletePlantRepository).delete(10);
    }
    @Test
    void testDeletePlantVerifyArguments() {
        // Arrange
        GetPlantRepository getPlantRepository = mock(GetPlantRepository.class);
        DeletePlantRepository deletePlantRepository = mock(DeletePlantRepository.class);
        DeletePlantUseCase deletePlantUseCase = new DeletePlantUseCase(getPlantRepository, deletePlantRepository);

        DeletePlantUseCase.Params params = DeletePlantUseCase.Params.builder()
                .idUser(1)
                .id(10)
                .build();

        Plant plant = Plant.builder()
                .id(10)
                .alias("Rose")
                .description("Red rose")
                .build();
        when(getPlantRepository.getByIdUserAndIdPlant(1, 10)).thenReturn(plant);

        // Act
        deletePlantUseCase.delete(params);

        // Assert
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(getPlantRepository).getByIdUserAndIdPlant(Mockito.eq(1), idCaptor.capture());
        verify(deletePlantRepository).delete(idCaptor.getValue());
        assertEquals(10, idCaptor.getValue());
    }
}
