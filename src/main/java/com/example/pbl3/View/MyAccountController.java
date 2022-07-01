package com.example.pbl3.View;

import com.example.pbl3.BLL.BLLAccounts;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.OpenUI;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MyAccountController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField addressTxt;

    @FXML
    private JFXRadioButton cashierRadioBtn;

    @FXML
    private JFXRadioButton managerRadioBtn;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private JFXButton resetButton;

    @FXML
    private JFXButton sendCodeButton;

    @FXML
    private JFXButton submitButton;

    @FXML
    private TextField code;

    @FXML
    private PasswordField newPass;

    @FXML
    private PasswordField newPassConfirm;

    @FXML
    private TextField gmailTxt;

    @FXML
    private TextField nameTxt;
    @FXML
    private MenuItem account;

    int value;
    OpenUI openUI = new OpenUI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        DisableField();
        FillInformation();

        resetButton.setOnAction(e -> resetField());

        sendCodeButton.setOnAction(e -> sendCode());

        submitButton.setOnAction(e -> {
            confirm();
            resetField();
        });
    }

    public void decentralization() {
        if (!BLLProject.typecashier) {
            account.setVisible(false);
        }
    }

    public void DisableField() {
        nameTxt.setEditable(false);
        gmailTxt.setEditable(false);
        phoneTxt.setEditable(false);
        usernameTxt.setEditable(false);
        addressTxt.setEditable(false);
        managerRadioBtn.setDisable(true);
        cashierRadioBtn.setDisable(true);
    }

    public void FillInformation() {
        nameTxt.setText(BLLProject.namecashier);
        gmailTxt.setText(BLLProject.gmail);
        phoneTxt.setText(BLLProject.phonecashier);
        usernameTxt.setText(BLLProject.username);
        addressTxt.setText(BLLProject.address);
        if (BLLProject.typecashier) managerRadioBtn.setSelected(true);
        else cashierRadioBtn.setSelected(true);
        nameLabel.setText(nameTxt.getText() + "!");
    }

    public void resetField() {
        newPass.setText("");
        newPassConfirm.setText("");
        code.setText("");
    }

    public void sendCode() {
        if (BLLAccounts.CheckMail(BLLProject.gmail)) {
            Random generator = new Random();
            this.value = generator.nextInt(9000) + 1000;
            BLLProject.SendMail(BLLProject.gmail, "Your confirmation code is: " + value +
                    "\nDo not share this to anyone.", "Your Confirmation Code");
            Notifications.create().text("Code sent to your gmail. Check it out!").title("Sent successful!")
                    .hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    public boolean inputEmpty() {
        return newPassConfirm.getText().isEmpty() || newPass.getText().isEmpty() || code.getText().isEmpty();
    }

    public void confirm() {
        if (!inputEmpty()) {
            if (this.value == Integer.parseInt(this.code.getText())) {
                if (newPass.getText().equals(newPassConfirm.getText())) {
                    updatePassword();
                } else {
                    Notifications.create().text("Your password doesn't match!").title("Oh snap!")
                            .hideAfter(Duration.seconds(5.0D)).show();
                }
            } else {
                Notifications.create().text("Incorrect confirmation code. Check it again!").title("Oh snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
            }
        } else {
            Notifications.create().text("Please fill in all fields!").title("Oh snap!")
                    .hideAfter(Duration.seconds(5.0D)).show();
        }

    }

    public void updatePassword() {
        String var10000 = this.newPass.getText();
        if (BLLAccounts.UpdatePasswordAccount(var10000)) {
            Notifications.create().text("Your password has been changed!").title("Great!")
                    .hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    @FXML
    public void productMenuItemOnAction() {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ProductUI.fxml");
    }

    @FXML
    public void logOutMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("LoginUI.fxml");
    }

    @FXML
    public void accountMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("AccountUI.fxml");
    }

    @FXML
    public void importMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ImportUI.fxml");
    }

    @FXML
    public void supplierMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("SupplierUI.fxml");
    }

    @FXML
    public void categoryMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CategoryUI.fxml");
    }

    @FXML
    public void customerMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CustomerUI.fxml");
    }

    @FXML
    public void orderMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CreateNewBillUI.fxml");
    }

    @FXML
    void billMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ViewBillUI.fxml");
    }

    @FXML
    public void homePageMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("HomePageUI.fxml");
    }

    @FXML
    public void statisticMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("StatisticsUI.fxml");
    }

    @FXML
    void myAccountMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("MyAccountUI.fxml");
    }
}
