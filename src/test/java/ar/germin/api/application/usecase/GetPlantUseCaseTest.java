package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Plant;
import ar.germin.api.application.domain.PlantHistory;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.port.in.GetPlantPortIn;
import ar.germin.api.application.port.out.GetPlantHistoryRepository;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.GetPlantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;

public class GetPlantUseCaseTest {

    @Test
    void testGetPlantByIdUserAndIdPlantReturnPlantWithPhotosAndHistory() {
        // Arrange
        GetPlantRepository getPlantRepository = Mockito.mock(GetPlantRepository.class);
        GetPlantPhotosRepository getPlantPhotosRepository = Mockito.mock(GetPlantPhotosRepository.class);
        GetPlantHistoryRepository getPlantHistoryRepository = Mockito.mock(GetPlantHistoryRepository.class);
        GetPlantPortIn getPlantUseCase = new GetPlantUseCase(getPlantRepository, getPlantPhotosRepository, getPlantHistoryRepository);

        int idUser = 1;
        int idPlant = 1;

        Plant plant = Plant.builder()
                .id(idPlant)
                .alias("Rose")
                .description("Red rose")
                .build();

        PlantPhoto photo1 = PlantPhoto.builder()
                .id(1)
                .url("https://example.com/photo1.jpg")
                .build();

        PlantPhoto photo2 = PlantPhoto.builder()
                .id(2)
                .url("https://example.com/photo2.jpg")
                .build();

        List<PlantPhoto> photos = List.of(photo1, photo2);

        PlantHistory history1 = PlantHistory.builder()
                .id(1)
                .build();

        PlantHistory history2 = PlantHistory.builder()
                .id(2)
                .build();

        List<PlantHistory> history = List.of(history1, history2);

        Mockito.when(getPlantRepository.getByIdUserAndIdPlant(idUser, idPlant)).thenReturn(plant);
        Mockito.when(getPlantPhotosRepository.getByPlantId(idPlant)).thenReturn(photos);
        Mockito.when(getPlantHistoryRepository.getByPlantId(idPlant)).thenReturn(history);

        // Act
        Plant retrievedPlant = getPlantUseCase.get(idUser, idPlant);

        // Assert
        Assertions.assertNotNull(retrievedPlant);
        Assertions.assertEquals(idPlant, retrievedPlant.getId());
        Assertions.assertEquals(2, retrievedPlant.getPhotos().size());
        Assertions.assertEquals(2, retrievedPlant.getHistory().size());
    }
}
