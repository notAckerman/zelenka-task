package com.example.bookapi.service;

import com.example.bookapi.model.Genre;
import com.example.bookapi.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService implements IGenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Genre findGenre(String name) {
        return genreRepository.findGenreByName(name).orElse(null);
    }

    @Override
    public void addGenre(Genre genre) {
        if (!existsGenre(genre)) {
            genreRepository.save(genre);
        }
    }

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    public boolean existsGenre(Genre genre) {
        return findGenre(genre.getName()) != null;
    }
}
