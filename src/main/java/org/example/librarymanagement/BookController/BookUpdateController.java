package org.example.librarymanagement.BookController;

import java.util.List;

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

public class BookUpdateController {

    @FXML
    private ComboBox<String> cbBookTitle;

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
    private Button btnUpdate;

    @FXML
    private Button btnBack;

    @FXML
    private void initialize() {
        try {
            // Lấy danh sách tất cả sách
            List<Book> books = Book.getAllBooks();

            // Thêm tên sách vào ComboBox
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
        System.out.println("Debug: Tên sách được chọn để cập nhật: " + selectedBookTitle); // DEBUG

        if (selectedBookTitle != null) {
            try {
                Book book = new Book();
                book.setTitle(selectedBookTitle);
                Book foundBook = book.read();
                System.out.println("Debug: Sách tìm thấy: " + (foundBook != null ? foundBook.getTitle() : "Không tìm thấy")); // DEBUG

                if (foundBook != null) {
                    String title = txtBookTitle.getText();
                    String author = txtAuthor.getText();
                    String ISBN = txtISBN.getText();
                    int year = Integer.parseInt(txtYear.getText());
                    int pageNumber = Integer.parseInt(txtPageNumber.getText());

                    System.out.println("Debug: Thông tin sách trước khi cập nhật: " +
                            "Title=" + foundBook.getTitle() + ", Author=" + foundBook.getAuthor() +
                            ", ISBN=" + foundBook.getISBN() + ", Year=" + foundBook.getYear() +
                            ", PageNumber=" + foundBook.getPageNumber()); // DEBUG

                    foundBook.setTitle(title);
                    foundBook.setAuthor(author);
                    foundBook.setISBN(ISBN);
                    foundBook.setYear(year);
                    foundBook.setPageNumber(pageNumber);

                    foundBook.update();
                    System.out.println("Debug: Cập nhật sách thành công."); // DEBUG
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

            // Lấy thông tin sách từ CSDL
            Book book = new Book();
            book.setTitle(selectedTitle);
            Book newBook = book.read();
            // Nếu tìm thấy, hiển thị thông tin
            if (newBook != null) {
                txtBookTitle.setText(selectedTitle);
                txtAuthor.setText(newBook.getAuthor());
                txtISBN.setText(newBook.getISBN());
                txtYear.setText(String.valueOf(newBook.getYear()));
                txtPageNumber.setText(String.valueOf(newBook.getPageNumber()));
            } else {
                System.out.println("Book not found.");
                txtBookTitle.clear();
                txtAuthor.clear();
                txtISBN.clear();
                txtYear.clear();
                txtPageNumber.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "admin", "Quay lại menu chính");
    }
}
