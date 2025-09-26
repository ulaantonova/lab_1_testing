
package com.example.practice1antonovayulia.controller;

import com.example.practice1antonovayulia.model.Loan;
import com.example.practice1antonovayulia.model.Book;
import com.example.practice1antonovayulia.model.Genre;
import com.example.practice1antonovayulia.repository.BookRepository;
import com.example.practice1antonovayulia.repository.GenreRepository;
import com.example.practice1antonovayulia.repository.LoanRepository;
import jakarta.validation.Valid;
import com.example.practice1antonovayulia.controller.GenreRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookRepository repo;
    private final GenreRepository genreRepo;
    private final LoanRepository loanRepo;

    public BookController(BookRepository repo, GenreRepository genreRepo, LoanRepository loanRepo) {
        this.repo = repo;
        this.genreRepo = genreRepo;
        this.loanRepo = loanRepo;
    }

    // Створити нову книгу
    @PostMapping
    public ResponseEntity<Book> add(@RequestBody Book book) {
        Book savedBook = repo.save(book);
        return ResponseEntity.ok(savedBook);
    }

    // Отримати всі книги
    @GetMapping
    public ResponseEntity<List<Book>> all() {
        List<Book> books = repo.findAll();
        return ResponseEntity.ok(books);
    }

    // Отримати книгу за ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        Optional<Book> book = repo.findById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Оновити книгу за ID
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book updatedBook) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedBook.setId(id);
        Book savedBook = repo.save(updatedBook);
        return ResponseEntity.ok(savedBook);
    }

    // Видалити книгу за ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Отримати книги за authorId
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Book>> byAuthor(@PathVariable Long authorId) {
        List<Book> books = repo.findByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }

    // Отримати книги за genreId
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<Book>> byGenre(@PathVariable Long genreId) {
        List<Book> books = repo.findByGenreId(genreId);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}/genre")
    public ResponseEntity<Book> assignGenre(@PathVariable Long id, @RequestBody @Valid GenreRequest genreRequest) {
        Optional<Book> bookOpt = repo.findById(id);
        if (bookOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Genre> genreOpt = genreRepo.findById(genreRequest.getGenreId());
        if (genreOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Book book = bookOpt.get();
        book.setGenre(genreOpt.get());
        return ResponseEntity.ok(repo.save(book));
    }

    // Отримати історію видачі книги
    @GetMapping("/{id}/loans")
    public ResponseEntity<List<Loan>> getBookLoans(@PathVariable Long id) {
        List<Loan> loans = loanRepo.findByBookId(id);
        return loans.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(loans);
    }
}
