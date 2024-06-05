package ar.germin.api.application.port.out;

import ar.germin.api.application.domain.Note;

public interface GetNoteRepository {
    Note getById(Integer id);
}
