package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.example.model.Book;
import org.example.model.Genre;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parser {
    public static String getId(String text) {
        String regex = "Id (\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        String id = null;
        if (matcher.find()) {
            id = matcher.group(1);
        }
        return id;
    }

    @SneakyThrows
    public static List<Book> getBooks(Response response) {
        ResponseBody body = response.body();
        List<Book> list = new ArrayList<>();
        if (body != null) {
            Type type = new TypeToken<List<Book>>() {
            }.getType();
            list = new Gson().fromJson(body.string(), type);
        }
        return list;
    }

    @SneakyThrows
    public static List<Genre> getGenres(Response response) {
        ResponseBody body = response.body();
        List<Genre> list = new ArrayList<>();
        if (body != null) {
            Type type = new TypeToken<List<Genre>>() {
            }.getType();
            list = new Gson().fromJson(body.string(), type);
        }
        return list;
    }

    @SneakyThrows
    public static List<String> parseGenres(List<Genre> list) {
        return list.stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public static Book getBook(Response response) {
        ResponseBody body = response.body();
        Book book = new Book();
        if (body != null) {
            book = new Gson().fromJson(body.string(), Book.class);
        }
        return book;
    }
}
