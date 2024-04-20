package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.FileImage;
import reactor.core.publisher.Mono;

public interface SaveImagePortIn {
    Mono<FileImage> save(byte[] image);
}
