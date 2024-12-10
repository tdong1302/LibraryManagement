package org.example.librarymanagement.UserController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.User;
import org.example.librarymanagement.UIHelper;

public class UserUpdateController {

    private final ObservableList<User> userList = FXCollections.observableArrayList();
    @FXML
    private TextField txtSearchUser;
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> userEmailColumn;
    @FXML
    private TableColumn<User, String> userPhoneColumn;
    @FXML
    private TableColumn<User, String> userAddressColumn;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtUserEmail;
    @FXML
    private TextField txtUserPhone;
    @FXML
    private TextField txtUserAddress;
    @FXML
    private TextField txtUserPassword;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnBack;

    @FXML
    private void initialize() {
        initializeColumns();
        loadUserData();
    }

    private void initializeColumns() {
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadUserData() {
        try {
            ObservableList<User> users = User.getAllUsers();
            userList.setAll(users);
            userTableView.setItems(userList);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Lỗi load user data.");
        }
    }

    @FXML
    private void onTableViewClicked(MouseEvent event) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            txtUserName.setText(selectedUser.getFullName());
            txtUserEmail.setText(selectedUser.getEmail());
            txtUserPhone.setText(selectedUser.getPhone());
            txtUserAddress.setText(selectedUser.getAddress());
            txtUserPassword.setText(selectedUser.getPassword());
        }
    }

    @FXML
    private void actionUserUpdate(MouseEvent event) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                selectedUser.setFullName(txtUserName.getText());
                selectedUser.setEmail(txtUserEmail.getText());
                selectedUser.setPhone(txtUserPhone.getText());
                selectedUser.setAddress(txtUserAddress.getText());
                selectedUser.setPassword(txtUserPassword.getText());

                selectedUser.update();

                loadUserData();
                UIHelper.showAlert(Alert.AlertType.INFORMATION, "Cập nhật người dùng thành công");
            } catch (Exception e) {
                e.printStackTrace();
                UIHelper.showAlert(Alert.AlertType.ERROR, "Lỗi cập nhật người dunùng " + e.getMessage());
            }
        } else {
            UIHelper.showAlert(Alert.AlertType.ERROR, "Vui lòng chọn 1 người dùng để cập nhật");
        }
    }


    @FXML
    private void actionSearchUser(KeyEvent event) {
        String searchText = txtSearchUser.getText().toLowerCase();

        ObservableList<User> filteredList = FXCollections.observableArrayList();
        for (User user : userList) {
            if (user.getFullName().toLowerCase().contains(searchText) ||
                    user.getEmail().toLowerCase().contains(searchText) ||
                    user.getPhone().toLowerCase().contains(searchText)) {
                filteredList.add(user);
            }
        }

        userTableView.setItems(filteredList);
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "admin_user", "Back to main menu");
    }
}
