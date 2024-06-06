package ar.germin.api.application.port.out;

import ar.germin.api.adapter.controller.models.PlantRequestModel;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

public interface UpdatePlantRepository {
    Integer update(Params params);

    @Value
    @Builder
    class Params {
        Integer id;
        String alias;
        Double height;
        LocalDateTime plantingDate;
        String notes;
        Integer idGarden;
        Boolean isActive;
        Boolean isFavorite;
    }
}
