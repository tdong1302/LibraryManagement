package org.example.librarymanagement.UserController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.User;
import org.example.librarymanagement.DatabaseConnection;
import org.example.librarymanagement.UIHelper;

import java.util.List;

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
            List<User> users = User.getAllUsers();

            ObservableList<String> userNames = FXCollections.observableArrayList();
            for (User user : users) {
                userNames.add(user.getFullName());
            }

            cbUserName.setItems(userNames);
            txtUserName.setEditable(false);
            txtUserEmail.setEditable(false);
            txtUserPhone.setEditable(false);
            txtUserAddress.setEditable(false);
            cbUserName.setItems(userNames);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "lỗi init list user");
        }
    }

    @FXML
    private void actionUserDelete(MouseEvent event) {
        String selectedUserName = cbUserName.getValue();
        if (selectedUserName != null) {
            try {
                User user = new User();
                user.setFullName(selectedUserName);
                User existingUser = user.read();
                if (existingUser != null) {
                    existingUser.delete();
                    UIHelper.showAlert(Alert.AlertType.INFORMATION, "đã xoá người dùng thành công");
                    cbUserName.getItems().remove(selectedUserName);
                }
            } catch (Exception e) {
                e.printStackTrace();
                UIHelper.showAlert(Alert.AlertType.ERROR, "k tìm thấy người dùng" + e.getMessage());
            }
        } else {
            UIHelper.showAlert(Alert.AlertType.ERROR, "vui lòng chọn 1 người dùng để xoá");
        }
    }

    @FXML
    private void writeInfos() {
        try {
            String selectedUserName = cbUserName.getValue();

            User user = DatabaseConnection.findUserByFullName(selectedUserName);

            if (user != null) {
                txtUserName.setText(user.getFullName());
                txtUserEmail.setText(user.getEmail());
                txtUserPhone.setText(user.getPhone());
                txtUserAddress.setText(user.getAddress());
            } else {
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
        UIHelper.switchWindow(event, "admin_user", "Back to main menu");
    }
}
