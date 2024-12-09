package org.example.librarymanagement;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
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
     * Mở cửa sổ mới nhưng 0 đóng cửa sổ hiện tại.
     */
    public static void openWindow(String windowName, String windowTitle) {
        try {
            Parent root = FXMLLoader.load(UIHelper.class.getResource("/org/example/librarymanagement/" + windowName + ".fxml"));
            Stage newStage = new Stage();
            newStage.getIcons().add(new Image(UIHelper.class.getResource("/org/example/librarymanagement/image/logo.png").toExternalForm()));
            newStage.setTitle(windowTitle);
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Không thể tải cửa sổ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Chuyển đổi scene trong cùng một cửa sổ, không cần tạo mới stage.
     */
    public static void switchWindow(MouseEvent event, String windowName, String windowTitle) {
        try {
            Parent root = FXMLLoader.load(UIHelper.class.getResource("/org/example/librarymanagement/" + windowName + ".fxml"));

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setTitle(windowTitle);
            currentStage.setScene(new Scene(root));

            currentStage.getIcons().add(new Image(UIHelper.class.getResource("/org/example/librarymanagement/image/logo.png").toExternalForm()));

            if ("login".equals(windowName) || "register".equals(windowName)) {
                currentStage.setWidth(600);
                currentStage.setHeight(400);
            }

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            currentStage.setX((screenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((screenBounds.getHeight() - currentStage.getHeight()) / 2);

            currentStage.show();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Không thể tải cửa sổ: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
