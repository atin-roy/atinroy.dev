package dev.atinroy.notesbackend.service;

import dev.atinroy.notesbackend.exception.InvalidNoteException;
import dev.atinroy.notesbackend.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Very simple service layer that keeps notes in memory.
 * CopyOnWriteArrayList is safe for concurrent reads/writes without explicit locking,
 * which is fine for learning scenarios like this.
 */
@Service
public class NoteService {

    private final AtomicLong idSequence = new AtomicLong();
    private final CopyOnWriteArrayList<Note> notes = new CopyOnWriteArrayList<>();

    /**
     * Return an immutable snapshot of everything we have so far.
     */
    public List<Note> findAll() {
        return List.copyOf(notes);
    }

    /**
     * Store the trimmed text and make sure there is some content first.
     * Manual validation exists in addition to bean validation to demonstrate
     * how you can defend against broken clients.
     */
    public Note create(String text) {
        String trimmed = (text == null) ? "" : text.trim();
        if (trimmed.isEmpty()) {
            throw new InvalidNoteException("Note text must not be blank");
        }

        Note note = new Note(idSequence.incrementAndGet(), trimmed);
        notes.add(note);
        return note;
    }
}
