package ar.germin.api.application.port.out;

import lombok.Builder;
import lombok.Value;


public interface SavePlantPhotoRepository {
    Integer save(Params params);

    @Value
    @Builder
    class Params {
        Integer idPlant;
        String url;
    }
}
