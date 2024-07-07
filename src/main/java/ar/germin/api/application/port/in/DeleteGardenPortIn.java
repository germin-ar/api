package ar.germin.api.application.port.in;

import lombok.Builder;
import lombok.Value;

public interface DeleteGardenPortIn {
    void delete(Params params);

    @Value
    @Builder
    class Params {
        Integer id;
        Integer idUser;
    }
}
