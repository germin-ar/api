package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Plant;

import java.util.List;

public interface GetPlantRepository {
    Plant getByIdUserAndIdPlant(Integer idUser, Integer idPlant);

    List<Plant> getByIdGardenAndIdUser(Integer idGarden, Integer idUser);

    List<Plant> getByIdUser(Integer idUser);
}
