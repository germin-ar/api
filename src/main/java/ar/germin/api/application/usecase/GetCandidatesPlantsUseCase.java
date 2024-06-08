package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.port.in.GetCandidatesPlantsPortIn;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.application.port.out.GetCandidateRepository;
import ar.germin.api.application.port.out.GetFileRepository;
import ar.germin.api.application.port.out.GetPlantDataRepository;
import ar.germin.api.application.port.out.SaveCandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class GetCandidatesPlantsUseCase implements GetCandidatesPlantsPortIn {
    private final GetFileRepository getFileRepository;
    private final GetAIDetectionRepository getAIDetectionRepository;
    private final GetCandidateRepository getCandidateRepository;
    private final SaveCandidateRepository saveCandidateRepository;
    private final GetPlantDataRepository getPlantDataRepository;

    @Autowired
    public GetCandidatesPlantsUseCase(GetFileRepository getFileRepository,
                                      GetAIDetectionRepository getAIDetectionRepository,
                                      GetCandidateRepository getCandidateRepository,
                                      SaveCandidateRepository saveCandidateRepository,
                                      GetPlantDataRepository getPlantDataRepository) {
        this.getFileRepository = getFileRepository;
        this.getAIDetectionRepository = getAIDetectionRepository;
        this.getCandidateRepository = getCandidateRepository;
        this.saveCandidateRepository = saveCandidateRepository;
        this.getPlantDataRepository = getPlantDataRepository;
    }

    @Override
    public AIDetection get(String id) {
        FileImage fileImage = this.getFileRepository.getById(id);

        AIDetection aiDetection = this.getAIDetectionRepository.getByFileImage(fileImage);

        aiDetection
                .getCandidates()
                .stream()
                .max(Comparator.comparingDouble(Candidate::getScore))
                .ifPresent(c -> this.getPlantDataRepository.search(c.getSpecie().getScientificNameWithoutAuthor()));

        return aiDetection;
    }
}
