package ar.germin.api.application.port.out;

import lombok.Builder;
import lombok.Value;

public interface SavePlantHistoryRepository {
    Integer save(Params params);

    @Value
    @Builder
    class Params {
        Integer idPlant;
        String notes;
        Double height;
        String alias;
        String urlImage;
        Integer idDiseases;
    }

}
