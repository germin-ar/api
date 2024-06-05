package ar.germin.api.application.port.in;

import ar.germin.api.application.domain.Note;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

public interface SaveNotePortIn {
    Note save(Params params);

    @Value
    @Builder
    class Params {
        Integer id;
        Integer idPlant;
        Integer idUser;
        String observations;
        LocalDateTime creationDate;
        LocalDateTime modificationDate;
    }
}
