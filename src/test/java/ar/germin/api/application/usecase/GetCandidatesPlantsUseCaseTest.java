package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.*;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.application.port.out.GetFileRepository;
import ar.germin.api.application.usecase.service.GetPlantCatalogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GetCandidatesPlantsUseCaseTest {

    private final GetFileRepository getFileRepository = mock(GetFileRepository.class);
    private final GetAIDetectionRepository getAIDetectionRepository = mock(GetAIDetectionRepository.class);
    private final GetPlantCatalogService getPlantCatalogService = mock(GetPlantCatalogService.class);

    @Test
    public void testGet() {

        String fileId = "1a";
        FileImage fileImage = FileImage.builder().id(fileId).build();

        Genus genus = Genus.builder()
                .scientificName("Genus Name")
                .scientificNameWithoutAuthor("Genus Name Without Author")
                .scientificNameAuthorship("Genus Authorship")
                .build();

        Family family = Family.builder()
                .scientificName("Family Name")
                .scientificNameWithoutAuthor("Family Name Without Author")
                .scientificNameAuthorship("Family Authorship")
                .build();

        Specie specie = Specie.builder()
                .scientificName("Scientific Name")
                .scientificNameWithoutAuthor("Scientific Name Without Author")
                .scientificNameAuthorship("Scientific Name Authorship")
                .genus(genus)
                .family(family)
                .commonNames(List.of("Common Name"))
                .idGbif(1)
                .idPowo("ID POWO")
                .build();

        Candidate candidate = Candidate.builder()
                .id("1")
                .score(0.9f)
                .specie(specie)
                .selectedByUser(false)
                .plantCatalog(null)
                .build();

        AIDetection aiDetection = AIDetection.builder()
                .id("1")
                .language("en")
                .candidates(List.of(candidate))
                .fileImage(fileImage)
                .healthAIDetection(null)
                .build();

        PlantCatalog plantCatalog = PlantCatalog.builder()
                .id(1)
                .scientificName("Scientific Name")
                .description("Description")
                .slug("Slug")
                .genus("Genus")
                .familyName("Family")
                .maxSize(2.5)
                .fertilizer("Fertilizer")
                .sunlight("Sunlight")
                .wateringFrequency("Watering Frequency")
                .pruning("Pruning")
                .soil("Soil")
                .insecticide("Insecticide")
                .tips("Tips")
                .temperatureMax(35.0)
                .temperatureMin(15.0)
                .growthSeason("Growth Season")
                .harvestTime("Harvest Time")
                .plantingTime("Planting Time")
                .slugScientificName("Slug Scientific Name")
                .wateringCare("Watering Care")
                .commonName("Common Name")
                .lifespan("Lifespan")
                .propagation("Propagation")
                .fruit("Fruit")
                .edible("Edible")
                .growthRate("Growth Rate")
                .maintenance("Maintenance")
                .specie("Specie")
                .toxic("Toxic")
                .repotting("Repotting")
                .dormancy("Dormancy")
                .atmosphericHumidity(70)
                .plantType("Plant Type")
                .width(1.5)
                .urlImage("URL Image")
                .build();

        HealthAIDetection healthAIDetection = HealthAIDetection.builder()
                .isHealthy(true)
                .candidates(Collections.emptyList())
                .build();

        when(getFileRepository.getById(fileId)).thenReturn(fileImage);
        when(getAIDetectionRepository.getByFileImage(fileImage)).thenReturn(aiDetection);
        when(getPlantCatalogService.getPlantCatalog(specie.toSlugFormat())).thenReturn(plantCatalog);

        GetCandidatesPlantsUseCase getCandidatesPlantsUseCase = new GetCandidatesPlantsUseCase(getFileRepository, getAIDetectionRepository, getPlantCatalogService);


        AIDetection result = getCandidatesPlantsUseCase.get(fileId);


        assertNotNull(result);
        assertEquals(fileImage, result.getFileImage());
        assertEquals(1, result.getCandidates().size());
        Candidate expectedCandidate = Candidate.builder()
                .id("1")
                .score(0.9f)
                .specie(specie)
                .selectedByUser(false)
                .plantCatalog(plantCatalog)
                .build();
        assertEquals(expectedCandidate, result.getCandidates().get(0));
        assertEquals(healthAIDetection, result.getHealthAIDetection());

        verify(getFileRepository, times(1)).getById(fileId);
        verify(getAIDetectionRepository, times(1)).getByFileImage(fileImage);
        verify(getPlantCatalogService, times(1)).getPlantCatalog(specie.toSlugFormat());
    }
}
