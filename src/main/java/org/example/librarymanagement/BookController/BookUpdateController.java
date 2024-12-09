package org.example.librarymanagement.BookController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.Book;
import org.example.librarymanagement.UIHelper;

import java.util.List;

public class BookUpdateController {

    @FXML
    private ComboBox<String> cbBookTitle;

    @FXML
    private TextField txtBookTitle;

    @FXML
    private TextField txtAuthor;


    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtPageNumber;


    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtDescription;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnBack;

    @FXML
    private void initialize() {
        try {
            List<Book> books = Book.getAllBooks();

            ObservableList<String> bookTitles = FXCollections.observableArrayList();
            for (Book book : books) {
                bookTitles.add(book.getTitle());
            }

            cbBookTitle.setItems(bookTitles);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(AlertType.ERROR, "Đã xảy ra lỗi khi tải danh sách sách.");
        }
    }

    @FXML
    private void actionBookUpdate(MouseEvent event) {
        String selectedBookTitle = cbBookTitle.getValue();

        if (selectedBookTitle != null) {
            try {
                Book book = new Book();
                book.setTitle(selectedBookTitle);
                Book foundBook = book.read();

                if (foundBook != null) {
                    String title = txtBookTitle.getText();
                    String author = txtAuthor.getText();
                    int year = Integer.parseInt(txtYear.getText());
                    int pageNumber = Integer.parseInt(txtPageNumber.getText());
                    int quantity = Integer.parseInt(txtQuantity.getText());
                    String description = txtDescription.getText();

                    foundBook.setTitle(title);
                    foundBook.setAuthor(author);
                    foundBook.setYear(year);
                    foundBook.setPageNumber(pageNumber);
                    foundBook.setQuantity(quantity);
                    foundBook.setDescription(description);

                    foundBook.update();

                    UIHelper.showAlert(AlertType.INFORMATION, "Cập nhật sách thành công!");
                } else {
                    UIHelper.showAlert(AlertType.ERROR, "Sách không được tìm thấy.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                UIHelper.showAlert(AlertType.ERROR, "Có lỗi xảy ra khi cập nhật sách.");
            }
        } else {
            UIHelper.showAlert(AlertType.ERROR, "Vui lòng chọn một cuốn sách để cập nhật.");
        }
    }


    @FXML
    private void writeInfos() {
        try {
            String selectedTitle = cbBookTitle.getValue();
            if (selectedTitle == null || selectedTitle.isEmpty()) {
                System.out.println("No book title selected.");
                return;
            }

            Book book = new Book();
            book.setTitle(selectedTitle);
            Book newBook = book.read();

            if (newBook != null) {
                txtBookTitle.setText(selectedTitle);
                txtAuthor.setText(newBook.getAuthor());
                txtYear.setText(String.valueOf(newBook.getYear()));
                txtPageNumber.setText(String.valueOf(newBook.getPageNumber()));
                txtQuantity.setText(String.valueOf(newBook.getQuantity()));
                txtDescription.setText(newBook.getDescription());
            } else {
                System.out.println("Book not found.");
                txtBookTitle.clear();
                txtAuthor.clear();
                txtYear.clear();
                txtPageNumber.clear();
                txtQuantity.clear();
                txtDescription.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "admin_book", "Quay lại menu chính");
    }
}
