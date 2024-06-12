package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.Plant;

public interface GetPlantPortIn {
    Plant get(Integer idUser, Integer idPlant);
}
