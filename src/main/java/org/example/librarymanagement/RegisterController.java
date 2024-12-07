package org.example.librarymanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.librarymanagement.Class.User;

public class RegisterController {

    @FXML
    private TextField registerEmailField;
    @FXML
    private TextField registerFullNameField;
    @FXML
    private TextField registerPasswordField;
    @FXML
    private TextField registerPhoneField;
    @FXML
    private TextField registerAddressField;
    @FXML
    private Button exit;

    @FXML
    private void handleRegister() {
        String fullName = registerFullNameField.getText();
        String email = registerEmailField.getText();
        String password = registerPasswordField.getText();
        String address = registerAddressField.getText();
        String phone = registerPhoneField.getText();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            UIHelper.showAlert(AlertType.ERROR, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        User newUser = new User(email, password, fullName, address, phone);
        newUser.create();

        UIHelper.showAlert(AlertType.INFORMATION, "Đăng ký thành công!");
        Stage currentStage = (Stage) registerEmailField.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void handleBack(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "login", "Back");
    }
}
