package com.example.practice1antonovayulia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.practice1antonovayulia.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {}
