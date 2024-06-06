package ar.germin.api.application.port.in;

import ar.germin.api.adapter.controller.models.GardenResponseModel;
import ar.germin.api.application.domain.Garden;

import java.util.List;

public interface GetPlantsGardenPortIn {
    Garden get(Integer id);
    List<Garden> getGardensByUser(Integer id);
}
