package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.AIDetection;
import ar.germin.api.application.domain.FileImage;

public interface GetAIDetectionRepository {

    AIDetection getByFileImage(FileImage fileImage);
}
