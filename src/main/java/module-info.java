module org.example.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.google.gson;
    requires com.google.api.client;
    requires google.http.client.jackson2;
    requires google.api.client;
    requires google.api.services.books.v1.rev114;

    opens org.example.librarymanagement.game to javafx.fxml;
    exports org.example.librarymanagement.game;
    opens org.example.librarymanagement to javafx.fxml;
    exports org.example.librarymanagement;
    exports org.example.librarymanagement.userController;
    opens org.example.librarymanagement.userController to javafx.fxml;
    exports org.example.librarymanagement.bookController;
    opens org.example.librarymanagement.bookController to javafx.fxml;
    exports org.example.librarymanagement.entity;
    opens org.example.librarymanagement.entity to javafx.fxml;
    exports org.example.librarymanagement.mainController;
    opens org.example.librarymanagement.mainController to javafx.fxml;
}