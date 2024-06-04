package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.Garden;

public interface SaveGardenPortIn {
    Garden save(Integer userId, String name);
}
