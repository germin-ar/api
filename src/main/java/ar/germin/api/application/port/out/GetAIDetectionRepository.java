package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.FileImage;
import reactor.core.publisher.Mono;

public interface GetAIDetectionRepository {

    Mono<AIDetection> getByFileImage(FileImage fileImage);
}
