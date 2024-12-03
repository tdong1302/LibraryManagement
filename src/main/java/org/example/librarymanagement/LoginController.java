package org.example.librarymanagement;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.example.librarymanagement.Class.User;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void initialize() {
        emailField.setOnKeyPressed(this::handleEnterKey);
        passwordField.setOnKeyPressed(this::handleEnterKey);
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin();
        }
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = User.checkUser(email, password);

        if (user != null) {
            try {
                String user_email = user.getEmail();
                if ("admin".equals(user_email)) {
                    UIHelper.openWindow("admin", "Library Management System - Admin");
                    Stage currentStage = (Stage) emailField.getScene().getWindow();
                    currentStage.close();
                } else {
                    UIHelper.showAlert(Alert.AlertType.ERROR, "Tài khoản không hợp lệ.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            UIHelper.showAlert(Alert.AlertType.ERROR, "Sai email or password");
        }
    }

    @FXML
    private void handleRegister() {
        UIHelper.openWindow("register", "Register");
    }

}