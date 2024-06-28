package ar.germin.api.application.usecase;

import ar.germin.api.application.port.in.DeletePlantPortIn.Params;
import ar.germin.api.application.port.out.DeletePlantRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import ar.germin.api.application.domain.Plant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeletePlantUseCaseTest {

    @Mock
    private GetPlantRepository getPlantRepository;

    @Mock
    private DeletePlantRepository deletePlantRepository;

    @InjectMocks
    private DeletePlantUseCase deletePlantUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeletePlant() {
        // Arrange
        Params params = Params.builder()
                .id(1)
                .idUser(1)
                .build();

        Plant plant = Plant.builder()
                .id(1)
                .alias("Rose")
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .plantingDate(LocalDateTime.now())
                .isFavorite(false)
                .height(10.0)
                .idGarden(1)
                .description("A beautiful rose")
                .idUser(1)
                .pcFamilyname("Rosaceae")
                .pcGenus("Rosa")
                .pcWateringFrecuency("Daily")
                .pcDescription("Common garden rose")
                .pcCommonName("Rose")
                .pcScientificName("Rosa rubiginosa")
                .pcSunExposure("Full Sun")
                .gardenName("My Garden")
                .build();

        when(getPlantRepository.getByIdUserAndIdPlant(params.getIdUser(), params.getId())).thenReturn(plant);

        // Act
        deletePlantUseCase.delete(params);

        // Assert
        verify(getPlantRepository, times(1)).getByIdUserAndIdPlant(params.getIdUser(), params.getId());
        verify(deletePlantRepository, times(1)).delete(params.getId());
    }
}