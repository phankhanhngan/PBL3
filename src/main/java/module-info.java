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
    requires AnimateFX;
    requires java.desktop;


    opens com.example.pbl3 to javafx.fxml;
    exports com.example.pbl3;
    exports com.example.pbl3.DAL;
    opens com.example.pbl3.DAL to javafx.fxml;
    exports com.example.pbl3.DTO;
    opens com.example.pbl3.DTO to javafx.fxml;
    exports com.example.pbl3.View;
    opens com.example.pbl3.View to javafx.fxml;
}