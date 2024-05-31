package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.Garden;

public interface GetPlantsGardenPortIn {
    Garden get(String id);
}
