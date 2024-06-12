package ar.germin.api.application.port.in;

import lombok.Builder;
import lombok.Value;

public interface DeletePlantPortIn {
    void delete(Params params);

    @Value
    @Builder
    class Params {
        Integer id;
        Integer idUser;
    }
}
