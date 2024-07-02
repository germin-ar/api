package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.exceptions.PlantNotFoundException;
import ar.germin.api.application.port.in.UpdatePlantPortIn;
import ar.germin.api.application.port.out.GetPlantRepository;
import ar.germin.api.application.port.out.UpdatePlantRepository;
import ar.germin.api.application.usecase.UpdatePlantUseCase;
import ar.germin.api.application.usecase.service.UpdatePlantService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdatePlantUseCaseTest {

    @Test
    void testUpdatePlant_Success() {

        GetPlantRepository mockGetPlantRepository = mock(GetPlantRepository.class);
        UpdatePlantRepository mockUpdatePlantRepository = mock(UpdatePlantRepository.class);
        UpdatePlantService mockUpdatePlantService = mock(UpdatePlantService.class);

        UpdatePlantUseCase updatePlantUseCase = new UpdatePlantUseCase(
                mockGetPlantRepository, mockUpdatePlantRepository, mockUpdatePlantService);

        UpdatePlantPortIn.Params params = UpdatePlantPortIn.Params.builder()
                .idUser(1)
                .id(1)
                .alias("Updated Plant Name")
                .build();

        Plant plant = Plant.builder()
                .id(1)
                .idUser(1)
                .alias("Original Plant Name")
                .description("Original description of the plant.")
                .build();

        when(mockGetPlantRepository.getByIdUserAndIdPlant(params.getIdUser(), params.getId())).thenReturn(plant);
        when(mockUpdatePlantService.updatePlant(params)).thenReturn(1);


        Integer result = updatePlantUseCase.update(params);


        assertEquals(1, result);
        verify(mockGetPlantRepository, times(1)).getByIdUserAndIdPlant(params.getIdUser(), params.getId());
        verify(mockUpdatePlantService, times(1)).updatePlant(params);
    }

    @Test
    void testUpdatePlant_PlantDoesNotBelongToUser() {

        GetPlantRepository mockGetPlantRepository = mock(GetPlantRepository.class);
        UpdatePlantRepository mockUpdatePlantRepository = mock(UpdatePlantRepository.class);
        UpdatePlantService mockUpdatePlantService = mock(UpdatePlantService.class);

        UpdatePlantUseCase updatePlantUseCase = new UpdatePlantUseCase(
                mockGetPlantRepository, mockUpdatePlantRepository, mockUpdatePlantService);

        UpdatePlantPortIn.Params params = UpdatePlantPortIn.Params.builder()
                .idUser(1)
                .id(1)
                .alias("Updated Plant Name")
                .build();

        // Simular que la planta no se encuentra o no pertenece al usuario (devolución de null)
        when(mockGetPlantRepository.getByIdUserAndIdPlant(params.getIdUser(), params.getId())).thenReturn(null);


        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            updatePlantUseCase.update(params);
        });

        // Verificar que la NullPointerException sea lanzada
        assertNotNull(exception);

        // También verificar que no se haya llamado a updatePlantService
        verify(mockGetPlantRepository, times(1)).getByIdUserAndIdPlant(params.getIdUser(), params.getId());
        verify(mockUpdatePlantService, never()).updatePlant(params);
    }

    @Test
    void testUpdatePlant_NoUserIdSpecified() {

        GetPlantRepository mockGetPlantRepository = mock(GetPlantRepository.class);
        UpdatePlantRepository mockUpdatePlantRepository = mock(UpdatePlantRepository.class);
        UpdatePlantService mockUpdatePlantService = mock(UpdatePlantService.class);

        UpdatePlantUseCase updatePlantUseCase = new UpdatePlantUseCase(
                mockGetPlantRepository, mockUpdatePlantRepository, mockUpdatePlantService);

        UpdatePlantPortIn.Params params = UpdatePlantPortIn.Params.builder()
                .id(1)
                .alias("Updated Plant Name")
                .build();

        when(mockUpdatePlantService.updatePlant(params)).thenReturn(1);


        Integer result = updatePlantUseCase.update(params);


        assertEquals(1, result);
        verify(mockGetPlantRepository, never()).getByIdUserAndIdPlant((int) anyLong(), (int) anyLong());
        verify(mockUpdatePlantService, times(1)).updatePlant(params);
    }

    @Test
    void testUpdatePlant_PlantNotFound() {

        GetPlantRepository mockGetPlantRepository = mock(GetPlantRepository.class);
        UpdatePlantRepository mockUpdatePlantRepository = mock(UpdatePlantRepository.class);
        UpdatePlantService mockUpdatePlantService = mock(UpdatePlantService.class);

        UpdatePlantUseCase updatePlantUseCase = new UpdatePlantUseCase(
                mockGetPlantRepository, mockUpdatePlantRepository, mockUpdatePlantService);

        UpdatePlantPortIn.Params params = UpdatePlantPortIn.Params.builder()
                .idUser(1)
                .id(1)
                .alias("Updated Plant Name")
                .build();

        when(mockGetPlantRepository.getByIdUserAndIdPlant(params.getIdUser(), params.getId()))
                .thenThrow(new PlantNotFoundException());


        assertThrows(PlantNotFoundException.class, () -> {
            updatePlantUseCase.update(params);
        });

        verify(mockGetPlantRepository, times(1)).getByIdUserAndIdPlant(params.getIdUser(), params.getId());
        verify(mockUpdatePlantService, never()).updatePlant(params);
    }


}
