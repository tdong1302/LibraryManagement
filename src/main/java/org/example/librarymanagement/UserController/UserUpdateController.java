package org.example.librarymanagement.UserController;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.UIHelper;
import org.example.librarymanagement.Class.User;

public class UserUpdateController {

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
    private TextField txtUserPassword;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnBack;

    @FXML
    private void initialize() {
        try {
            // Lấy danh sách tất cả người dùng
            List<User> users = User.getAllUsers();

            // Thêm tên người dùng vào ComboBox
            ObservableList<String> userNames = FXCollections.observableArrayList();
            for (User user : users) {
                userNames.add(user.getFullName());
            }

            cbUserName.setItems(userNames);
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(AlertType.ERROR, "Đã xảy ra lỗi khi tải danh sách người dùng.");
        }
    }

    @FXML
    private void actionUserUpdate(MouseEvent event) {
        String selectedUserName = cbUserName.getValue();
        System.out.println("Debug: Tên người dùng được chọn để cập nhật: " + selectedUserName); // DEBUG

        if (selectedUserName != null) {
            try {
                User user = new User();
                user.setFullName(selectedUserName);
                User foundUser = user.read();
                System.out.println("Debug: Người dùng tìm thấy: " + (foundUser != null ? foundUser.getFullName() : "Không tìm thấy")); // DEBUG

                if (foundUser != null) {
                    String name = txtUserName.getText();
                    String email = txtUserEmail.getText();
                    String phone = txtUserPhone.getText();
                    String address = txtUserAddress.getText();
                    String password = txtUserPassword.getText();

                    System.out.println("Debug: Thông tin người dùng trước khi cập nhật: " +
                            "Name=" + foundUser.getFullName() + ", Email=" + foundUser.getEmail() +
                            ", Phone=" + foundUser.getPhone() + ", Address=" + foundUser.getAddress() +
                            ", Password=" + foundUser.getPassword()); // DEBUG

                    foundUser.setFullName(name);
                    foundUser.setEmail(email);
                    foundUser.setPhone(phone);
                    foundUser.setAddress(address);
                    foundUser.setPassword(password);

                    foundUser.update();
                    System.out.println("Debug: Cập nhật người dùng thành công."); // DEBUG
                    UIHelper.showAlert(AlertType.INFORMATION, "Cập nhật người dùng thành công!");
                } else {
                    UIHelper.showAlert(AlertType.ERROR, "Người dùng không được tìm thấy.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                UIHelper.showAlert(AlertType.ERROR, "Có lỗi xảy ra khi cập nhật người dùng.");
            }
        } else {
            UIHelper.showAlert(AlertType.ERROR, "Vui lòng chọn một người dùng để cập nhật.");
        }
    }

    @FXML
    private void writeInfos() {
        try {
            String selectedName = cbUserName.getValue();
            if (selectedName == null || selectedName.isEmpty()) {
                System.out.println("No user name selected.");
                return;
            }

            // Lấy thông tin người dùng từ CSDL
            User user = new User();
            user.setFullName(selectedName);
            User newUser = user.read();
            // Nếu tìm thấy, hiển thị thông tin
            if (newUser != null) {
                txtUserName.setText(selectedName);
                txtUserEmail.setText(newUser.getEmail());
                txtUserPhone.setText(newUser.getPhone());
                txtUserAddress.setText(newUser.getAddress());
                txtUserPassword.setText(newUser.getPassword());
            } else {
                System.out.println("User not found.");
                txtUserName.clear();
                txtUserEmail.clear();
                txtUserPhone.clear();
                txtUserAddress.clear();
                txtUserPassword.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.openWindowAndClose(event, "admin", "Quay lại menu chính");
    }
}
