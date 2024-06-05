package ar.germin.api.application.exceptions;

public class NoteNotFoundException extends ApplicationException {

    public NoteNotFoundException() {
        super("Note not found");
    }
}
