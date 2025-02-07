package com.library.librarymanagementui.librarymanagementfx.controller;

import com.library.librarymanagementui.librarymanagementfx.model.Book;
import com.library.librarymanagementui.librarymanagementfx.service.BookService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;




public class BookController {
    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String> titleColumn, authorColumn, isbnColumn, dateColumn;
    @FXML private TextField titleField, authorField, isbnField, searchField;
    @FXML private DatePicker publishedDateField;
    @FXML private Button addButton, updateButton, deleteButton, refreshButton, searchButton;

    private final BookService bookService = new BookService();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublishedDate()));

        refreshBookList();
        setupTableSelection();
    }

    private void setupTableSelection() {
        bookTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                titleField.setText(newSelection.getTitle());
                authorField.setText(newSelection.getAuthor());
                isbnField.setText(newSelection.getIsbn());
                publishedDateField.setValue(java.time.LocalDate.parse(newSelection.getPublishedDate()));
            }
        });
    }

    @FXML
    private void handleAddBook() {
        bookService.addBook(titleField.getText(), authorField.getText(), isbnField.getText(), publishedDateField.getValue().toString());
        refreshBookList();
    }

    @FXML
    private void handleUpdateBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookService.updateBook(selectedBook.getId(), titleField.getText(), authorField.getText(), isbnField.getText(), publishedDateField.getValue().toString());
            refreshBookList();
        }
    }

    @FXML
    private void handleDeleteBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookService.deleteBook(selectedBook.getId());
            refreshBookList();
        }
    }

    @FXML
    private void handleSearch() {
        List<Book> books = bookService.searchBooks(searchField.getText());
        bookTable.setItems(FXCollections.observableArrayList(books));
    }

    private void refreshBookList() {
        List<Book> books = bookService.getAllBooks();
        bookTable.setItems(FXCollections.observableArrayList(books));
    }
}

