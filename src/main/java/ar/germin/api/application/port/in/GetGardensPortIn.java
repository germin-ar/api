package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.Garden;

import java.util.List;

public interface GetGardensPortIn {
    List<Garden> getGardensByUser(Integer id);
}
