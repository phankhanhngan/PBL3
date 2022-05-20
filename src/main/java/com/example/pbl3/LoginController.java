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

    OpenUI openUI = new OpenUI();

    public LoginController() {
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) this.cancelButton.getScene().getWindow();
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
        Stage stage = (Stage) this.recoveryPasswordHyperlink.getScene().getWindow();
        stage.close();
        openUI.Open_UI("RecoveryPasswordUI.fxml");
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String var10000 = this.usernameTextField.getText();
        String verifyLogin = "SELECT count(1),concat(firstname,' ',lastname) as nameCashier,phone_number,type_customer,username,address,gmail FROM account WHERE username = '" + var10000 + "' AND password = '" + this.passwordField.getText() + "'";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    Stage stage = (Stage)this.signInButton.getScene().getWindow();
                    stage.close();
                    openUI.setGmail(queryResult.getString(7));
                    openUI.setAddress(queryResult.getString(6));
                    openUI.setUsername(queryResult.getString(5));
                    openUI.setNameCashier(queryResult.getString(2));
                    openUI.setPhoneCashier(queryResult.getString(3));
                    openUI.setTypecashier(queryResult.getBoolean(4));
//                    openUI.Open_UI("CreateNewBillUI.fxml","");
                    openUI.Open_UI("HomePageUI.fxml");
                } else {
                    this.loginMessageLabel.setText("Please try again");
                }
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            var7.getCause();
        }
    }
}
