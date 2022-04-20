package com.example.pbl3;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpenUI {
    public static String gmail;
    public void Open_UI(String namefile, String gmail)
    {
        try {
            this.gmail = gmail;
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
