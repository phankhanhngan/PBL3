package com.example.pbl3;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OpenUI {
    public void Open_UI(String namefile)
    {
        try {
            Parent root = (Parent) FXMLLoader.load(this.getClass().getResource(namefile));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception var3) {
            var3.printStackTrace();
            var3.getCause();
        }
    }
}