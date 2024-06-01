package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Garden;
import ar.germin.api.application.domain.Plant;

public interface SaveGardenRepository {
    Garden save(Garden garden);
    Boolean savePlant(Plant plant);
}
