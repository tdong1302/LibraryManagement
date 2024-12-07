package org.example.librarymanagement;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UIHelper {

    public static void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type == AlertType.ERROR ? "Lỗi" : "Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Mở cửa sổ mới và đóng cửa sổ hiện tại , dùng event chuột.
     */
    public static void openWindowAndClose(MouseEvent event, String windowName, String windowTitle) {
        try {
            Parent root = FXMLLoader.load(UIHelper.class.getResource("/org/example/librarymanagement/" + windowName + ".fxml"));
            Stage newStage = new Stage();
            newStage.setTitle(windowTitle);
            newStage.setScene(new Scene(root));

            if ("login".equals(windowName) || "register".equals(windowName)) {
                newStage.setWidth(600);
                newStage.setHeight(400);
            }

            newStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Không thể tải cửa sổ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mở cửa sổ mới nhưng 0 đóng cửa sổ hiện tại.
     */
    public static void openWindow(String windowName, String windowTitle) {
        try {
            Parent root = FXMLLoader.load(UIHelper.class.getResource("/org/example/librarymanagement/" + windowName + ".fxml"));
            Stage newStage = new Stage();
            newStage.setTitle(windowTitle);
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Không thể tải cửa sổ: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
