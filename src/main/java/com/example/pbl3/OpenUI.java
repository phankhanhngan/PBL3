package com.example.pbl3;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpenUI {
    public static int IDBill = 0;
    public static String gmail;
    public static String phonecashier;
    public static String namecashier;
    public static boolean typecashier;
    public static String username;
    public static String address;

    public static void setAddress(String address) {
        OpenUI.address = address;
    }

    public static void setUsername(String username) {
        OpenUI.username = username;
    }

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

    public void setPhoneCashier(String phoneCashier) {
        phonecashier = phoneCashier;
    }

    public void setNameCashier(String nameCashier) {
        namecashier = nameCashier;
    }

    public  void setIDBill(int idbill) {
        IDBill = idbill;
    }

    public void setGmail(String Gmail) {
        gmail = Gmail;
    }
    public void setTypecashier (boolean type)
    {
        typecashier = type;
    }
}