package dev.atinroy.bookshelfbackend.dto;

import java.util.List;

public record BookDto(
        Long id,
        String title,
        String subtitle,
        List<String> authors,
        String description,
        String isbn,
        String publisher,
        String publishedDate,
        Integer publicationYear,
        String coverImageUrl,
        // --- UI Enrichment Fields ---
        Double averageRating,
        Integer reviewCount,
        List<String> categories // e.g., ["Fantasy", "Hardcover"]
) {
}
