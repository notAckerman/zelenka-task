package com.example.bookapi.repository;

import com.example.bookapi.model.Book;
import com.example.bookapi.model.Genre;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> getBookById(Long id);

    void deleteById(Long id);

    Optional<Book> getBookByAuthorAndNameAndGenre(String author, String name, Genre genre);

    List<Book> findAll();

    List<Book> getBooksByGenre(Genre genre);

}
