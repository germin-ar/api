package ar.germin.api.application.usecase;
import ar.germin.api.application.domain.HealthAIDetection;
import ar.germin.api.application.domain.PlantPhoto;
import ar.germin.api.application.port.out.GetHealthSuggestionsRepository;
import ar.germin.api.application.port.out.GetPlantPhotosRepository;
import ar.germin.api.application.port.out.SaveCandidateDiseasePlantsRepository;
import ar.germin.api.application.port.out.SavePlantPhotoDiseaseRelationRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetCandidatesDiseasePlantsUseCaseTest {

    @Test
    void testGetCandidatesDiseasePlants_Success() {

        GetPlantPhotosRepository getPlantPhotosRepository = mock(GetPlantPhotosRepository.class);
        GetHealthSuggestionsRepository getHealthSuggestionsRepository = mock(GetHealthSuggestionsRepository.class);
        SaveCandidateDiseasePlantsRepository saveCandidateDiseasePlantsRepository = mock(SaveCandidateDiseasePlantsRepository.class);
        SavePlantPhotoDiseaseRelationRepository savePlantPhotoDiseaseRelationRepository = mock(SavePlantPhotoDiseaseRelationRepository.class);

        GetCandidatesDiseasePlantsUseCase getCandidatesDiseasePlantsUseCase =
                new GetCandidatesDiseasePlantsUseCase(getPlantPhotosRepository, getHealthSuggestionsRepository,
                        saveCandidateDiseasePlantsRepository, savePlantPhotoDiseaseRelationRepository);

        Integer plantId = 1;


        LocalDateTime now = LocalDateTime.now();
        PlantPhoto plantPhoto1 = PlantPhoto.builder()
                .id(1)
                .idPlant(plantId)
                .uploadedAt(now.minusDays(2))
                .url("photo1.jpg")
                .build();
        PlantPhoto plantPhoto2 = PlantPhoto.builder()
                .id(2)
                .idPlant(plantId)
                .uploadedAt(now.minusDays(1))
                .url("photo2.jpg")
                .build();
        List<PlantPhoto> plantPhotoList = new ArrayList<>();
        plantPhotoList.add(plantPhoto1);
        plantPhotoList.add(plantPhoto2);
        when(getPlantPhotosRepository.getByPlantId(plantId)).thenReturn(plantPhotoList);


        HealthAIDetection expectedHealthAIDetection = HealthAIDetection.builder()
                .candidates(new ArrayList<>())
                .isHealthy(true)
                .build();
        when(getHealthSuggestionsRepository.getHealthStatus("photo2.jpg")).thenReturn(expectedHealthAIDetection);


        HealthAIDetection result = getCandidatesDiseasePlantsUseCase.get(plantId);


        assertEquals(expectedHealthAIDetection, result);
    }
}
