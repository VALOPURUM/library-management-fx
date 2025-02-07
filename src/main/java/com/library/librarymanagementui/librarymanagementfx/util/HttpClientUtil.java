package com.library.librarymanagementui.librarymanagementfx.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.librarymanagementui.librarymanagementfx.model.Book;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpClientUtil {
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // GET Request - Fetches list of books
    public static List<Book> getRequest(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                return objectMapper.readValue(response.body(), new TypeReference<List<Book>>() {});
            } else {
                throw new RuntimeException("Failed to fetch books. HTTP Status: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error making GET request", e);
        }
    }

    // POST Request - Adds a new book
    public static void postRequest(String url, Book book) {
        try {
            String json = objectMapper.writeValueAsString(book);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed to add book. HTTP Status: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error making POST request", e);
        }
    }

    // PUT Request - Updates a book
    public static void putRequest(String url, Book book) {
        try {
            String json = objectMapper.writeValueAsString(book);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed to update book. HTTP Status: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error making PUT request", e);
        }
    }

    // DELETE Request - Deletes a book
    public static void deleteRequest(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                throw new RuntimeException("Failed to delete book. HTTP Status: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error making DELETE request", e);
        }
    }
}
