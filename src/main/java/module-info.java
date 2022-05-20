module com.example.pbl3 {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.base;
    requires org.controlsfx.controls;
    requires java.mail;
    requires jdk.compiler;
    requires com.jfoenix;
    requires kernel;
    requires layout;


    opens com.example.pbl3 to javafx.fxml;
    exports com.example.pbl3;
}