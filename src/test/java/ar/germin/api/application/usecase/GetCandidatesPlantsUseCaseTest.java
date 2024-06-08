package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.port.in.GetCandidatesPlantsPortIn;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.application.port.out.GetCandidateRepository;
import ar.germin.api.application.port.out.GetFileRepository;
import ar.germin.api.application.port.out.SaveCandidateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetCandidatesPlantsUseCaseTest {

    private final GetFileRepository getFileRepository = mock(GetFileRepository.class);
    private final GetAIDetectionRepository getAIDetectionRepository = mock(GetAIDetectionRepository.class);
    private final GetCandidateRepository getCandidateRepository = mock(GetCandidateRepository.class);
    private final SaveCandidateRepository saveCandidateRepository = mock(SaveCandidateRepository.class);

    @Test
    void testGetAIDetectionWithCandidatesReturnsCorrectNumberOfCandidates() {
        FileImage fileImage = mock(FileImage.class);

        AIDetection aiDetection = mock(AIDetection.class);

        Candidate candidate1 = mock(Candidate.class);
        Candidate candidate2 = mock(Candidate.class);
        List<Candidate> mockAICandidates = new ArrayList<>();
        mockAICandidates.add(candidate1);
        mockAICandidates.add(candidate2);

        when(aiDetection.getId()).thenReturn("1a");
        when(aiDetection.getCandidates()).thenReturn(mockAICandidates);
        when(getFileRepository.getById("1a")).thenReturn(fileImage);
        when(getAIDetectionRepository.getByFileImage(fileImage)).thenReturn(aiDetection);
        when(saveCandidateRepository.save(aiDetection)).thenReturn(mockAICandidates);

        GetCandidatesPlantsPortIn useCase = new GetCandidatesPlantsUseCase(getFileRepository, getAIDetectionRepository, getCandidateRepository, saveCandidateRepository);

        Integer candidateResults = useCase.get("1a").getCandidates().size();

        Assertions.assertEquals(2, candidateResults);
    }

    @Test
    void testGetAIDetectionReturnsNullWhenNoDetectionFound() {
        FileImage fileImage = mock(FileImage.class);
        
        when(getFileRepository.getById("1a")).thenReturn(fileImage);
        when(getAIDetectionRepository.getByFileImage(fileImage)).thenReturn(null);

        GetCandidatesPlantsPortIn useCase = new GetCandidatesPlantsUseCase(getFileRepository, getAIDetectionRepository, getCandidateRepository, saveCandidateRepository);

        AIDetection candidateResults = useCase.get("1a");

        Assertions.assertNull(candidateResults);
    }
}
