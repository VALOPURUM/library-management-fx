package com.library.librarymanagementui.librarymanagementfx.service;

import com.library.librarymanagementui.librarymanagementfx.model.Book;
import com.library.librarymanagementui.librarymanagementfx.util.HttpClientUtil;

import java.util.List;


public class BookService {
    private static final String BASE_URL = "http://localhost:8080/api/books";

    public List<Book> getAllBooks() {
        return HttpClientUtil.getRequest(BASE_URL);
    }

    public void addBook(String title, String author, String isbn, String publishedDate) {
        HttpClientUtil.postRequest(BASE_URL, new Book(null, title, author, isbn, publishedDate));
    }

    public void updateBook(Long id, String title, String author, String isbn, String publishedDate) {
        HttpClientUtil.putRequest(BASE_URL + "/" + id, new Book(id, title, author, isbn, publishedDate));
    }

    public void deleteBook(Long id) {
        HttpClientUtil.deleteRequest(BASE_URL + "/" + id);
    }

    public List<Book> searchBooks(String query) {
        return HttpClientUtil.getRequest(BASE_URL + "/searchBooksByTitleOrAuthor/" + query + "/" + query);
    }
}
