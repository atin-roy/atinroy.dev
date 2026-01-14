package dev.atinroy.notesbackend.controller;

import dev.atinroy.notesbackend.model.CreateNoteRequest;
import dev.atinroy.notesbackend.model.Note; // Domain DTO returned to the client
import dev.atinroy.notesbackend.service.NoteService; // Service layer encapsulating note logic
import jakarta.validation.Valid; // Annotation that triggers bean validation on request bodies
import org.springframework.http.HttpStatus; // Enum constants for HTTP status codes
import org.springframework.web.bind.annotation.CrossOrigin; // Enables cross-origin requests from frontend
import org.springframework.web.bind.annotation.GetMapping; // Maps GET requests onto handler methods
import org.springframework.web.bind.annotation.PostMapping; // Maps POST requests onto handler methods
import org.springframework.web.bind.annotation.RequestBody; // Binds method parameter to HTTP request body
import org.springframework.web.bind.annotation.RequestMapping; // Shared path prefix for controller routes
import org.springframework.web.bind.annotation.ResponseStatus; // Overrides status returned from handler
import org.springframework.web.bind.annotation.RestController; // Indicates this class handles REST endpoints

import java.util.List;

/**
 * Simple REST controller demonstrating the usual Spring Boot annotations.
 * 
 * @CrossOrigin allows the Next.js app running on localhost:3000 to call these
 *              endpoints.
 */
@CrossOrigin(origins = "http://localhost:3000") // Permit requests from the Next.js dev server
@RestController // Combines @Controller and @ResponseBody, returning JSON by default
@RequestMapping("/notes") // All routes in this class are under /notes
public class NoteController {

    private final NoteService noteService; // Service dependency injected via constructor

    public NoteController(NoteService noteService) { // Constructor injection keeps the controller testable
        this.noteService = noteService; // Store the service reference for handler methods
    }

    /**
     * Handles GET /notes.
     * Spring automatically serializes the returned List<Note> to JSON.
     */
    @GetMapping // Called when GET requests arrive at /notes
    public List<Note> listNotes() {
        return noteService.findAll(); // Delegate to service to fetch stored notes
    }

    /**
     * Handles POST /notes.
     * 
     * @Valid triggers bean validation before the controller runs.
     */
    @PostMapping // Maps HTTP POST /notes to this method
    @ResponseStatus(HttpStatus.CREATED) // Tell clients a resource was created
    public Note addNote(@RequestBody @Valid CreateNoteRequest request) {
        return noteService.create(request.text()); // Create note and return its representation
    }
}
