package ar.germin.api.application.port.out;


import ar.germin.api.adapter.controller.models.PlantResponseModel;



public interface GetPlantRepository {
    PlantResponseModel get(Integer idUser, Integer idPlant);
}
