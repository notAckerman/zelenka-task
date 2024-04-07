package com.example.bookapi.controller;

import com.example.bookapi.model.Genre;
import com.example.bookapi.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genre")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping("/add")
    public void addGenre(@RequestBody Genre genre) {
        genreService.addGenre(genre);
    }

    @GetMapping("/get")
    public List<Genre> getGenres() {
        return genreService.getGenres();
    }
}
