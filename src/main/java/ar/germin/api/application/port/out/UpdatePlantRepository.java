package ar.germin.api.application.port.out;

import ar.germin.api.adapter.controller.models.PlantRequestModel;

public interface UpdatePlantRepository {
    Integer update(PlantRequestModel plantRequestModel);
}
