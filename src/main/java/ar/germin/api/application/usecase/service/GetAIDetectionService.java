package ar.germin.api.application.usecase.service;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.Candidate;
import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.exceptions.AIDetectionNotFoundException;
import ar.germin.api.application.port.out.GetAIDetectionRepository;
import ar.germin.api.application.port.out.GetCandidateRepository;
import ar.germin.api.application.port.out.SaveAIDetectionRepository;
import ar.germin.api.application.port.out.SaveCandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetAIDetectionService {
    private final GetAIDetectionRepository getAIJdbcDetectionRepository;
    private final GetAIDetectionRepository getAIRestDetectionRepository;
    private final GetCandidateRepository getCandidateRepository;
    private final SaveAIDetectionRepository saveAIDetectionRepository;
    private final SaveCandidateRepository saveCandidateRepository;


    @Autowired
    public GetAIDetectionService(@Qualifier("jdbc") GetAIDetectionRepository getAIJdbcDetectionRepository,
                                 @Qualifier("rest") GetAIDetectionRepository getAIRestDetectionRepository,
                                 GetCandidateRepository getCandidateRepository,
                                 SaveAIDetectionRepository saveAIDetectionRepository,
                                 SaveCandidateRepository saveCandidateRepository) {
        this.getAIJdbcDetectionRepository = getAIJdbcDetectionRepository;
        this.getAIRestDetectionRepository = getAIRestDetectionRepository;
        this.getCandidateRepository = getCandidateRepository;
        this.saveAIDetectionRepository = saveAIDetectionRepository;
        this.saveCandidateRepository = saveCandidateRepository;
    }

    @Transactional
    public AIDetection getByFileImage(FileImage fileImage) {
        AIDetection aiDetection = this.getFromDatabaseOrRest(fileImage);


        return aiDetection;
    }

    public AIDetection getFromDatabaseOrRest(FileImage fileImage) {
        try {
            AIDetection aiDetection = this.getAIJdbcDetectionRepository.getByFileImage(fileImage);
            List<Candidate> candidates = this.getCandidateRepository.getByFileImage(fileImage);
            return aiDetection.withCandidates(candidates);
        } catch (AIDetectionNotFoundException ex) {
            AIDetection aiDetection = this.getAIRestDetectionRepository.getByFileImage(fileImage);

            this.saveAIDetectionRepository.save(aiDetection);

            aiDetection.getCandidates().parallelStream().forEach(this.saveCandidateRepository::save);

            return aiDetection;
        }
    }
}
