package com.example.bookapi.controller;

import com.example.bookapi.model.Book;
import com.example.bookapi.service.BookService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookService.findAllByKeyword(keyword);
    }

    @GetMapping("/search/{genre}")
    public List<Book> searchBooksByGenre(@PathVariable String genre) {
        return bookService.findAllByGenre(genre);
    }

    @PostMapping("/add")
    public void add(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public Book get(@PathVariable Long id) {
        return bookService.findBook(id);
    }

    @GetMapping("/get")
    @ResponseBody
    public List<Book> getAll() {
        return bookService.findAll();
    }
}
