package com.example.pbl3;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.PreencodedMimeBodyPart;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
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
    private OpenUI openUI = new OpenUI();



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
        nameTxt.setText(openUI.namecashier);
        gmailTxt.setText(openUI.gmail);
        phoneTxt.setText(openUI.phonecashier);
        usernameTxt.setText(openUI.username);
        addressTxt.setText(openUI.address);
        if(openUI.typecashier) managerRadioBtn.setSelected(true);
        else cashierRadioBtn.setSelected(true);
        nameLabel.setText(nameTxt.getText() + "!");
    }

    public void changePassword() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("RecoveryPasswordUI.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        DisableField();
        FillInformation();

        resetButton.setOnAction(e -> {
            resetField();
        });

        sendCodeButton.setOnAction(e -> {
            sendCode();
        });

        submitButton.setOnAction(e -> {
            confirm();
        });
    }

    public void resetField() {
        newPass.setText("");
        newPassConfirm.setText("");
        code.setText("");
    }

    public void sendCode() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM account WHERE gmail = '" + openUI.gmail + "'";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    this.SendMail(this.openUI.gmail);
                    Notifications.create().text("Code sent to your gmail. Check it out!").title("Sent successful!").hideAfter(Duration.seconds(5.0D)).show();
                    break;
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            var8.getCause();
        }
    }

    public void SendMail(String to) {
        Random generator = new Random();
        this.value = generator.nextInt(9000) + 1000;
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vokhuong1403@gmail.com", "blackdiablo1403");
            }
        });
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("vokhuong1403@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Confirmation code");
            message.setText(String.valueOf(this.value));
            Transport.send(message);
        } catch (MessagingException var7) {
            var7.printStackTrace();
        }

    }

    public void confirm() {
        String s = String.valueOf(this.value);
        if (this.value == Integer.parseInt(this.code.getText())) {
            if (newPass.getText().equals(newPassConfirm.getText())) {
                updatePassword();
            } else {
                Notifications.create().text("Your password doesn't match!").title("Oh snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        } else {
            Notifications.create().text("Incorrect confirmation code. Check it again!").title("Oh snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    public void updatePassword() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String var10000 = this.newPass.getText();
        String verifyLogin = "UPDATE account SET password = '" + var10000 + "' WHERE gmail = '" + openUI.gmail + "'";

        try {
            Statement statement = connectDb.createStatement();
            statement.executeUpdate(verifyLogin);
            Notifications.create().text("Your password has been changed!").title("Great!").hideAfter(Duration.seconds(5.0D)).show();
        } catch (Exception var5) {
            var5.printStackTrace();
            var5.getCause();
        }
    }

    @FXML
    public void productMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ProductManagementUI.fxml");
    }

    @FXML
    public void logOutMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("LoginUI.fxml");
    }

    @FXML
    public void accountMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("AccountManagementUI.fxml");
    }

    @FXML
    public void importMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ImportUI.fxml");
    }

    @FXML
    public void supplierMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("SupplierManagementUI.fxml");
    }

    @FXML
    public void categoryMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CategoryManagementUI.fxml");
    }

    @FXML
    public void customerMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CustomerUI.fxml");
    }

    @FXML
    public void orderMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CreateNewBillUI.fxml");
    }

    @FXML
    void billMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ViewBillUI.fxml");
    }

    @FXML
    public void homePageMenuItemOnAction() {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("HomePageUI.fxml");
    }

    @FXML
    public void statisticMenuItemOnAction() {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("StatisticsUI.fxml");
    }

    @FXML
    void myAccountMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("MyAccountUI.fxml");
    }
    public void decentralization()
    {
        if(openUI.typecashier == false)
        {
            account.setVisible(false);
        }
    }
}
