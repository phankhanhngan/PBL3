package com.example.pbl3;

import com.example.pbl3.DatabaseConnection;
import com.example.pbl3.OpenUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;

public class RecoveryPasswordSecondController {
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button saveNewPasswordButton;
    @FXML
    private Label announceLabel;
    @FXML
    private Hyperlink backToLoginHyperLink;
    public String gmail;

    public RecoveryPasswordSecondController() {
    }

    @FXML
    void saveNewPasswordOnAction(ActionEvent event) {
        if (!this.passwordField.getText().isBlank() && !this.confirmPasswordField.getText().isBlank()) {
            if (this.passwordField.getText().equals(this.confirmPasswordField.getText())) {
                this.updatePassword();
                Stage stage = (Stage)this.saveNewPasswordButton.getScene().getWindow();
                stage.close();
                this.backLoginStage();
            } else {
                this.announceLabel.setText("Password does not match");
            }
        } else {
            this.announceLabel.setText("Please enter password and confirmPassword!");
        }

    }

    @FXML
    void backToLoginOnAction(ActionEvent event) {
        this.announceLabel.setText("Khang");
        Stage stage = (Stage)this.backToLoginHyperLink.getScene().getWindow();
        stage.close();
        this.backLoginStage();
    }


    public void updatePassword() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String var10000 = this.passwordField.getText();
        String verifyLogin = "UPDATE account SET password = '" + var10000 + "' WHERE gmail = '" + this.gmail + "'";

        try {
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(verifyLogin);
        } catch (Exception var5) {
            var5.printStackTrace();
            var5.getCause();
        }

    }

    public void backLoginStage() {
        OpenUI openUI = new OpenUI();
        openUI.Open_UI("LoginUI.fxml");
    }
}
