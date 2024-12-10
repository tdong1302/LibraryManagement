package org.example.librarymanagement.UserController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.Class.User;
import org.example.librarymanagement.LoginController;
import org.example.librarymanagement.UIHelper;

public class CurrentUpdateController {

    private final User currentUser = LoginController.getCurrentUser();
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
            if (currentUser != null) {
                User userFromDB = currentUser.read();
                if (userFromDB != null) {
                    txtUserName.setText(userFromDB.getFullName());
                    txtUserEmail.setText(userFromDB.getEmail());
                    txtUserPhone.setText(userFromDB.getPhone());
                    txtUserAddress.setText(userFromDB.getAddress());
                    txtUserPassword.setText(userFromDB.getPassword());
                } else {
                    UIHelper.showAlert(AlertType.ERROR, "Không tìm thấy người dùng trong cơ sở dữ liệu.");
                }
            } else {
                UIHelper.showAlert(AlertType.ERROR, "Không tìm thấy người dùng hiện tại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(AlertType.ERROR, "Đã xảy ra lỗi khi tải thông tin người dùng.");
        }
    }

    @FXML
    private void actionUserUpdate(MouseEvent event) {
        try {
            if (currentUser != null) {
                String name = txtUserName.getText();
                String email = txtUserEmail.getText();
                String phone = txtUserPhone.getText();
                String address = txtUserAddress.getText();
                String password = txtUserPassword.getText();

                currentUser.setFullName(name);
                currentUser.setEmail(email);
                currentUser.setPhone(phone);
                currentUser.setAddress(address);
                currentUser.setPassword(password);

                currentUser.update();

                UIHelper.showAlert(AlertType.INFORMATION, "Cập nhật thông tin thành công!");
                UIHelper.switchWindow(event, "infoUser", "Thông tin người dùng");
            } else {
                UIHelper.showAlert(AlertType.ERROR, "Người dùng không hợp lệ.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.showAlert(AlertType.ERROR, "Có lỗi xảy ra khi cập nhật thông tin.");
        }
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "infoUser", "Quay lại thông tin người dùng");
    }
}
