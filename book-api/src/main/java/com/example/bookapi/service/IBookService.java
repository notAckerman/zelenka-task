package com.example.bookapi.service;

import com.example.bookapi.model.Book;

import java.util.List;

public interface IBookService {
    void addBook(Book book);

    Book findBook(Long id);

    List<Book> findAll();
    List<Book> findAllByGenre(String genre);

    List<Book> findAllByKeyword(String keyword);

    boolean existsBook(Book book);

    boolean deleteBook(Long id);
}
