package org.example.librarymanagement.userController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.librarymanagement.UIHelper;
import org.example.librarymanagement.entity.User;

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

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                UIHelper.showAlert(AlertType.ERROR, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            if (!email.endsWith("@gmail.com")) {
                UIHelper.showAlert(AlertType.ERROR, "Email không hợp lệ!");
                return;
            }

            if (password.length() < 8) {
                UIHelper.showAlert(AlertType.ERROR, "Mật khẩu phải chứa ít nhất 8 ký tự!");
                return;
            }

            if (!password.matches(".*[^a-zA-Z0-9].*")) {
                UIHelper.showAlert(AlertType.ERROR, "Mật khẩu phải chứa ký tự đặc biệt!");
                return;
            }

            if (phone.length() != 10 || !phone.matches("\\d+")) {
                UIHelper.showAlert(AlertType.ERROR, "Số điện thoại không hợp lệ!");
                return;
            }
            User user = new User(email, password, fullName, address, phone);
            if (user.isEmailUserExist()) {
                UIHelper.showAlert(AlertType.ERROR, "Email này đã tồn tại");
                return;
            }
            user.create();
            UIHelper.showAlert(AlertType.INFORMATION, "Thêm user thành công");
        } catch (Exception e) {
            UIHelper.showAlert(AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    private void actionBack(MouseEvent event) {
        UIHelper.switchWindow(event, "admin_user", "Quay lại menu chính");
    }

}
