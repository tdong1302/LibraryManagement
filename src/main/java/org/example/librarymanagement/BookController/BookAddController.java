package org.example.librarymanagement.bookController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.UIHelper;
import org.example.librarymanagement.entity.Book;

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
        String title = txtBookTitle.getText();
        String author = txtAuthor.getText();
        String ISBN = txtISBN.getText();
        String yearText = txtYear.getText();
        String pageNumberText = txtPageNumber.getText();
        String description = txtDescription.getText();
        String quantityText = txtQuantity.getText();

        if (title.isEmpty() || author.isEmpty() || ISBN.isEmpty() || yearText.isEmpty() || pageNumberText.isEmpty() || description.isEmpty() || quantityText.isEmpty()) {
            UIHelper.showAlert(Alert.AlertType.ERROR, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        try {
            int year = Integer.parseInt(yearText);
            int pageNumber = Integer.parseInt(pageNumberText);
            int quantity = Integer.parseInt(quantityText);

            if (!ISBN.matches("\\d{6,}")) {
                UIHelper.showAlert(Alert.AlertType.ERROR, "ISBN phải chứa ít nhất 6 chữ số!");
                return;
            }

            if (year < 0 || year >= 2025) {
                UIHelper.showAlert(Alert.AlertType.ERROR, "Năm xuất bản không hợp lệ");
                return;
            }

            if (pageNumber <= 0) {
                UIHelper.showAlert(Alert.AlertType.ERROR, "Số trang phải lớn hơn 0");
                return;
            }

            if (quantity <= 0) {
                UIHelper.showAlert(Alert.AlertType.ERROR, "Số lượng sách phải lớn hơn 0");
                return;
            }

            Book newBook = new Book(author, ISBN, title, year, pageNumber, quantity, description);
            if (newBook.isISBNExists()) {
                UIHelper.showAlert(Alert.AlertType.WARNING, "Sách với ISBN này đã tồn tại trong cơ sở dữ liệu");
                return;
            }
            newBook.create();
            UIHelper.showAlert(Alert.AlertType.INFORMATION, "Sách đã được thêm thành công");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Lỗi thêm sách");
        }
    }

    @FXML
    void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "admin_book", "back to admin");
    }
}
