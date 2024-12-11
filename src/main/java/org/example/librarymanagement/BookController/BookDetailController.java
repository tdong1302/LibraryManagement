package org.example.librarymanagement.bookController;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.example.librarymanagement.entity.Book;

public class BookDetailController {

    @FXML
    private Text bookTitle;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookISBN;
    @FXML
    private Text bookYear;
    @FXML
    private Text bookPageNumber;
    @FXML
    private Text bookQuantity;
    @FXML
    private Text bookDescription;

    private Book book;

    public void setBook(Book book) {
        this.book = book;
        windowBookDetails();
    }

    private void windowBookDetails() {
        if (book != null) {
            bookTitle.setText("Title: " + book.getTitle());
            bookAuthor.setText("Author: " + book.getAuthor());
            bookISBN.setText("ISBN: " + book.getISBN());
            bookYear.setText("Year: " + book.getYear());
            bookPageNumber.setText("Pages: " + book.getPageNumber());
            bookQuantity.setText("Quantity: " + book.getQuantity());
            bookDescription.setText("Description: " + book.getDescription());
        }
    }
}
