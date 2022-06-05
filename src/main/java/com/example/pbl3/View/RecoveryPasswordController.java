package com.example.pbl3;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.invoke.StringConcatFactory;
import java.util.ResourceBundle;

import static com.sun.tools.javac.jvm.ByteCodes.invokedynamic;

public class RecoveryPasswordController implements Initializable {
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label announceLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private Button sendResetCodeButton;
    @FXML
    private TextField userGmailTextField;
    @FXML
    private TextField codeTextField;
    @FXML
    private Hyperlink backToLoginHyperLink;
    @FXML
    private Hyperlink backToHome;
    int value;
    String gmail;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private OpenUI openUI = new OpenUI();

    public void backToHomeOnAction() {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("MyAccountUI.fxml");
    }
    public RecoveryPasswordController() {
    }

    @FXML
    void sendCodeOnAction(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM account WHERE gmail = '" + this.userGmailTextField.getText() + "'";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            boolean last_account = true;

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    this.SendMail(this.userGmailTextField.getText());
                    this.announceLabel.setText("Sent!");
                    last_account = false;
                    break;
                }
            }

            if (last_account) {
                this.announceLabel.setText("Gmail not found!");
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            var8.getCause();
        }
    }

    @FXML
    void confirmOnAcTion(ActionEvent event) {
        String s = String.valueOf(this.value);
        if (this.value == Integer.parseInt(this.codeTextField.getText())) {
            Stage stage = (Stage)this.confirmButton.getScene().getWindow();
            stage.close();
            this.createForgetPasswordSecondStage(event);
        } else {
            this.announceLabel.setText("Password doesn't match!");
        }
    }

    @FXML
    void backToLoginOnAction(ActionEvent event) {
        Stage stage = (Stage)this.backToLoginHyperLink.getScene().getWindow();
        stage.close();

        try {
            Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("LoginUI.fxml"));
            Stage forgetpassword = new Stage();
            forgetpassword.setScene(new Scene(root));
            forgetpassword.show();
        } catch (Exception var5) {
            var5.printStackTrace();
            var5.getCause();
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
            message.addRecipient(RecipientType.TO, new InternetAddress(to));
            message.setSubject("Confirmation code");
            message.setText(String.valueOf(this.value));
            Transport.send(message);
        } catch (MessagingException var7) {
            var7.printStackTrace();
        }

    }

    public void createForgetPasswordSecondStage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("RecoverPasswordSecondUI.fxml"));
            this.root = (Parent)loader.load();
            RecoveryPasswordSecondController rpsc = (RecoveryPasswordSecondController)loader.getController();
            rpsc.gmail = this.userGmailTextField.getText();
            this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            this.scene = new Scene(this.root);
            this.stage.setScene(this.scene);
            this.stage.show();
        } catch (Exception var4) {
            var4.printStackTrace();
            var4.getCause();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(openUI.namecashier == null) {
            backToHome.setVisible(false);
            backToLoginHyperLink.setVisible(true);
        } else {
            backToHome.setVisible(true);
            backToLoginHyperLink.setVisible(false);
        }
    }
}
