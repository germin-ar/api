package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.DiseaseCandidate;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.port.in.SaveCandidatesDiseasePlantsPortIn;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.application.port.out.GetFileRepository;
import ar.germin.api.application.port.out.GetHealthSuggestionsRepository;
import ar.germin.api.application.port.out.SaveCandidateDiseasePlantsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetDiseasePlantSuggestionsUseCase implements SaveCandidatesDiseasePlantsPortIn {
    private final GetFileRepository getFileRepository;
    private final GetAIDetectionRepository getAIDetectionRepository;
    private final SaveCandidateDiseasePlantsRepository saveCandidateDiseasePlantsRepository;



    @Autowired
    public GetDiseasePlantSuggestionsUseCase (GetFileRepository getFileRepository,
                                                @Qualifier("rest") GetAIDetectionRepository getAIDetectionRepository,
                                                SaveCandidateDiseasePlantsRepository saveCandidateDiseasePlantsRepository)

        {
            this.getFileRepository = getFileRepository;
            this.getAIDetectionRepository = getAIDetectionRepository;
            this.saveCandidateDiseasePlantsRepository= saveCandidateDiseasePlantsRepository;

                 }

    @Override
    public Integer save(DiseaseCandidate diseaseCandidate) {
        return this.saveCandidateDiseasePlantsRepository.save(diseaseCandidate);
    }
}
