package dev.atinroy.notesbackend.model;

/**
 * Simple immutable DTO representing a persisted lab note.
 * Records automatically provide getters, equals/hashCode, and a compact constructor.
 */
public record Note(Long id, String text) {
}
