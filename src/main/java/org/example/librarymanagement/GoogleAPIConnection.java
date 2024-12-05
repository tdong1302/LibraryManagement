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
        
        Books.Volumes.List volumes = books.volumes().list("english");
        volumes.setKey(API_KEY);
        
        Volumes volumesResponse = volumes.execute();
        
        if (volumesResponse.getItems() != null) {
            for (Volume volume : volumesResponse.getItems()) {
                Book book = getBook(volume);
                DatabaseConnection.createBook(book);
            }
        } else {
            System.out.println("Không tìm thấy sách.");
        }
    }

    private static Book getBook(Volume volume) {
        String ISBN = volume.getVolumeInfo().getIndustryIdentifiers() != null ? volume.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier() : null;
        String author = volume.getVolumeInfo().getAuthors() != null ? volume.getVolumeInfo().getAuthors().get(0) : "Unknown";
        String title = volume.getVolumeInfo().getTitle() != null ? volume.getVolumeInfo().getTitle() : "No title";
        int year = volume.getVolumeInfo().getPublishedDate() != null ? Integer.parseInt(volume.getVolumeInfo().getPublishedDate().split("-")[0]) : 0;
        int pageNumber = volume.getVolumeInfo().getPageCount() != null ? volume.getVolumeInfo().getPageCount() : 0;
        String description = volume.getVolumeInfo().getDescription() != null ? volume.getVolumeInfo().getDescription() : "";
        Book book = new Book(author, ISBN, title, year, pageNumber, 0, description);
        System.out.print("des:" + book.getDescription());
        return book;
    }
}
