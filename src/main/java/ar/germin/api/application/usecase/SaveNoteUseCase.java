package ar.germin.api.application.usecase;

import ar.germin.api.application.domain.Note;
import ar.germin.api.application.port.in.SaveNotePortIn;
import ar.germin.api.application.port.out.SaveNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveNoteUseCase implements SaveNotePortIn {
    private final SaveNoteRepository saveNoteRepository;

    @Autowired
    public SaveNoteUseCase(SaveNoteRepository saveNoteRepository) {
        this.saveNoteRepository = saveNoteRepository;
    }

    @Override
    public Note save(Params params) {
        return null;
    }
}
