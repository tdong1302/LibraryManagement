package org.example.librarymanagement.bookController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.librarymanagement.entity.Book;
import org.example.librarymanagement.UIHelper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookListController implements Initializable {

    private final ObservableList<Book> bookList = FXCollections.observableArrayList();
    @FXML
    private TextField txtSearchBook;
    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private Button btnBack;
    @FXML
    private TableColumn<Book, String> bookTitleColumn;
    @FXML
    private TableColumn<Book, String> bookAuthorColumn;
    @FXML
    private TableColumn<Book, String> bookISBNColumn;
    @FXML
    private TableColumn<Book, Integer> bookYearColumn;
    @FXML
    private TableColumn<Book, Integer> bookPageNumberColumn;
    @FXML
    private TableColumn<Book, Integer> bookQuantityColumn;
    @FXML
    private TableColumn<Book, String> bookDescriptionColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeColumns();
        loadBookData();
        bookDetailsClicked();

    }

    private void bookDetailsClicked() {
        bookTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    openBookDetails(selectedBook);
                }
            }
        });
    }

    private void openBookDetails(Book selectedBook) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/librarymanagement/book_detail.fxml"));
            Parent root = loader.load();

            BookDetailController controller = loader.getController();
            controller.setBook(selectedBook);

            Stage stage = new Stage();
            stage.setTitle("Book Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeColumns() {
        bookISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        bookPageNumberColumn.setCellValueFactory(new PropertyValueFactory<>("pageNumber"));


        bookQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        bookDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void loadBookData() {
        try {
            ObservableList<Book> books = Book.getAllBooks();
            setBookList(books);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Lỗi load book data");
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

    public void setBookList(ObservableList<Book> books) {
        bookList.setAll(books);
        bookTableView.setItems(bookList);
    }

    @FXML
    void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "admin_book", "back to admin");
    }
}
