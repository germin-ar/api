package ar.germin.api.application.port.out;

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
        Integer idGarden;
        Boolean isFavorite;
    }
}
