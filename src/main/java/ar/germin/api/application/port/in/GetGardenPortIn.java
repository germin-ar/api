package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.Garden;

public interface GetGardenPortIn {
    Garden get(Integer id, Integer userId);
}
