package org.example.librarymanagement.UserController;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.example.librarymanagement.Class.User;
import org.example.librarymanagement.LoginController;
import org.example.librarymanagement.UIHelper;

public class UserInfoController {

    private final User currentUser = LoginController.getCurrentUser();
    @FXML
    private Text userIdText;
    @FXML
    private Text fullNameText;
    @FXML
    private Text emailText;
    @FXML
    private Text addressText;
    @FXML
    private Text phoneText;

    @FXML
    public void initialize() {
        if (currentUser != null) {
            userIdText.setText("User ID: " + currentUser.getID());
            fullNameText.setText(currentUser.getFullName());
            emailText.setText(currentUser.getEmail());
            addressText.setText(currentUser.getAddress());
            phoneText.setText(currentUser.getPhone());
        } else {
            userIdText.setText("No user is logged in.");
            fullNameText.setText("");
            emailText.setText("");
            addressText.setText("");
            phoneText.setText("");
        }
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "user", "Quay lại đăng nhập");
    }

    @FXML
    private void actionUpdate(MouseEvent event) {
        UIHelper.switchWindow(event, "user_current_update", "Cập nhật thông tin người dùng");
    }

}
