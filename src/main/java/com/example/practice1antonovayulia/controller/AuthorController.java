package com.example.practice1antonovayulia.controller;

import com.example.practice1antonovayulia.model.Book;
import com.example.practice1antonovayulia.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.practice1antonovayulia.model.Author;
import com.example.practice1antonovayulia.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorRepository repo;


    public AuthorController(AuthorRepository repo) {
        this.repo = repo;
    }

    // Створити нового автора
    @PostMapping
    public ResponseEntity<Author> add(@RequestBody Author author) {
        Author savedAuthor = repo.save(author);
        return ResponseEntity.ok(savedAuthor);
    }

    // Отримати всіх авторів
    @GetMapping
    public ResponseEntity<List<Author>> all() {
        List<Author> authors = repo.findAll();
        return ResponseEntity.ok(authors);
    }

    // Отримати автора за ID
    @GetMapping("/{id}")
    public ResponseEntity<Author> get(@PathVariable Long id) {
        Optional<Author> author = repo.findById(id);
        return author.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Оновити автора за ID
    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author updatedAuthor) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedAuthor.setId(id);
        Author savedAuthor = repo.save(updatedAuthor);
        return ResponseEntity.ok(savedAuthor);
    }

    // Видалити автора за ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/book")
    public ResponseEntity<List<Book>> getAuthorBooks(@PathVariable Long id, @Autowired BookRepository bookRepo) {
        List<Book> books = bookRepo.findByAuthorId(id);
        return books.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(books);
    }


}
