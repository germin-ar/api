package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.in.DeletePlantPortIn;
import ar.germin.api.application.port.out.DeletePlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class DeletePlantUseCaseTest {

    private final DeletePlantRepository deletePlantRepository = mock(DeletePlantRepository.class);

    @Test
    void testDeletePlantDeactivatesPlant() {
        Plant plant = Plant.builder()
                .id(6)
                .isActive(true)
                .build();

        doNothing().when(deletePlantRepository).delete(6);

        DeletePlantPortIn useCase = new DeletePlantUseCase(deletePlantRepository);

        useCase.delete(6);

        verify(deletePlantRepository).delete(6);
        Assertions.assertFalse(plant.getIsActive());
    }

}
