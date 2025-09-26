package com.example.practice1antonovayulia.controller;

import jakarta.validation.constraints.NotNull;

public class GenreRequest {
    @NotNull
    private Long genreId;

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
