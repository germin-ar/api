package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.PlantPhoto;

import java.util.List;

public interface GetPlantPhotosRepository {
    List<PlantPhoto> getByPlantId(Integer plantId);
}
