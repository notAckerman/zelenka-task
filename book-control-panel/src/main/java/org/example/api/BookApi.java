package org.example.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import okhttp3.*;
import org.example.model.Book;
import org.example.utils.Parser;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookApi {
    private static final OkHttpClient client = new OkHttpClient();

    @SneakyThrows
    public List<Book> getBooks() {
        List<Book> list;
        Request request = new Request.Builder()
                .url("http://localhost:8082/api/v1/book/get")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            list = Parser.getBooks(response);
        }
        return list;
    }

    @SneakyThrows
    public void addBook(Book book) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String json = new Gson().toJson(book);
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://localhost:8082/api/v1/book/add")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            System.out.println("Response: " + responseBody);
        }
    }

    @SneakyThrows
    public List<Book> searchBooksByGenre(String genre) {
        List<Book> list;
        Request request = new Request.Builder()
                .url("http://localhost:8082/api/v1/book/search/" + genre)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            list = Parser.getBooks(response);
        }
        return list;
    }

    @SneakyThrows
    public List<Book> searchBooks(String keyword) {
        List<Book> list;
        Request request = new Request.Builder()
                .url("http://localhost:8082/api/v1/book/search?keyword=" + keyword)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            list = Parser.getBooks(response);
        }
        return list;
    }

    @SneakyThrows
    public Book getBook(String id) {
        Book book;
        Request request = new Request.Builder()
                .url("http://localhost:8082/api/v1/book/get/" + id)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            book = Parser.getBook(response);
        }
        return book;
    }

    @SneakyThrows
    public void deleteBook(String id) {
        Request request = new Request.Builder()
                .url("http://localhost:8082/api/v1/book/delete/" + id)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        response.close();
    }
}
