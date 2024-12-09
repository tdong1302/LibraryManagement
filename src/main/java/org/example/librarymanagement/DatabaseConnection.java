package org.example.librarymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.librarymanagement.Class.Book;
import org.example.librarymanagement.Class.Rented_Book;
import org.example.librarymanagement.Class.User;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_copy";
    private static final String USER = "root";
    private static final String PASSWORD = "trandong1302";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createBook(Book book) {
        String sql = "INSERT INTO books (ISBN, author, title, year, pageNumber, quantity, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getISBN());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getTitle());
            stmt.setInt(4, book.getYear());
            stmt.setInt(5, book.getPageNumber());
            stmt.setInt(6, book.getQuantity());
            stmt.setString(7, book.getDescription());
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
                        rs.getInt("pageNumber"),
                        rs.getInt("quantity"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, year = ?, pageNumber = ?, quantity = ?, description = ? WHERE ISBN = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYear());
            stmt.setInt(4, book.getPageNumber());
            stmt.setInt(5, book.getQuantity());
            stmt.setString(6, book.getDescription());
            stmt.setString(7, book.getISBN());

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
                        rs.getString("author"),
                        rs.getString("ISBN"),
                        rs.getString("title"),
                        rs.getInt("year"),
                        rs.getInt("pageNumber"),
                        rs.getInt("quantity"),
                        rs.getString("description")
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
                User user = new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
                user.setID(rs.getInt("ID"));
                users.add(user);
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
                User user = new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
                user.setID(rs.getInt("ID"));
                return user;
            } else {
                System.out.println("User not found with the given email and password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean rentBook(String ISBN, int userID, LocalDate returnDate) {
        String updateBooksQuery = "UPDATE books SET quantity = quantity - 1 WHERE ISBN = ?";
        String insertRentQuery = "INSERT INTO book_lent (ISBN, userID, borrowDate, returnDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateBooksQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertRentQuery)) {

            updateStmt.setString(1, ISBN);
            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0) {
                insertStmt.setString(1, ISBN);
                insertStmt.setInt(2, userID);
                insertStmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                insertStmt.setDate(4, java.sql.Date.valueOf(returnDate));

                int rowsInserted = insertStmt.executeUpdate();
                return rowsInserted > 0;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean returnBook(int rentedId, String isbn, LocalDate returnDate) {
        String updateBooksQuery = "UPDATE books SET quantity = quantity + 1 WHERE ISBN = ?";
        String deleteRentedQuery = "DELETE FROM book_lent WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(updateBooksQuery);
             PreparedStatement deleteStmt = connection.prepareStatement(deleteRentedQuery)) {

            updateStmt.setString(1, isbn);
            updateStmt.executeUpdate();

            deleteStmt.setInt(1, rentedId);
            deleteStmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Rented_Book> getAllRentedBooks() {
        ObservableList<Rented_Book> rentedBooks = FXCollections.observableArrayList();
        String sql = "SELECT ID, ISBN, userID, borrowDate, returnDate FROM book_lent";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String ISBN = rs.getString("ISBN");
                int userID = rs.getInt("userID");
                LocalDate borrowDate = rs.getDate("borrowDate").toLocalDate();
                LocalDate returnDate = rs.getDate("returnDate") != null ? rs.getDate("returnDate").toLocalDate() : null;

                Rented_Book rentedBook = new Rented_Book(ISBN, userID, borrowDate, returnDate);
                rentedBook.setID(rs.getInt("ID"));

                rentedBooks.add(rentedBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentedBooks;
    }

    public static ObservableList<Rented_Book> getRentedBooksByUser(int userId) {
        String selectSQL = "SELECT ID, ISBN, userID, borrowDate, returnDate FROM book_lent WHERE userID = ?";
        ObservableList<Rented_Book> rentedBooks = FXCollections.observableArrayList();

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String ISBN = rs.getString("ISBN");
                int userID = rs.getInt("userID");
                LocalDate borrowDate = rs.getDate("borrowDate").toLocalDate();
                LocalDate returnDate = rs.getDate("returnDate") != null ? rs.getDate("returnDate").toLocalDate() : null;

                Rented_Book rentedBook = new Rented_Book(ISBN, userID, borrowDate, returnDate);
                rentedBook.setID(rs.getInt("ID"));

                rentedBooks.add(rentedBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentedBooks;
    }

    public static boolean isBookBeingLent(String isbn) {
        String checkSQL = "SELECT COUNT(*) FROM book_lent WHERE ISBN = ?";
        boolean isBeingLent = false;

        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(checkSQL)) {
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                isBeingLent = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isBeingLent;
    }


}