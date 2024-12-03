package org.example.librarymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.librarymanagement.Class.Book;
import org.example.librarymanagement.Class.User;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_copy";
    private static final String USER = "root";
    private static final String PASSWORD = "trandong1302";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createBook(Book book) {
        String sql = "INSERT INTO books (ISBN, author, title, year, pageNumber) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getISBN());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getTitle());
            stmt.setInt(4, book.getYear());
            stmt.setInt(5, book.getPageNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Book findBookByTitle(String title) {
        String sql = "SELECT * FROM books WHERE title = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getString("author"),
                        rs.getString("ISBN"),
                        rs.getString("title"),
                        rs.getInt("year"),
                        rs.getInt("pageNumber")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        public static void updateBook(Book book) {
            String sql = "UPDATE books SET title = ?, author = ?, year = ?, pageNumber = ? WHERE ISBN = ?";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setInt(3, book.getYear());
                stmt.setInt(4, book.getPageNumber());
                stmt.setString(5, book.getISBN());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Cập nhật sách thành công.");
                } else {
                    System.out.println("Không có sách nào được cập nhật.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    public static void deleteBookByTitle(String title) {
        String sql = "DELETE FROM books WHERE title = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Book> getAllBooks() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String sql = "SELECT * FROM books";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("ISBN"),
                        rs.getString("author"),
                        rs.getString("title"),
                        rs.getInt("year"),
                        rs.getInt("pageNumber")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void saveUser(User user) {
        String sql = "INSERT INTO users (fullName, password, address, email, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User findUserByFullName(String fullName) {
        String sql = "SELECT * FROM users WHERE fullName = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void updateUser(User user) {
        String sql = "UPDATE users SET fullName = ?, password = ?, address = ?, email = ? WHERE phone = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserByEmail(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("address"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User checkUser(String email, String password) {
        String selectSQL = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            } else {
                System.out.println("User not found with the given email and password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}