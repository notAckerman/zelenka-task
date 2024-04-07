package org.example.api;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.model.Book;
import org.example.model.Genre;
import org.example.utils.Parser;

import java.util.List;

public class GenreApi {
    private static final OkHttpClient client = new OkHttpClient();

    @SneakyThrows
    public List<Genre> getGenres() {
        List<Genre> list;
        Request request = new Request.Builder()
                .url("http://localhost:8082/api/v1/genre/get")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            list = Parser.getGenres(response);
        }
        return list;
    }
}
