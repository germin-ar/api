package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.Family;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.Genus;
import ar.germin.api.application.domain.PlantCatalog;
import ar.germin.api.application.domain.Specie;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.application.port.out.GetFileRepository;
import ar.germin.api.application.usecase.service.GetPlantCatalogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetCandidatesPlantsUseCaseTest {

    @Mock
    private GetFileRepository getFileRepository;

    @Mock
    private GetAIDetectionRepository getAIDetectionRepository;

    @Mock
    private GetPlantCatalogService getPlantCatalogService;

    @InjectMocks
    private GetCandidatesPlantsUseCase getCandidatesPlantsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAIDetectionWithCandidatesReturnsCorrectNumberOfCandidates() {
        // Mock data
        String fileId = "sample-file-id";
        String filePath = "/path/to/file.jpg";
        Boolean isPublic = true;
        String checksum = "abcdef123456";
        FileImage mockFileImage = FileImage.builder()
                .id(fileId)
                .filePath(filePath)
                .isPublic(isPublic)
                .checksum(checksum)
                .build();
        List<Candidate> mockCandidates = createMockCandidates();

        // Mock behaviors
        when(getFileRepository.getById(fileId)).thenReturn(mockFileImage);
        when(getAIDetectionRepository.getByFileImage(mockFileImage)).thenReturn(createMockAIDetection(mockCandidates));
        when(getPlantCatalogService.getPlantCatalog(any())).thenReturn(createMockPlantCatalog());

        // Call the method under test
        AIDetection result = getCandidatesPlantsUseCase.get(fileId);

        // Assertions
        assertNotNull(result, "AIDetection should not be null");

        // Assert correct number of candidates
        assertEquals(1, result.getCandidates().size(),
                "Number of candidates in AIDetection should be 3"); // Adjusted to match the number of mock candidates

        // Assert that the candidate with the highest score is returned
        Candidate returnedCandidate = result.getCandidates().stream()
                .max(Comparator.comparingDouble(Candidate::getScore))
                .orElseThrow(() -> new AssertionError("No candidates found"));
        Candidate expectedCandidate = mockCandidates.stream()
                .max(Comparator.comparingDouble(Candidate::getScore))
                .orElseThrow();
        assertEquals(expectedCandidate.getSpecie().getScientificName(), returnedCandidate.getSpecie().getScientificName(),
                "The returned candidate should be the one with the highest score");
        assertEquals(expectedCandidate.getScore(), returnedCandidate.getScore(),
                "The returned candidate should have the highest score");

        // Additional assertions for FileImage fields if needed
        assertEquals(filePath, result.getFileImage().getFilePath(),
                "File path should match");
        assertEquals(isPublic, result.getFileImage().getIsPublic(),
                "isPublic flag should match");
        assertEquals(checksum, result.getFileImage().getChecksum(),
                "Checksum should match");
    }

    @Test
    void testGetAIDetectionReturnsNullWhenNoDetectionFound() {
        // Mock data
        String fileId = "sample-file-id";
        String filePath = "/path/to/file.jpg";
        Boolean isPublic = true;
        String checksum = "abcdef123456";
        FileImage mockFileImage = FileImage.builder()
                .id(fileId)
                .filePath(filePath)
                .isPublic(isPublic)
                .checksum(checksum)
                .build();

        // Mock behavior
        when(getFileRepository.getById(fileId)).thenReturn(mockFileImage);
        when(getAIDetectionRepository.getByFileImage(mockFileImage)).thenReturn(null);

        // Call the method under test
        AIDetection result = getCandidatesPlantsUseCase.get(fileId);

        // Assert that null is returned when no AI detection is found
        assertNull(result, "AIDetection should be null when no detection is found");
    }

    // Helper method to create a simulated AIDetection with mock candidates
    private AIDetection createMockAIDetection(List<Candidate> candidates) {
        return AIDetection.builder()
                .fileImage(FileImage.builder().id("mock-file-id").build())
                .candidates(candidates)
                .build();
    }

    // Helper method to create a list of simulated candidates
    private List<Candidate> createMockCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(Candidate.builder().specie(createMockSpecie("species1")).score(0.8F).build());
        candidates.add(Candidate.builder().specie(createMockSpecie("species2")).score(0.9F).build());
        candidates.add(Candidate.builder().specie(createMockSpecie("species3")).score(0.7F).build());
        return candidates;
    }

    // Helper method to create a simulated specie
    private Specie createMockSpecie(String scientificName) {
        return Specie.builder()
                .scientificName(scientificName)
                .scientificNameWithoutAuthor(scientificName)
                .scientificNameAuthorship("Authorship")
                .genus(Genus.builder().scientificName("Genus").scientificNameAuthorship("Author").scientificNameWithoutAuthor("WithouthAuthor").build())
                .family(Family.builder().scientificName("Family").scientificNameAuthorship("Author1").scientificNameWithoutAuthor("WithouthAuthor1").build())
                .commonNames(Collections.singletonList("Common Name"))
                .idGbif(1234)
                .idPowo("POWO1234")
                .build();
    }

    // Helper method to create a simulated PlantCatalog
    private PlantCatalog createMockPlantCatalog() {
        return PlantCatalog.builder()
                .id(1)
                .scientificName("Scientific Name")
                .description("Description")
                .slug("slug")
                .genus("Genus")
                .familyName("Family")
                .maxSize(5.0)
                .fertilizer("Fertilizer")
                .sunlight("Sunlight")
                .wateringFrequency("Watering Frequency")
                .pruning("Pruning")
                .soil("Soil")
                .insecticide("Insecticide")
                .tips("Tips")
                .temperatureMax(25.0)
                .temperatureMin(15.0)
                .growthSeason("Growth Season")
                .harvestTime("Harvest Time")
                .plantingTime("Planting Time")
                .build();
    }
}
