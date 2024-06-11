package ar.germin.api.application.port.out;

import ar.germin.api.adapter.controller.models.PlantRequestModel;
import com.fasterxml.jackson.annotation.JsonFormat;
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
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime plantingDate;
        String notes;
        Integer id_garden;
        Boolean is_active;
        Boolean is_favorite;
    }
}
