package ar.germin.api.application.port.in;

import ar.germin.api.adapter.controller.models.PlantRequestModel;
import ar.germin.api.application.port.out.UpdatePlantRepository;


public interface UpdatePlantPortIn {
    Integer update(UpdatePlantRepository.Params params);
}
