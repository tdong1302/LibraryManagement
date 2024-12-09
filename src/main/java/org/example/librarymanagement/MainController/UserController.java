package org.example.librarymanagement.MainController;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.UIHelper;

public class UserController {


    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "login", "Quay lại đăng nhập");
    }

    @FXML
    private void actionBorrowBook(MouseEvent event) {
        UIHelper.switchWindow(event, "user_lent_returnbook", "mượn trả sách");
    }

    @FXML
    private void actionViewUserInfo(MouseEvent event) {
        UIHelper.switchWindow(event, "infoUser", "Thông tin người dùng");
    }

}