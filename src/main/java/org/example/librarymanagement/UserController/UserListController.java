package org.example.librarymanagement.UserController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.User;
import org.example.librarymanagement.UIHelper;

import java.net.URL;
import java.util.ResourceBundle;

public class UserListController implements Initializable {

    @FXML
    private TextField txtSearchUser;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<User, String> userNameColumn;

    @FXML
    private TableColumn<User, String> userEmailColumn;

    @FXML
    private TableColumn<User, String> userPhoneColumn;

    @FXML
    private TableColumn<User, String> userAddressColumn;

    @FXML
    private TableColumn<User, String> userPasswordColumn;

    @FXML
    private TableColumn<User, Integer> userIDColumn;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeColumns();
        loadUserData();
    }

    private void initializeColumns() {
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        userPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void loadUserData() {
        try {
            ObservableList<User> users = User.getAllUsers();
            setUserList(users);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(Alert.AlertType.ERROR, "Failed to load user data.");
        }
    }

    @FXML
    private void actionSearchUser(KeyEvent event) {
        String searchText = txtSearchUser.getText().toLowerCase();

        ObservableList<User> filteredList = FXCollections.observableArrayList();
        for (User user : userList) {
            if (user.getFullName().toLowerCase().contains(searchText) ||
                    user.getEmail().toLowerCase().contains(searchText) ||
                    user.getPhone().toLowerCase().contains(searchText) ||
                    user.getAddress().toLowerCase().contains(searchText)) {
                filteredList.add(user);
            }
        }

        userTableView.setItems(filteredList);
    }

    public void setUserList(ObservableList<User> users) {
        userList.setAll(users);
        userTableView.setItems(userList);
    }

    @FXML
    void actionBack(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "admin_user", "back to admin");
    }
}
