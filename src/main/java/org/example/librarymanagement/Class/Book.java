package org.example.librarymanagement.Class;

import javafx.collections.ObservableList;
import org.example.librarymanagement.DatabaseConnection;

public class Book implements Manage {
    public String title;
    public int year;
    public int pageNumber;
    private String ISBN;
    private String author;
    private int quantity;
    private String description;

    public Book() {
    }

    public Book(String author, String ISBN, String title, int year, int pageNumber, int quantity, String description) {
        this.author = author;
        this.ISBN = ISBN;
        this.title = title;
        this.year = year;
        this.pageNumber = pageNumber;
        this.quantity = quantity;
        this.description = description;
    }

    public static ObservableList<Book> getAllBooks() {
        return DatabaseConnection.getAllBooks();
    }

    @Override
    public void create() {
        DatabaseConnection.createBook(this);
        ;
    }

    @Override
    public Book read() {
        return DatabaseConnection.findBookByTitle(this.title);
    }

    @Override
    public void update() {
        DatabaseConnection.updateBook(this);
    }

    @Override
    public void delete() {
        DatabaseConnection.deleteBookByTitle(this.title);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
