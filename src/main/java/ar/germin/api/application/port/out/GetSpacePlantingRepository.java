package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.FileImage;
import ar.germin.api.application.domain.SpacePlanting;

public interface GetSpacePlantingRepository {
    SpacePlanting getSpacePlanting(FileImage fileImage);
}
