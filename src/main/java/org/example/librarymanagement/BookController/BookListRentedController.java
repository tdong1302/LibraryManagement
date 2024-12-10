package org.example.librarymanagement.BookController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.Rented_Book;
import org.example.librarymanagement.UIHelper;

public class BookListRentedController {

    private final ObservableList<Rented_Book> rentedBooksList = FXCollections.observableArrayList();
    @FXML
    private TableView<Rented_Book> rentedBookTable;
    @FXML
    private TableColumn<Rented_Book, Integer> rentedBookIdColumn;
    @FXML
    private TableColumn<Rented_Book, String> rentedBookIsbnColumn;
    @FXML
    private TableColumn<Rented_Book, Integer> rentedBookUserIdColumn;
    @FXML
    private TableColumn<Rented_Book, String> rentedBookBorrowDateColumn;
    @FXML
    private TableColumn<Rented_Book, String> rentedBookReturnDateColumn;

    @FXML
    public void initialize() {
        initializeColumns();
        loadRentedBooksData();
    }

    private void initializeColumns() {
        rentedBookIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        rentedBookIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        rentedBookUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        rentedBookBorrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        rentedBookReturnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    private void loadRentedBooksData() {
        rentedBooksList.setAll(Rented_Book.getAllRentedBooks());
        rentedBookTable.setItems(rentedBooksList);
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "admin", "Quay lại admin");
    }
}
