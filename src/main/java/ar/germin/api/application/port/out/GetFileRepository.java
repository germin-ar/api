package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.FileImage;

public interface GetFileRepository {
    FileImage getById(String id);
}
