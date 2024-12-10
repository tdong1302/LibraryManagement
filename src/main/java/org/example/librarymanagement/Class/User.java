package org.example.librarymanagement.Class;

import javafx.collections.ObservableList;
import org.example.librarymanagement.DatabaseConnection;

import java.util.ArrayList;

public class User implements Manage {
    private final ArrayList<Rented_Book> user_rented_books = new ArrayList<>();
    private int ID;
    private String fullName;
    private String password;
    private String address;
    private String phone;
    private String email;

    public User() {
    }

    public User(String email, String password, String fullName, String address, String phone) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
    }

    public static ObservableList<User> getAllUsers() {
        return DatabaseConnection.getAllUsers();
    }

    public static User checkUser(String email, String password) {
        return DatabaseConnection.checkUser(email, password);
    }

    @Override
    public void create() {
        DatabaseConnection.saveUser(this);
    }

    @Override
    public User read() {
        return DatabaseConnection.findUserByFullName(this.fullName);
    }

    @Override
    public void update() {
        DatabaseConnection.updateUser(this);
    }

    @Override
    public void delete() {
        DatabaseConnection.deleteUserByEmail(this.email);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void addRentedBook(Rented_Book rentedBook) {
        user_rented_books.add(rentedBook);
    }

    public boolean checkUserRentBook() {
        return DatabaseConnection.isUserRentedBooks(this.ID);
    }
}