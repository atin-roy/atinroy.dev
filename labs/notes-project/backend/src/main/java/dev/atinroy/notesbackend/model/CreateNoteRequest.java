package dev.atinroy.notesbackend.model;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for creating a Note. Spring Validation (JSR 380) enforces
 * that the incoming {@code text} field is not blank before the controller even runs.
 */
public record CreateNoteRequest(@NotBlank(message = "Text must not be blank") String text) {
}
