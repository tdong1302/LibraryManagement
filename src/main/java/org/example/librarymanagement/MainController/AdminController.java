package org.example.librarymanagement.MainController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.Rented_Book;
import org.example.librarymanagement.UIHelper;

public class AdminController {

    @FXML
    private Button btnManageBook;

    @FXML
    private Button btnManageUser;

    @FXML
    private Button btnBack;

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

    private ObservableList<Rented_Book> rentedBooksList = FXCollections.observableArrayList();

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
    private void openBookManage(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "admin_book", "manage Book");
    }

    @FXML
    private void opeUserManage(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "admin_user", "manage User");
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "login", "Quay lại đăng nhập");
    }
}
