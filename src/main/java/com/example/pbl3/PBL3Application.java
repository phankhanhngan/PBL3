package com.example.pbl3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PBL3Application extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("LoginUI.fxml"));
        Scene scene = new Scene(root, 684.0, 514.0);
        stage.setScene(scene);
        stage.setTitle("Electronic Shop");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}