package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Garden;

public interface SaveGardenRepository {
    Boolean save(Integer userId, String name);
}
