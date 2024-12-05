module org.example.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    requires org.apache.httpcomponents.client5.httpclient5.fluent;
    requires com.google.gson;
    requires com.google.api.client;
    requires google.api.services.books.v1.rev114;
    requires google.api.client;
    requires google.http.client.jackson2;
    requires com.fasterxml.jackson.databind;

    opens org.example.librarymanagement to javafx.fxml;
    exports org.example.librarymanagement;
    exports org.example.librarymanagement.UserController;
    opens org.example.librarymanagement.UserController to javafx.fxml;
    exports org.example.librarymanagement.BookController;
    opens org.example.librarymanagement.BookController to javafx.fxml;
    exports org.example.librarymanagement.Class;
    opens org.example.librarymanagement.Class to javafx.fxml;
    //opens org.example.librarymanagement.api to javafx.fxml;
}