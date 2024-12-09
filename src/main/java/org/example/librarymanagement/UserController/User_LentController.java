package org.example.librarymanagement.UserController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.Book;
import org.example.librarymanagement.Class.Rented_Book;
import org.example.librarymanagement.Class.User;
import org.example.librarymanagement.LoginController;
import org.example.librarymanagement.UIHelper;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class User_LentController implements Initializable {


    @FXML
    private Button btnBack;

    @FXML
    private TextField txtSearchBook;

    @FXML
    private TableView<Book> bookTable;

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

    @FXML
    private TableView<Rented_Book> rentedTable;

    @FXML
    private TableColumn<Rented_Book, Integer> rentedIdColumn;

    @FXML
    private TableColumn<Rented_Book, String> rentedIsbnColumn;

    @FXML
    private TableColumn<Rented_Book, Integer> rentedUserIdColumn;

    @FXML
    private TableColumn<Rented_Book, LocalDate> rentedDateColumn;

    @FXML
    private TableColumn<Rented_Book, LocalDate> rentedReturnDateColumn;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    private ObservableList<Rented_Book> rentedBooks = FXCollections.observableArrayList();
    private User currentUser = LoginController.getCurrentUser();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeBookColumns();
        initializeRentedBookColumns();
        loadBookData();
        loadRentedBooks();
    }

    private void initializeBookColumns() {
        bookISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        bookQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        bookYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        bookPageNumberColumn.setCellValueFactory(new PropertyValueFactory<>("pageNumber"));
    }

    private void initializeRentedBookColumns() {
        rentedIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        rentedIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        rentedUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        rentedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        rentedReturnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    private void loadBookData() {
        try {
            ObservableList<Book> books = Book.getAllBooks();
            setBookList(books);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Không thể tải danh sách sách.");
        }
    }

    private void loadRentedBooks() {
        try {
            ObservableList<Rented_Book> rentedBooks = Rented_Book.getRentedBooksByUser(currentUser.getID());
            setRentedBooksList(rentedBooks);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Không thể tải danh sách sách đã mượn.");
        }
    }


    private void setRentedBooksList(ObservableList<Rented_Book> rentedBooks) {
        this.rentedBooks.setAll(rentedBooks);
        rentedTable.setItems(this.rentedBooks);
    }

    public void setBookList(ObservableList<Book> books) {
        bookList.setAll(books);
        bookTable.setItems(bookList);
    }

    @FXML
    private void actionSearchBook(KeyEvent event) {
        String searchText = txtSearchBook.getText().toLowerCase();
        ObservableList<Book> filteredList = FXCollections.observableArrayList();
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(searchText) ||
                    book.getAuthor().toLowerCase().contains(searchText) ||
                    book.getISBN().toLowerCase().contains(searchText)) {
                filteredList.add(book);
            }
        }
        bookTable.setItems(filteredList);
    }

    @FXML
    private void handleRentBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            UIHelper.showAlert(Alert.AlertType.WARNING, "Vui lòng chọn một cuốn sách để mượn.");
            return;
        }

        if (selectedBook.getQuantity() <= 0) {
            UIHelper.showAlert(Alert.AlertType.WARNING, "Sách đã hết.");
            return;
        }

        Rented_Book rentedBook = new Rented_Book(
                selectedBook.getISBN(),
                currentUser.getID(),
                LocalDate.now(),
                LocalDate.now().plusWeeks(2)
        );

        if (Rented_Book.rentBook(selectedBook.getISBN(), currentUser.getID(), rentedBook.getReturnDate())) {
            currentUser.addRentedBook(rentedBook);
            loadBookData();
            loadRentedBooks();
            UIHelper.showAlert(Alert.AlertType.INFORMATION, "Mượn sách thành công!");
        } else {
            UIHelper.showAlert(Alert.AlertType.ERROR, "Mượn sách thất bại.");
        }
    }


    @FXML
    private void handleReturnBook() {
        Rented_Book selectedRentedBook = rentedTable.getSelectionModel().getSelectedItem();
        if (selectedRentedBook == null) {
            UIHelper.showAlert(Alert.AlertType.WARNING, "Vui lòng chọn một cuốn sách để trả.");
            return;
        }

        int rentedId = selectedRentedBook.getID();
        String isbn = selectedRentedBook.getISBN();
        LocalDate returnDate = LocalDate.now();

        boolean isReturned = Rented_Book.returnBook(rentedId, isbn, returnDate);

        if (isReturned) {
            UIHelper.showAlert(Alert.AlertType.INFORMATION, "Trả sách thành công!");
            loadBookData();
            loadRentedBooks();
        } else {
            UIHelper.showAlert(Alert.AlertType.ERROR, "Trả sách thất bại. Vui lòng thử lại.");
        }
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "user", "Quay lại đăng nhập");
    }

}