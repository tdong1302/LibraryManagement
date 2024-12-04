package org.example.librarymanagement.Class;

import java.sql.*;
import java.util.Date;
import org.example.librarymanagement.DatabaseConnection;

public class BookBorrow {
    private int id;
    private int userId;
    private String ISBN;
    private Date borrowDate;
    private Date returnDate;

    public BookBorrow(int userId, String ISBN, Date borrowDate) {
        this.userId = userId;
        this.ISBN = ISBN;
        this.borrowDate = borrowDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getISBN() {
        return ISBN;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
}
