package org.example.librarymanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.librarymanagement.entity.User;

public class LoginController {

    private static User currentUser;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    public static User getCurrentUser() {
        return currentUser;
    }

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
            currentUser = user;

            try {
                String user_email = user.getEmail();
                if ("admin".equals(user_email)) {
                    UIHelper.openWindow("admin", "Library Management System - Admin");
                    Stage currentStage = (Stage) emailField.getScene().getWindow();
                    currentStage.close();
                } else {
                    UIHelper.openWindow("user", "Library Management System - User");
                    Stage currentStage = (Stage) emailField.getScene().getWindow();
                    currentStage.close();
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