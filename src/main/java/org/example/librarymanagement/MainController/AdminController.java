package org.example.librarymanagement.MainController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.UIHelper;

public class AdminController {

    @FXML
    private Button btnManageBook;

    @FXML
    private Button btnManageUser;

    @FXML
    private Button btnBack;


    @FXML
    private void openBookManage(MouseEvent event) {
        UIHelper.switchWindow(event, "admin_book", "manage Book");
    }

    @FXML
    private void opeUserManage(MouseEvent event) {
        UIHelper.switchWindow(event, "admin_user", "manage User");
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "login", "Quay lại đăng nhập");
    }

    @FXML
    private void openBookRentedMange(MouseEvent event) {
        UIHelper.switchWindow(event, "bookListRented", "manage BookRented");
    }
}
