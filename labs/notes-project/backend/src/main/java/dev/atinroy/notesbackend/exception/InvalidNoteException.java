package dev.atinroy.notesbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidNoteException extends RuntimeException {

    /**
     * Custom exception so we return 400 when our manual validation fails.
     */
    public InvalidNoteException(String message) {
        super(message);
    }
}
