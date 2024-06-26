package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.port.in.GetCandidatesPlantsPortIn;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.application.port.out.GetFileRepository;
import ar.germin.api.application.usecase.service.GetPlantCatalogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetCandidatesPlantsUseCaseTest {

    private final GetFileRepository getFileRepository = mock(GetFileRepository.class);
    private final GetAIDetectionRepository getAIDetectionRepository = mock(GetAIDetectionRepository.class);
    private final GetPlantCatalogService getPlantCatalogService = mock(GetPlantCatalogService.class);

    @Test
    void testGetAIDetectionWithCandidatesReturnsCorrectNumberOfCandidates() {
        FileImage fileImage = FileImage.builder()
                .id("1a")
                .build();

        Candidate candidate1 = Candidate.builder()
                .id("9z")
                .build();
        Candidate candidate2 = Candidate.builder().build();

        AIDetection aiDetection = AIDetection.builder()
                .candidates(List.of(candidate1, candidate2))
                .build();

        when(getFileRepository.getById("1a")).thenReturn(fileImage);
        when(getAIDetectionRepository.getByFileImage(fileImage)).thenReturn(aiDetection);

        GetCandidatesPlantsPortIn useCase = new GetCandidatesPlantsUseCase(
                getFileRepository,
                getAIDetectionRepository,
                getPlantCatalogService);

        AIDetection result = useCase.get("1a");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getCandidates().size());
        Assertions.assertEquals("9z", result.getCandidates().get(0).getId());
    }

    @Test
    void testGetAIDetectionReturnsNullWhenNoDetectionFound() {
        FileImage fileImage = FileImage.builder()
                .id("1a")
                .build();

        when(getFileRepository.getById("1a")).thenReturn(fileImage);
        when(getAIDetectionRepository.getByFileImage(fileImage)).thenReturn(null);

        GetCandidatesPlantsPortIn useCase = new GetCandidatesPlantsUseCase(
                getFileRepository,
                getAIDetectionRepository,
                getPlantCatalogService);

        AIDetection candidateResults = useCase.get("1a");

        Assertions.assertNull(candidateResults);
    }
}
