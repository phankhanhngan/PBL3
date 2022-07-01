package com.example.pbl3.View;

import java.net.URL;
import java.util.Random;

import com.example.pbl3.BLL.BLLAccounts;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.OpenUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;


public class RecoveryPasswordController implements Initializable {
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label announceLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private TextField userGmailTextField;
    @FXML
    private TextField codeTextField;
    @FXML
    private Hyperlink backToLoginHyperLink;
    @FXML
    private Hyperlink backToHome;
    int value;

    private OpenUI openUI = new OpenUI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (BLLProject.namecashier == null) {
            backToHome.setVisible(false);
            backToLoginHyperLink.setVisible(true);
        } else {
            backToHome.setVisible(true);
            backToLoginHyperLink.setVisible(false);
        }
    }

    public void backToHomeOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("LoginUI.fxml");
    }

    @FXML
    void sendCodeOnAction() {
        if (BLLAccounts.CheckMail(userGmailTextField.getText())) {
            Random generator = new Random();
            this.value = generator.nextInt(9000) + 1000;
            BLLProject.SendMail(this.userGmailTextField.getText(), "Your confirmation code is: " + value +
                    "\nDo not share this to anyone.", "Your Confirmation Code");
            this.announceLabel.setText("Sent");
        } else {
            this.announceLabel.setText("Gmail not found!");
        }
    }

    @FXML
    void confirmOnAcTion() {
        try {
            if (this.value == Integer.parseInt(this.codeTextField.getText())) {
                Stage stage = (Stage) this.confirmButton.getScene().getWindow();
                stage.close();
                openUI.Open_UI("RecoveryPasswordSecondUI.fxml");
            } else {
                this.announceLabel.setText("Incorrect code");
            }
        } catch (Exception e) {
            this.announceLabel.setText("Incorrect code");
        }
    }

    @FXML
    void backToLoginOnAction() {
        Stage stage = (Stage) this.backToLoginHyperLink.getScene().getWindow();
        stage.close();
        openUI.Open_UI("LoginUI.fxml");
    }
}
