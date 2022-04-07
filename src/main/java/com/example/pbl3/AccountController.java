package com.example.pbl3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

public class AccountController {
    @FXML
    private Button addButton;
    @FXML
    private Button signOutButton;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField gmailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private RadioButton managerRadioButton;
    @FXML
    private RadioButton cashierRadioButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;

    public AccountController() {
    }

    public boolean isInputFieldEmpty() {
        return this.passwordTextField.getText().trim().isEmpty() || this.firstnameTextField.getText().trim().isEmpty() || this.lastnameTextField.getText().trim().isEmpty() || this.usernameTextField.getText().trim().isEmpty() || this.gmailTextField.getText().trim().isEmpty() || this.phoneTextField.getText().trim().isEmpty() || this.addressTextField.getText().trim().isEmpty() || !this.managerRadioButton.isSelected() && !this.cashierRadioButton.isSelected();
    }

    public void addOnAction(ActionEvent event) throws Exception {
        if (!this.isInputFieldEmpty()) {
            this.addAccount();
        } else {
            Notifications.create().text("Please fill in all fields.").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
        }

    }

    private void addAccount() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        String firstname = this.firstnameTextField.getText();
        String lastname = this.lastnameTextField.getText();
        String username = this.usernameTextField.getText();
        String password = this.passwordTextField.getText();
        String gmail = this.gmailTextField.getText();
        String phone = this.phoneTextField.getText();
        String address = this.addressTextField.getText();
        int isManager = this.managerRadioButton.isSelected() ? 1 : 0;
        String insertFields = "insert into account(firstname, lastname, gmail, phone_number, username,password, address, type_customer) values ('";
        String insertValues = firstname + "','" + lastname + "','" + gmail + "','" + phone + "','" + username + "','" + password + "','" + address + "','" + isManager + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            Notifications.create().text("You have add account successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
        } catch (Exception var15) {
            Notifications.create().text("You have failed add account in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }

    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)this.closeButton.getScene().getWindow();
        stage.close();
    }

    public void minimizeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)this.closeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void tblCashierClick(MouseEvent mouseEvent) {
    }

    public void searchOnAction(ActionEvent event) {
    }

    public void cashierUpdateOnAction(ActionEvent event) {
    }

    public void cashierDeleteOnAction(ActionEvent event) {
    }

    public void setOnAction(ActionEvent event) {
    }

    @FXML
    public void signOutButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)this.signOutButton.getScene().getWindow();
        stage.close();

        try {
            Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("LoginUI.fxml"));
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (Exception var5) {
            var5.printStackTrace();
            var5.getCause();
        }

    }
}

