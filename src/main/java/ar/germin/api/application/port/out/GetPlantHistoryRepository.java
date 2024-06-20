package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.PlantHistory;

import java.util.List;

public interface GetPlantHistoryRepository {
    List<PlantHistory> getByPlantId(Integer id);
}
