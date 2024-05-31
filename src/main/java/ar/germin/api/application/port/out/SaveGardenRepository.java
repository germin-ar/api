package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Garden;

public interface SaveGardenRepository {
    Garden save(Garden garden);
}
