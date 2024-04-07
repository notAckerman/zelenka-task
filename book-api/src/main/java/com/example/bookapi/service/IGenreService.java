package com.example.bookapi.service;

import com.example.bookapi.model.Genre;

import java.util.List;

public interface IGenreService {
    Genre findGenre(String name);
    List<Genre> getGenres();
    void addGenre(Genre genre);
    boolean existsGenre(Genre genre);
}
