package com.example.practice1antonovayulia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.practice1antonovayulia.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(Long authorId);

    List<Book> findByGenreId(Long genreId);
}
