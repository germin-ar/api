package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.exceptions.GardenNotFoundException;
import ar.germin.api.application.port.in.SavePlantPortIn;
import ar.germin.api.application.port.out.GetGardenRepository;
import ar.germin.api.application.usecase.service.SavePlantService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SavePlantUseCaseTest {

    @Test
    void testSavePlant_Success() {

        GetGardenRepository mockGetGardenRepository = mock(GetGardenRepository.class);
        SavePlantService mockSavePlantService = mock(SavePlantService.class);
        SavePlantUseCase savePlantUseCase = new SavePlantUseCase(mockGetGardenRepository, mockSavePlantService);

        SavePlantPortIn.Params params = SavePlantPortIn.Params.builder()
                .alias("alias")
                .height(10.00)
                .notes("Rose")
                .plantingDate(LocalDateTime.of(2007, 1, 1, 0, 0))
                .idGarden(1)
                .isActive(true)
                .idUser(1)
                .imageUrl("http://example.com/image.jpg")
                .idPlantCatalog(1)
                .build();

        Garden garden = Garden.builder()
                .id(1)
                .idUser(1)
                .name("My Garden")
                .build();

        when(mockGetGardenRepository.getById(params.getIdGarden())).thenReturn(garden);
        when(mockSavePlantService.savePlant(params)).thenReturn(1);


        Integer result = savePlantUseCase.save(params);


        assertEquals(1, result);
        verify(mockGetGardenRepository, times(1)).getById(params.getIdGarden());
        verify(mockSavePlantService, times(1)).savePlant(params);
    }

    @Test
    void testSavePlant_GardenDoesNotBelongToUser() {

        GetGardenRepository mockGetGardenRepository = mock(GetGardenRepository.class);
        SavePlantService mockSavePlantService = mock(SavePlantService.class);
        SavePlantUseCase savePlantUseCase = new SavePlantUseCase(mockGetGardenRepository, mockSavePlantService);

        SavePlantPortIn.Params params = SavePlantPortIn.Params.builder()
                .alias("alias")
                .height(10.00)
                .notes("Rose")
                .plantingDate(LocalDateTime.of(2007, 1, 1, 0, 0))
                .idGarden(1)
                .isActive(true)
                .idUser(2) // User ID different from the garden's owner
                .imageUrl("http://example.com/image.jpg")
                .idPlantCatalog(1)
                .build();

        Garden garden = Garden.builder()
                .id(1)
                .idUser(1) // Garden belongs to user with ID 1
                .name("My Garden")
                .build();

        when(mockGetGardenRepository.getById(params.getIdGarden())).thenReturn(garden);


        GardenNotFoundException exception = assertThrows(GardenNotFoundException.class, () -> {
            savePlantUseCase.save(params);
        });

        verify(mockGetGardenRepository, times(1)).getById(params.getIdGarden());
        verify(mockSavePlantService, never()).savePlant(params);
    }

    @Test
    void testSavePlant_NoGardenSpecified() {
        // Arrange
        GetGardenRepository mockGetGardenRepository = mock(GetGardenRepository.class);
        SavePlantService mockSavePlantService = mock(SavePlantService.class);
        SavePlantUseCase savePlantUseCase = new SavePlantUseCase(mockGetGardenRepository, mockSavePlantService);

        SavePlantPortIn.Params params = SavePlantPortIn.Params.builder()
                .alias("alias")
                .height(10.00)
                .notes("Rose")
                .plantingDate(LocalDateTime.of(2007, 1, 1, 0, 0))
                .idUser(1) // No garden ID specified
                .isActive(true)
                .imageUrl("http://example.com/image.jpg")
                .idPlantCatalog(1)
                .build();

        when(mockSavePlantService.savePlant(params)).thenReturn(1);


        Integer result = savePlantUseCase.save(params);


        assertEquals(1, result);
        verify(mockGetGardenRepository, never()).getById((int) anyLong());
        verify(mockSavePlantService, times(1)).savePlant(params);
    }

    @Test
    void testSavePlant_GardenNotFound() {

        GetGardenRepository mockGetGardenRepository = mock(GetGardenRepository.class);
        SavePlantService mockSavePlantService = mock(SavePlantService.class);
        SavePlantUseCase savePlantUseCase = new SavePlantUseCase(mockGetGardenRepository, mockSavePlantService);

        SavePlantPortIn.Params params = SavePlantPortIn.Params.builder()
                .alias("alias")
                .height(10.00)
                .notes("Rose")
                .plantingDate(LocalDateTime.of(2007, 1, 1, 0, 0))
                .idGarden(1)
                .idUser(1)
                .isActive(true)
                .imageUrl("http://example.com/image.jpg")
                .idPlantCatalog(1)
                .build();

        when(mockGetGardenRepository.getById(params.getIdGarden())).thenThrow(new GardenNotFoundException());


        assertThrows(GardenNotFoundException.class, () -> {
            savePlantUseCase.save(params);
        });

        verify(mockGetGardenRepository, times(1)).getById(params.getIdGarden());
        verify(mockSavePlantService, never()).savePlant(params);
    }
}
