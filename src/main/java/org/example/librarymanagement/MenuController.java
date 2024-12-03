package org.example.librarymanagement;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController {
    @FXML Button btnBookList;
    @FXML Button btnBookAdd;
    @FXML Button btnBookUpdate;
    @FXML Button btnBookDelete;
    @FXML Button btnUserList;
    @FXML Button btnUserAdd;
    @FXML Button btnUserUpdate;
    @FXML Button btnUserDelete;
    @FXML Button btnExit1;
    @FXML Button btnExit2;

    @FXML
    private void openBookList(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "bookList", "Books");
    }
    @FXML
    private void openBookAdd(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "bookAdd", "Add Book");
    }
    @FXML
    private void openBookUpdate(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "bookUpdate", "Update Book");
    }
    @FXML
    private void openBookDelete(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "bookDelete", "Delete Book");
    }
    @FXML
    private void openUserList(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "userList", "Users");
    }
    @FXML
    private void openUserAdd(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "userAdd", "Add User");
    }
    @FXML
    private void openUserUpdate(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "userUpdate", "Update User");
    }
    @FXML
    private void openUserDelete(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "userDelete", "Delete User");
    }

    @FXML
    private void exitMenu(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "login", "Exit");
    }
}
