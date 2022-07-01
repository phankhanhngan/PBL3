package com.example.pbl3.View;


import com.example.pbl3.BLL.BLLAccounts;
import com.example.pbl3.OpenUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

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
    void saveNewPasswordOnAction() {
        if (!this.passwordField.getText().isBlank() && !this.confirmPasswordField.getText().isBlank()) {
            if (this.passwordField.getText().equals(this.confirmPasswordField.getText())) {
                this.updatePassword();
                Stage stage = (Stage) this.saveNewPasswordButton.getScene().getWindow();
                stage.close();
                this.backLoginStage();
            } else {
                this.announceLabel.setText("Password does not match");
            }
        } else {
            this.announceLabel.setText("Please enter password and confirm password!");
        }
    }

    @FXML
    void backToLoginOnAction() {
        this.announceLabel.setText("");
        Stage stage = (Stage) this.backToLoginHyperLink.getScene().getWindow();
        stage.close();
        this.backLoginStage();
    }

    public void updatePassword() {
        BLLAccounts.UpdatePasswordAccount(passwordField.getText());
    }

    public void backLoginStage() {
        OpenUI BLLProject = new OpenUI();
        BLLProject.Open_UI("LoginUI.fxml");
    }
}

