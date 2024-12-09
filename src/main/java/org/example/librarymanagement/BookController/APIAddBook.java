package org.example.librarymanagement.BookController;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.Book;
import org.example.librarymanagement.GoogleAPIConnection;
import org.example.librarymanagement.UIHelper;

public class APIAddBook {

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSearchKeyword;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnBack;

    @FXML
    private TableView<Book> tableViewBooks;

    @FXML
    private TableColumn<Book, String> columnTitle;

    @FXML
    private TableColumn<Book, String> columnAuthor;

    @FXML
    private TableColumn<Book, String> columnISBN;

    @FXML
    private TableColumn<Book, Integer> columnYear;

    @FXML
    private TableColumn<Book, Integer> columnPageNumber;

    @FXML
    void actionSearch(MouseEvent event) {
        try {
            String keyword = txtSearchKeyword.getText().trim();

            if (keyword.isEmpty()) {
                UIHelper.showAlert(Alert.AlertType.WARNING, "Vui lòng nhập từ khóa tìm kiếm.");
                return;
            }

            ObservableList<Book> booksList = GoogleAPIConnection.searchBooksFromAPI(keyword);

            tableViewBooks.setItems(booksList);

            UIHelper.showAlert(Alert.AlertType.INFORMATION, "Tìm kiếm thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Đã xảy ra lỗi trong quá trình tìm kiếm.");
        }
    }

    @FXML
    void actionBorrow(MouseEvent event) {
        Book selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            UIHelper.showAlert(Alert.AlertType.WARNING, "Vui lòng chọn một cuốn sách để mượn.");
            return;
        }

        String quantityText = txtQuantity.getText().trim();
        if (quantityText.isEmpty()) {
            UIHelper.showAlert(Alert.AlertType.WARNING, "Vui lòng nhập số lượng sách.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                UIHelper.showAlert(Alert.AlertType.WARNING, "Số lượng phải lớn hơn 0.");
                return;
            }
        } catch (NumberFormatException e) {
            UIHelper.showAlert(Alert.AlertType.WARNING, "Số lượng không hợp lệ.");
            return;
        }

        selectedBook.setQuantity(quantity);

        try {
            selectedBook.create();
            UIHelper.showAlert(Alert.AlertType.INFORMATION, "Mượn sách thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Đã xảy ra lỗi trong quá trình mượn sách.");
        }
    }


    @FXML
    void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "admin_book", "Back to User Menu");
    }

    @FXML
    public void initialize() {
        columnTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        columnAuthor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        columnISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
        columnYear.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYear()).asObject());
        columnPageNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPageNumber()).asObject());
    }
}