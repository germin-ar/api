package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Note;

public interface SaveNoteRepository {
    Note save(Integer userId, Integer plantId, String observations);
}
