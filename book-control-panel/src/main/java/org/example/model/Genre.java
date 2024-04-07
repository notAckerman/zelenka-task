package org.example.model;

import lombok.Data;

@Data
public class Genre {
    private Long id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
