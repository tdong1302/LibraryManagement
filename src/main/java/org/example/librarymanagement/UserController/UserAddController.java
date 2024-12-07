package org.example.librarymanagement.UserController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.User;
import org.example.librarymanagement.UIHelper;

public class UserAddController {

    @FXML
    TextField txtUserFullName;
    @FXML
    TextField txtUserPassword;
    @FXML
    TextField txtUserAddress;
    @FXML
    TextField txtUserPhone;
    @FXML
    TextField txtUserEmail;

    @FXML
    Button btnAdd;

    @FXML
    private void actionUserAdd(MouseEvent event) {
        try {
            String fullName = txtUserFullName.getText();
            String password = txtUserPassword.getText();
            String address = txtUserAddress.getText();
            String phone = txtUserPhone.getText();
            String email = txtUserEmail.getText();
            User user = new User(email, password, fullName, address, phone);
            user.create();
            UIHelper.showAlert(AlertType.INFORMATION, "User was successfully added");
        } catch (Exception e) {
            UIHelper.showAlert(AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "admin_user", "Quay lại menu chính");
    }

}
