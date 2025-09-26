package com.example.practice1antonovayulia.controller;

import com.example.practice1antonovayulia.model.Book;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.practice1antonovayulia.model.Genre;
import com.example.practice1antonovayulia.repository.GenreRepository;
import com.example.practice1antonovayulia.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genre")
public class GenreController {
    private final GenreRepository repo;
    private final BookRepository bookRepo;

    public GenreController(GenreRepository repo, BookRepository bookRepo) {
        this.repo = repo;
        this.bookRepo = bookRepo;
    }

    // Створити новий жанр
    @PostMapping
    public ResponseEntity<Genre> add(@RequestBody Genre genre) {
        Genre savedGenre = repo.save(genre);
        return ResponseEntity.ok(savedGenre);
    }

    // Отримати всі жанри
    @GetMapping
    public ResponseEntity<List<Genre>> all() {
        List<Genre> genres = repo.findAll();
        return ResponseEntity.ok(genres);
    }

    // Отримати жанр за ID
    @GetMapping("/{id}")
    public ResponseEntity<Genre> get(@PathVariable Long id) {
        Optional<Genre> genre = repo.findById(id);
        return genre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Оновити жанр за ID
    @PutMapping("/{id}")
    public ResponseEntity<Genre> update(@PathVariable Long id, @RequestBody Genre updatedGenre) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedGenre.setId(id);
        Genre savedGenre = repo.save(updatedGenre);
        return ResponseEntity.ok(savedGenre);
    }

    // Видалити жанр за ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Отримати книги за жанром
    @GetMapping("/{id}/book")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable Long id) {
        List<Book> books = bookRepo.findByGenreId(id);
        return ResponseEntity.ok(books);
    }
}
