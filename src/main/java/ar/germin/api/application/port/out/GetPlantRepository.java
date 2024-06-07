package ar.germin.api.application.port.out;


import ar.germin.api.adapter.controller.models.PlantResponseModel;
import ar.germin.api.application.domain.Plant;


public interface GetPlantRepository {
    Plant get(Integer idUser, Integer idPlant);
}
