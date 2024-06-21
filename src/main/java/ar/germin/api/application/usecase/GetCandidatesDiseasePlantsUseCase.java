package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.HealthAIDetection;
import ar.germin.api.application.port.in.GetCandidatesDiseasePlantsPortIn;
import ar.germin.api.application.port.out.GetFileRepository;
import ar.germin.api.application.port.out.GetHealthSuggestionsRepository;
import ar.germin.api.application.port.out.SaveCandidateDiseasePlantsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetCandidatesDiseasePlantsUseCase implements GetCandidatesDiseasePlantsPortIn {
    private final GetFileRepository getFileRepository;
    private final GetHealthSuggestionsRepository getHealthSuggestionsRepository;
    private final SaveCandidateDiseasePlantsRepository saveCandidateDiseasePlantsRepository;

    @Autowired
    public GetCandidatesDiseasePlantsUseCase(GetFileRepository getFileRepository,
                                             @Qualifier("cropkindwise") GetHealthSuggestionsRepository getHealthSuggestionsRepository,
                                             SaveCandidateDiseasePlantsRepository saveCandidateDiseasePlantsRepository) {
        this.getFileRepository = getFileRepository;
        this.getHealthSuggestionsRepository = getHealthSuggestionsRepository;
        this.saveCandidateDiseasePlantsRepository = saveCandidateDiseasePlantsRepository;
    }

    @Override
    public HealthAIDetection get(String id) {
        FileImage fileImage = getFileRepository.getById(id);

        return this.saveCandidateDiseasePlantsRepository.save(diseaseCandidate);
    }
}
