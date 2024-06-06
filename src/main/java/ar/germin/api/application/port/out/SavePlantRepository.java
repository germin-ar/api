package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Plant;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

public interface SavePlantRepository {
    Plant save(Params params);

    @Value
    @Builder
    class Params {
        String alias;
        Double height;
        LocalDateTime plantingDate;
        String notes;
        Integer idGarden;
        Boolean isActive;
    }
}
