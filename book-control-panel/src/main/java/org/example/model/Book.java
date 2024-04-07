package org.example.model;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String name;
    private String author;
    private String description;
    private Genre genre;

    public Book(String name, String author, String description, Genre genre) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.genre = genre;
    }

    public Book() {
    }
}
