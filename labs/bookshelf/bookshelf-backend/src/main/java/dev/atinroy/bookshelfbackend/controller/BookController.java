package dev.atinroy.bookshelfbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class BookController {
    private static final List<BookInfo> BOOKS = List.of(
        new BookInfo(
            1L,
            "Effective Java",
            "Joshua Bloch",
            4.9,
            "A comprehensive guide to best practices in Java programming."
        ),
        new BookInfo(
            2L,
            "Clean Code",
            "Robert C. Martin",
            4.7,
            "A handbook of agile software craftsmanship."
        ),
        new BookInfo(
            3L,
            "Design Patterns",
            "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides",
            4.8,
            "Elements of Reusable Object-Oriented Software."
        )
    );

    @GetMapping("/api/books")
    public List<BookInfo> getBooks(
        @RequestParam(required = false) String author,
        @RequestParam(required = false) Double minRating
    ) {
        return BOOKS.stream()
            .filter(book -> author == null || matchesAuthor(author, book))
            .filter(book -> minRating == null || book.rating() >= minRating)
            .toList();
    }

    @GetMapping("/api/books/{id}")
    public BookInfo getBook(@PathVariable Long id) {
        return BOOKS.stream()
            .filter(book -> book.id().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Book not found with ID: " + id
            ));
    }

    private boolean matchesAuthor(String authorQuery, BookInfo book) {
        return book.author().toLowerCase().contains(authorQuery.toLowerCase());
    }

    private record BookInfo(
        Long id,
        String title,
        String author,
        double rating,
        String description
    ) {}
}
