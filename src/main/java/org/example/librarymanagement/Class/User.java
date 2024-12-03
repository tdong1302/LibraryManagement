package org.example.librarymanagement.Class;

import javafx.collections.ObservableList;
import org.example.librarymanagement.DatabaseConnection;

public class User implements Manage{
    public static int nums = 0;
    private final int ID;
    private String fullName;
    private String password;
    private String address;
    private String phone;
    private String email;

    public User() {
        nums++;
        this.ID = nums;

    }
    public User(String email, String password, String fullName, String address, String phone) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        nums++;
        this.ID = nums;
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

    public static ObservableList<User> getAllUsers() {
        return DatabaseConnection.getAllUsers();
    }

    public static User checkUser(String email, String password) {
        return DatabaseConnection.checkUser(email, password);
    }

    public static int getNums() {
        return nums;
    }

    public static void setNums(int nums) {
        User.nums = nums;
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
}
