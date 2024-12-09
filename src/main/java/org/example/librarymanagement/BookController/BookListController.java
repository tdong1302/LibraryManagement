package org.example.librarymanagement.BookController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.Book;
import org.example.librarymanagement.UIHelper;

import java.net.URL;
import java.util.ResourceBundle;

public class BookListController implements Initializable {

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

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeColumns();
        loadBookData();
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
            UIHelper.showAlert(Alert.AlertType.ERROR, "Failed to load book data.");
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
