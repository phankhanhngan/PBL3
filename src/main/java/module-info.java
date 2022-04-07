module com.example.pbl3 {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.base;
    requires org.controlsfx.controls;
    requires java.mail;
    requires jdk.compiler;


    opens com.example.pbl3 to javafx.fxml;
    exports com.example.pbl3;
}