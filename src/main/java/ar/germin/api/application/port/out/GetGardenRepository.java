package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Garden;

public interface GetGardenRepository {
    Garden getById(String id);
}
