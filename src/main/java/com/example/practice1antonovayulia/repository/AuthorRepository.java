package com.example.practice1antonovayulia.repository;

import com.example.practice1antonovayulia.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.practice1antonovayulia.model.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @EntityGraph(attributePaths = {"books"})
    Author findAuthorWithBooksById(Long id); // Повертає автора з книгами
}