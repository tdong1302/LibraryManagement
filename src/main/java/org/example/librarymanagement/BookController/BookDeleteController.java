package org.example.librarymanagement.BookController;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.Book;
import org.example.librarymanagement.UIHelper;

public class BookDeleteController {
    @FXML
    private ComboBox<String> cbBookTitle;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtISBN;
    @FXML
    private TextField txtYear;
    @FXML
    private TextField txtQuantity;


    @FXML
    private Button btnDelete;
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
            txtAuthor.setEditable(false);
            txtISBN.setEditable(false);
            txtYear.setEditable(false);
            txtQuantity.setEditable(false);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.INFORMATION, "Lỗi init book");
        }
    }

    @FXML
    private void actionBookDelete(MouseEvent event) {
        String selectedBookTitle = cbBookTitle.getValue();
        if (selectedBookTitle != null) {
            try {
                Book book = new Book();
                book.setTitle(selectedBookTitle);
                Book newBook = book.read();
                if (newBook != null) {
                    newBook.delete();
                    UIHelper.showAlert(Alert.AlertType.INFORMATION, "Sách đã được xoá thành công");
                    cbBookTitle.getItems().remove(selectedBookTitle);
                }
            } catch (Exception e) {
                e.printStackTrace();
                UIHelper.showAlert(Alert.AlertType.ERROR, "Sách không tồn tại, Vui lòng chọn lại sách " + e.getMessage());
            }
        } else {
            UIHelper.showAlert(Alert.AlertType.ERROR, "Vui lòng chọn 1 sách để xoá");
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

            // Lấy thông tin sách từ CSDL
            Book book = new Book();
            book.setTitle(selectedTitle);
            Book newBook = book.read();

            // Nếu tìm thấy, hiển thị thông tin
            if (newBook != null) {
                txtAuthor.setText(newBook.getAuthor());
                txtISBN.setText(newBook.getISBN());
                txtYear.setText(String.valueOf(newBook.getYear()));
                txtQuantity.setText(String.valueOf(newBook.getQuantity()));
            } else {
                System.out.println("Book not found.");
                txtAuthor.clear();
                txtISBN.clear();
                txtYear.clear();
                txtQuantity.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.openWindowAndClose(event,"admin_book", "back to main");
    }

}

