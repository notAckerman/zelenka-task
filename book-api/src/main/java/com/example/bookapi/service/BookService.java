package com.example.bookapi.service;

import com.example.bookapi.model.Book;
import com.example.bookapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreService genreService;

    @Override
    public void addBook(Book book) {
        genreService.addGenre(book.getGenre());
        book.setGenre(genreService.findGenre(book.getGenre().getName()));
        if (!existsBook(book)) {
            bookRepository.save(book);
        }
    }

    @Override
    public Book findBook(Long id) {
        Optional<Book> book = bookRepository.getBookById(id);
        return book.orElse(null);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllByGenre(String genre) {
        return bookRepository.findAll().stream()
                .filter((book) -> book.getGenre().getName().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllByKeyword(String keyword) {
        return bookRepository.findAll().stream()
                .filter((book) -> book.toString().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }


    @Override
    public boolean existsBook(Book book) {
        return bookRepository.getBookByAuthorAndNameAndGenre(book.getAuthor(), book.getName(), book.getGenre()).isPresent();
    }

    @Override
    public boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
        return true;
    }
}
