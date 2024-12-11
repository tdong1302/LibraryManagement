package org.example.librarymanagement.bookController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.entity.Book;
import org.example.librarymanagement.UIHelper;

public class BookDeleteController {

    private final ObservableList<Book> bookList = FXCollections.observableArrayList();
    @FXML
    private TextField txtSearchBook;
    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, String> bookTitleColumn;
    @FXML
    private TableColumn<Book, String> bookAuthorColumn;
    @FXML
    private TableColumn<Book, String> bookISBNColumn;
    @FXML
    private TableColumn<Book, Integer> bookYearColumn;
    @FXML
    private TableColumn<Book, Integer> bookQuantityColumn;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnBack;

    @FXML
    private void initialize() {
        initializeColumns();
        loadBookData();
    }

    private void initializeColumns() {
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        bookYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        bookQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void loadBookData() {
        try {
            ObservableList<Book> books = Book.getAllBooks();
            bookList.setAll(books);
            bookTableView.setItems(bookList);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Lỗi load book data");
        }
    }

    @FXML
    private void actionBookDelete(MouseEvent event) {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            try {
                if (selectedBook.isBeingLent()) {
                    UIHelper.showAlert(Alert.AlertType.ERROR, "Sách này đang được mượn, không thể xoá.");
                    return;
                }

                selectedBook.delete();
                bookList.remove(selectedBook);
                UIHelper.showAlert(Alert.AlertType.INFORMATION, "Sách đã được xoá thành công.");
            } catch (Exception e) {
                e.printStackTrace();
                UIHelper.showAlert(Alert.AlertType.ERROR, "Có lỗi xảy ra: " + e.getMessage());
            }
        } else {
            UIHelper.showAlert(Alert.AlertType.ERROR, "Vui lòng chọn một sách để xoá");
        }
    }

    @FXML
    private void actionSearchBook(KeyEvent event) {
        String searchText = txtSearchBook.getText().toLowerCase();

        ObservableList<Book> filteredList = FXCollections.observableArrayList();
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(searchText) ||
                    book.getAuthor().toLowerCase().contains(searchText) ||
                    book.getISBN().toLowerCase().contains(searchText) ||
                    String.valueOf(book.getYear()).contains(searchText)) {
                filteredList.add(book);
            }
        }

        bookTableView.setItems(filteredList);
    }


    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "admin_book", "back to main");
    }
}
