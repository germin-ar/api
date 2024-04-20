package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.FileImage;
import reactor.core.publisher.Mono;

public interface UploadFileRepository {
    Mono<FileImage> upload(byte[] file);
}
