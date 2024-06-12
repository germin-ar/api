package ar.germin.api.application.port.in;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

public interface SavePlantPortIn {
    Integer save(Params params);

    @Value
    @Builder
    class Params {
        String alias;
        Double height;
        LocalDateTime plantingDate;
        Integer idGarden;
        Boolean isActive;
        Integer idUser;
        String imageUrl;
    }
}
