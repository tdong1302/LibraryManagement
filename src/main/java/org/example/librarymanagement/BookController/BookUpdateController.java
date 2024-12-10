package org.example.librarymanagement.BookController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.Book;
import org.example.librarymanagement.UIHelper;

public class BookUpdateController {

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
    private TableColumn<Book, Integer> bookYearColumn;
    @FXML
    private TableColumn<Book, Integer> bookPageNumberColumn;
    @FXML
    private TableColumn<Book, Integer> bookQuantityColumn;
    @FXML
    private TableColumn<Book, String> bookDescriptionColumn;
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
    private void initialize() {
        initializeColumns();
        loadBookData();
    }

    private void initializeColumns() {
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        bookPageNumberColumn.setCellValueFactory(new PropertyValueFactory<>("pageNumber"));
        bookQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        bookDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void loadBookData() {
        try {
            ObservableList<Book> books = Book.getAllBooks();
            bookList.setAll(books);
            bookTableView.setItems(bookList);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Lỗi load book data.");
        }
    }

    @FXML
    private void clickTableView(MouseEvent event) {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            txtBookTitle.setText(selectedBook.getTitle());
            txtAuthor.setText(selectedBook.getAuthor());
            txtYear.setText(String.valueOf(selectedBook.getYear()));
            txtPageNumber.setText(String.valueOf(selectedBook.getPageNumber()));
            txtQuantity.setText(String.valueOf(selectedBook.getQuantity()));
            txtDescription.setText(selectedBook.getDescription());
        }
    }

    @FXML
    private void actionBookUpdate(MouseEvent event) {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            try {
                String title = txtBookTitle.getText().trim();
                String author = txtAuthor.getText().trim();
                String yearText = txtYear.getText().trim();
                String pageNumberText = txtPageNumber.getText().trim();
                String quantityText = txtQuantity.getText().trim();
                String description = txtDescription.getText().trim();

                if (title.isEmpty() || author.isEmpty() || yearText.isEmpty() || pageNumberText.isEmpty() || quantityText.isEmpty() || description.isEmpty()) {
                    UIHelper.showAlert(Alert.AlertType.ERROR, "Vui lòng điền đầy đủ thông tin sách.");
                    return;
                }

                int year = Integer.parseInt(yearText);
                int pageNumber = Integer.parseInt(pageNumberText);
                int quantity = Integer.parseInt(quantityText);

                selectedBook.setTitle(title);
                selectedBook.setAuthor(author);
                selectedBook.setYear(year);
                selectedBook.setPageNumber(pageNumber);
                selectedBook.setQuantity(quantity);
                selectedBook.setDescription(description);

                selectedBook.update();

                UIHelper.showAlert(Alert.AlertType.INFORMATION, "Cập nhật sách thành công");

                loadBookData();
            } catch (Exception e) {
                e.printStackTrace();
                UIHelper.showAlert(Alert.AlertType.ERROR, "Có lỗi xảy ra khi cập nhật sách");
            }
        } else {
            UIHelper.showAlert(Alert.AlertType.ERROR, "Vui lòng chọn một sách để cập nhật");
        }
    }


    @FXML
    private void actionSearchBook(KeyEvent event) {
        String searchText = txtSearchBook.getText().toLowerCase();

        ObservableList<Book> filteredList = FXCollections.observableArrayList();
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(searchText) ||
                    book.getAuthor().toLowerCase().contains(searchText) ||
                    String.valueOf(book.getYear()).contains(searchText)) {
                filteredList.add(book);
            }
        }

        bookTableView.setItems(filteredList);
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "admin_book", "Quay lại menu chính");
    }
}
