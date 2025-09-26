package com.example.practice1antonovayulia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    private String title;
    private Integer yearPublished;
    private String isbn;
    @ManyToOne
    private Author author;
    @ManyToOne
    private Genre genre;
}
