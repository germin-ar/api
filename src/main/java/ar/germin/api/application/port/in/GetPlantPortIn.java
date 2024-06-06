package ar.germin.api.application.port.in;

import ar.germin.api.adapter.controller.models.PlantResponseModel;


public interface GetPlantPortIn {
    PlantResponseModel get(Integer id);
}
