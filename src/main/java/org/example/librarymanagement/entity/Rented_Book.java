package org.example.librarymanagement.entity;

import javafx.collections.ObservableList;
import org.example.librarymanagement.DatabaseConnection;

import java.time.LocalDate;

public class Rented_Book {
    private final String ISBN;
    private final int userID;
    private final LocalDate borrowDate;
    private final LocalDate returnDate;
    private int ID;

    public Rented_Book(String ISBN, int userID, LocalDate borrowDate, LocalDate returnDate) {
        this.ISBN = ISBN;
        this.userID = userID;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public static boolean rentBook(String ISBN, int userID, LocalDate returnDate) {
        return DatabaseConnection.rentBook(ISBN, userID, returnDate);
    }

    public static boolean returnBook(int rentedId, String isbn, LocalDate returnDate) {
        return DatabaseConnection.returnBook(rentedId, isbn, returnDate);
    }

    public static ObservableList<Rented_Book> getAllRentedBooks() {
        return DatabaseConnection.getAllRentedBooks();
    }

    public static ObservableList<Rented_Book> getRentedBooksByUser(int userId) {
        return DatabaseConnection.getRentedBooksByUser(userId);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getUserID() {
        return userID;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

}
