package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.port.out.GetGardenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class GetGardenUseCaseTest {

    @Mock
    private GetGardenRepository getGardenRepository;

    @InjectMocks
    private GetGardenUseCase getGardenUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGardenByIdReturnGarden() {
        // Given
        Garden garden = Garden.builder()
                .id(1)
                .name("Test Garden")
                .idUser(1)
                .build();

        when(getGardenRepository.getById(anyInt())).thenReturn(garden);

        // When
        Garden result = getGardenUseCase.getGardenById(1);

        // Then
        assertNotNull(result);
        assertEquals(garden.getId(), result.getId());
        assertEquals(garden.getName(), result.getName());
        assertEquals(garden.getIdUser(), result.getIdUser());
    }

    @Test
    public void testGetGardenByIdReturnGardenAndCorrectNumberOfPlants() {
        // Given
        List<Plant> plants = new ArrayList<>();
        plants.add(new Plant(1, "Alias", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                true, 1.5, "Notes", 1, "Description", 1, "Family", "Genus",
                "Daily", "Description", "Common Name", "Scientific Name",
                new ArrayList<>(), "Full Sun", "Garden Name", new ArrayList<>()));
        plants.add(new Plant(2, "Plant 2", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                true, 1.2, "Notes for Plant 2", 1, "Description", 1, "Family", "Genus",
                "Weekly", "Description", "Common Name", "Scientific Name",
                new ArrayList<>(), "Partial Shade", "Garden Name", new ArrayList<>()));

        Garden garden = Garden.builder()
                .id(1)
                .name("Test Garden")
                .idUser(1)
                .plants(plants)
                .build();

        when(getGardenRepository.getById(anyInt())).thenReturn(garden);

        // When
        Garden result = getGardenUseCase.getGardenById(1);

        // Then
        assertNotNull(result);
        assertEquals(garden.getId(), result.getId());
        assertEquals(garden.getName(), result.getName());
        assertEquals(garden.getIdUser(), result.getIdUser());
        assertEquals(plants.size(), result.getPlants().size());
    }

    @Test
    public void testGetGardenByIdWithoutPlantsReturnGardenAndCorrectNumberOfPlants() {
        // Given
        Garden garden = Garden.builder()
                .id(1)
                .name("Test Garden")
                .idUser(1)
                .plants(Collections.emptyList())  // Establecer la lista de plantas como vacía
                .build();

        when(getGardenRepository.getById(anyInt())).thenReturn(garden);

        // When
        Garden result = getGardenUseCase.getGardenById(1);

        // Then
        assertNotNull(result);
        assertEquals(garden.getId(), result.getId());
        assertEquals(garden.getName(), result.getName());
        assertEquals(garden.getIdUser(), result.getIdUser());
        assertEquals(0, result.getPlants().size());
    }

    @Test
    public void testGetGardenByIdGardenNotFound() {
        // Given
        when(getGardenRepository.getById(anyInt())).thenReturn(null);

        // When
        Garden result = getGardenUseCase.getGardenById(1);

        // Then
        assertNull(result);
    }
}
