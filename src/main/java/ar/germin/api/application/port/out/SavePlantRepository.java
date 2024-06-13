package ar.germin.api.application.port.out;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

public interface SavePlantRepository {
    Integer save(Params params);

    @Value
    @Builder
    class Params {
        String alias;
        Double height;
        LocalDateTime plantingDate;
        Integer idGarden;
        Integer idUser;
        Boolean isActive;
        String imageUuid;
        Integer idPlantCatalog;
    }
}
