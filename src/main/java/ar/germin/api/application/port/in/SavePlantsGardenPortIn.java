package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;

public interface SavePlantsGardenPortIn {
    Garden save(Garden garden);
    Boolean savePlant(Plant plant);
}
