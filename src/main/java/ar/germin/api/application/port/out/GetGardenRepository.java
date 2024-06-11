package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Garden;

import java.util.List;

public interface GetGardenRepository {
    Garden getById(Integer id);

    List<Garden> getByUserId(Integer userId);

    //List<Garden> getAllGardensByUserId(Integer userId);
}
