package org.example.librarymanagement;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.example.librarymanagement.Class.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GoogleAPIConnection {

    private static final String APPLICATION_NAME = "My Library App";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String API_KEY = "AIzaSyC5Y1csGjJ9FYs-oErBuChG9jINUGLvJaA";

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
        Book book = new Book(author, ISBN, title, year, pageNumber, 0, description);
        System.out.print("des:" + book.getDescription());
        return book;
    }

    public static void saveBookToDatabase(Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO books (ISBN, author, title, year, pageNumber, quantity, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, book.getISBN());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setInt(4, book.getYear());
            preparedStatement.setInt(5, book.getPageNumber());
            preparedStatement.setInt(6, book.getQuantity());
            preparedStatement.setString(7, book.getDescription());

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
