package com.example.pbl3.View;


import com.example.pbl3.BLL.BLLAccounts;
import com.example.pbl3.OpenUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        if (BLLAccounts.CheckAccount(usernameTextField.getText(), passwordField.getText())) {
            Stage stage = (Stage)this.usernameTextField.getScene().getWindow();
            stage.close();
            openUI.Open_UI("HomePageUI.fxml");
        } else {
            this.loginMessageLabel.setText("Please try again");
        }
    }

}
