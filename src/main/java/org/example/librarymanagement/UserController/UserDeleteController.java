package org.example.librarymanagement.UserController;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.DatabaseConnection;
import org.example.librarymanagement.UIHelper;
import org.example.librarymanagement.Class.User;

public class UserDeleteController {

    @FXML
    private ComboBox<String> cbUserName;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtUserEmail;
    @FXML
    private TextField txtUserPhone;
    @FXML
    private TextField txtUserAddress;

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnBack;

    @FXML
    private void initialize() {
        try {
            // Retrieve all users
            List<User> users = User.getAllUsers();

            // Add user names to the ComboBox
            ObservableList<String> userNames = FXCollections.observableArrayList();
            for (User user : users) {
                userNames.add(user.getFullName());
            }

            // Set items for the ComboBox
            cbUserName.setItems(userNames);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "An error occurred while retrieving the user list.");
        }
    }

    @FXML
    private void actionUserDelete(MouseEvent event) {
        String selectedUserName = cbUserName.getValue();
        if (selectedUserName != null) {
            try {
                User user = new User();
                user.setFullName(selectedUserName);
                User existingUser = user.read();  // Assuming there is a method `read()` to find a user.
                if (existingUser != null) {
                    existingUser.delete();  // Assuming there is a delete method to remove the user.
                    UIHelper.showAlert(Alert.AlertType.INFORMATION, "User has been successfully deleted.");
                    cbUserName.getItems().remove(selectedUserName);  // Remove the deleted user from ComboBox
                } else {
                    UIHelper.showAlert(Alert.AlertType.ERROR, "User not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                UIHelper.showAlert(Alert.AlertType.ERROR, "An error occurred while deleting the user: " + e.getMessage());
            }
        } else {
            UIHelper.showAlert(Alert.AlertType.ERROR, "Please select a user to delete.");
        }
    }

    @FXML
    private void writeInfos() {
        try {
            String selectedUserName = cbUserName.getValue();
            if (selectedUserName == null || selectedUserName.isEmpty()) {
                System.out.println("No user selected.");
                return;
            }

            System.out.println("Selected User: " + selectedUserName);

            // Fetch user from database based on full name
            User user = DatabaseConnection.findUserByFullName(selectedUserName);

            if (user != null) {
                System.out.println("User found: " + user.getFullName());

                // Display user details in text fields
                txtUserName.setText(user.getFullName());
                txtUserEmail.setText(user.getEmail());
                txtUserPhone.setText(user.getPhone());
                txtUserAddress.setText(user.getAddress());
            } else {
                System.out.println("User not found.");
                // Clear the fields if user not found
                txtUserName.clear();
                txtUserEmail.clear();
                txtUserPhone.clear();
                txtUserAddress.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "admin", "Back to main menu");
    }
}
