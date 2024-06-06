package ar.germin.api.application.port.in;

import ar.germin.api.adapter.controller.models.PlantRequestModel;


public interface UpdatePlantPortIn {
    Integer update(PlantRequestModel plantRequestModel);
}
