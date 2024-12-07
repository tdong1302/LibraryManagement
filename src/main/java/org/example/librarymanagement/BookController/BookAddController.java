package org.example.librarymanagement.BookController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.Book;
import org.example.librarymanagement.UIHelper;

public class BookAddController {

    @FXML
    private TextField txtBookTitle;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtPageNumber;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQuantity;


    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    void actionBookAdd(MouseEvent event) {
        try {
            String title = txtBookTitle.getText();
            String author = txtAuthor.getText();
            String ISBN = txtISBN.getText();
            int year = Integer.parseInt(txtYear.getText());
            int pageNumber = Integer.parseInt(txtPageNumber.getText());
            String description = txtDescription.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());

            Book newBook = new Book(author, ISBN, title, year, pageNumber, quantity, description);
            newBook.create();
            UIHelper.showAlert(Alert.AlertType.INFORMATION, "Sách đã được thêm thành công");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Lỗi thêm sách");
        }
    }

    @FXML
    void actionBack(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "admin_book", "back to admin");
    }
}
