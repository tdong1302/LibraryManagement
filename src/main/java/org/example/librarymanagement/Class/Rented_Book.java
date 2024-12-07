package org.example.librarymanagement.Class;

import javafx.collections.ObservableList;
import org.example.librarymanagement.DatabaseConnection;

import java.time.LocalDate;

public class Rented_Book {
    private int ID;
    private String ISBN;
    private int userID;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public Rented_Book(int ID, String ISBN, int userID, LocalDate borrowDate, LocalDate returnDate) {
        this.ID = ID;
        this.ISBN = ISBN;
        this.userID = userID;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getID() {
        return ID;
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
}
