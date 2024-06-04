package ar.germin.api.application.port.in;

import ar.germin.api.adapter.controller.models.NoteResponseModel;
import ar.germin.api.application.domain.Plant;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface SavePlantPortIn {
    Plant save(Params params);

    @Value
    @Builder
    class Params {
        String alias;
        Double height;
        LocalDateTime plantingDate;
        List<NoteResponseModel> notes;
        Integer idGarden;
    }
}
