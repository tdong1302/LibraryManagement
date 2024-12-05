package org.example.librarymanagement.UserController;

import org.example.librarymanagement.Class.Book;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.*;
import org.example.librarymanagement.DatabaseConnection;

import java.sql.*;

public class GoogleAPIConnection {

    private static final String APPLICATION_NAME = "My Library App";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String API_KEY = "AIzaSyC5Y1csGjJ9FYs-oErBuChG9jINUGLvJaA";  // Thay thế bằng API Key của bạn

    public static void main(String[] args) throws Exception {
        Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
        
        Books.Volumes.List volumes = books.volumes().list("shoes");
        volumes.setKey(API_KEY);
        
        Volumes volumesResponse = volumes.execute();
        
        if (volumesResponse.getItems() != null) {
            for (Volume volume : volumesResponse.getItems()) {
                Book book = getBook(volume);
                saveBookToDatabase(book);
            }
        } else {
            System.out.println("No books found.");
        }
    }

    private static Book getBook(Volume volume) {
        String ISBN = volume.getVolumeInfo().getIndustryIdentifiers() != null ? volume.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier() : null;
        String author = volume.getVolumeInfo().getAuthors() != null ? volume.getVolumeInfo().getAuthors().get(0) : "Unknown";
        String title = volume.getVolumeInfo().getTitle();
        int year = volume.getVolumeInfo().getPublishedDate() != null ? Integer.parseInt(volume.getVolumeInfo().getPublishedDate().split("-")[0]) : 0;
        int pageNumber = volume.getVolumeInfo().getPageCount() != null ? volume.getVolumeInfo().getPageCount() : 0;
        String description = volume.getVolumeInfo().getDescription() != null ? volume.getVolumeInfo().getDescription() : "";
        Book book = new Book(author, ISBN, title, year, pageNumber, description);
        System.out.print("des:" + book.getDescription());
        return book;
    }

    // Hàm lưu thông tin sách vào cơ sở dữ liệu
    public static void saveBookToDatabase(Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Bước 1: Kết nối tới cơ sở dữ liệu
            connection = DatabaseConnection.getConnection();

            // Bước 2: Tạo câu lệnh SQL để lưu thông tin sách
            String sql = "INSERT INTO books (ISBN, author, title, year, pageNumber, description) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            // Bước 3: Thiết lập các tham số
            preparedStatement.setString(1, book.getISBN());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setInt(4, book.getYear());
            preparedStatement.setInt(5, book.getPageNumber());
            preparedStatement.setString(6, book.getDescription());

            // Bước 4: Thực thi câu lệnh SQL
            preparedStatement.executeUpdate();
            System.out.println("Book saved: " + book.getTitle());
            System.out.println("Description: " + book.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
