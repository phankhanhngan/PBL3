package com.example.pbl3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Hyperlink recoveryPasswordHyperlink;
    @FXML
    private Button signInButton;
    @FXML
    private TextField usernameTextField;

    public LoginController() {
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)this.cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void signInButtonOnAction(ActionEvent event) {
        if (!this.usernameTextField.getText().isBlank() && !this.passwordField.getText().isBlank()) {
            this.validateLogin();
        } else {
            this.loginMessageLabel.setText("Please enter username and password");
        }

    }

    @FXML
    void recoveryPasswordOnAction(ActionEvent event) {
        Stage stage = (Stage)this.recoveryPasswordHyperlink.getScene().getWindow();
        stage.close();
        this.createForgetPasswordStage();
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String var10000 = this.usernameTextField.getText();
        String verifyLogin = "SELECT count(1) FROM account WHERE username = '" + var10000 + "' AND password = '" + this.passwordField.getText() + "'";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    Stage stage = (Stage)this.signInButton.getScene().getWindow();
                    stage.close();
                    this.createAccountManagementStage();
                } else {
                    this.loginMessageLabel.setText("Please try again");
                }
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            var7.getCause();
        }

    }

    public void createAccountManagementStage() {
        try {
            Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("AccountManagementUI.fxml"));
            Stage accountStage = new Stage();
            accountStage.initStyle(StageStyle.UNDECORATED);
            accountStage.setScene(new Scene(root));
            accountStage.show();
        } catch (Exception var3) {
            var3.printStackTrace();
            var3.getCause();
        }

    }

    public void createHomeStage() {
        try {
            Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("HomePageUI.fxml"));
            Stage homeStage = new Stage();
            homeStage.setScene(new Scene(root));
            homeStage.initStyle(StageStyle.UNDECORATED);
            homeStage.show();
        } catch (Exception var3) {
            var3.printStackTrace();
            var3.getCause();
        }

    }

    public void createForgetPasswordStage() {
        try {
            Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("RecoveryPasswordUI.fxml"));
            Stage forgetpassword = new Stage();
            forgetpassword.setScene(new Scene(root));
            forgetpassword.show();
        } catch (Exception var3) {
            var3.printStackTrace();
            var3.getCause();
        }

    }
}
