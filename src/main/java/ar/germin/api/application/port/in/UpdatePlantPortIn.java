package ar.germin.api.application.port.in;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;


public interface UpdatePlantPortIn {
    Integer update(Params params);

    @Value
    @Builder
    class Params {
        Integer id;
        Integer idUser;
        String alias;
        Double height;
        //LocalDateTime plantingDate;
        String notes;
        Integer idGarden;
        Boolean isFavorite;
    }
}
