package org.example.librarymanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


public class MenuController {
    @FXML
    Button btnBookList;
    @FXML
    Button btnBookAdd;
    @FXML
    Button btnBookUpdate;
    @FXML
    Button btnBookDelete;
    @FXML
    Button btnUserList;
    @FXML
    Button btnUserAdd;
    @FXML
    Button btnUserUpdate;
    @FXML
    Button btnUserDelete;
    @FXML
    Button btnExit1;
    @FXML
    Button btnExit2;

    @FXML
    private void openBookList(MouseEvent event) {
        UIHelper.switchWindow(event, "bookList", "Books");
    }

    @FXML
    private void openBookAdd(MouseEvent event) {
        UIHelper.switchWindow(event, "bookAdd", "Add Book");
    }

    @FXML
    private void openBookUpdate(MouseEvent event) {
        UIHelper.switchWindow(event, "bookUpdate", "Update Book");
    }

    @FXML
    private void openBookDelete(MouseEvent event) {
        UIHelper.switchWindow(event, "bookDelete", "Delete Book");
    }

    @FXML
    private void openUserList(MouseEvent event) {
        UIHelper.switchWindow(event, "userList", "Users");
    }

    @FXML
    private void openUserAdd(MouseEvent event) {
        UIHelper.switchWindow(event, "userAdd", "Add User");
    }

    @FXML
    private void openUserUpdate(MouseEvent event) {
        UIHelper.switchWindow(event, "userUpdate", "Update User");
    }

    @FXML
    private void openUserDelete(MouseEvent event) {
        UIHelper.switchWindow(event, "userDelete", "Delete User");
    }

    @FXML
    private void exitMenu(MouseEvent event) {
        UIHelper.switchWindow(event, "admin", "Exit");
    }

    @FXML
    private void openBookAPIAdd(MouseEvent event) {
        UIHelper.switchWindow(event, "apiaddbook", "Add Book API");
    }
}

