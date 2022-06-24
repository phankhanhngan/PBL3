package com.example.pbl3.View;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.example.pbl3.BLL.BLLAccounts;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.DTO.Account;
import com.example.pbl3.OpenUI;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

public class AccountController implements Initializable {
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
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
    private TextField searchTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<Account> AccountTableView;
    @FXML
    private TableColumn<Account, String> Col_FName;
    @FXML
    private TableColumn<Account, String> Col_LName;
    @FXML
    private TableColumn<Account, String> Col_Username;
    @FXML
    private TableColumn<Account, PasswordField> Col_Password;
    @FXML
    private TableColumn<Account, String> Col_Gmail;
    @FXML
    private TableColumn<Account, String> Col_Phone;
    @FXML
    private TableColumn<Account, String> Col_Address;
    @FXML
    private TableColumn<Account, Boolean> Col_TypeOfUser;
    @FXML
    private MenuItem account;

    private ObservableList<Account> accountsList;
    OpenUI openUI = new OpenUI();


    public AccountController() {
    }

    public boolean isInputFieldEmpty() {
        return this.firstnameTextField.getText().trim().isEmpty() || this.lastnameTextField.getText().trim().isEmpty() || this.gmailTextField.getText().trim().isEmpty() || this.phoneTextField.getText().trim().isEmpty() || this.addressTextField.getText().trim().isEmpty() || !this.managerRadioButton.isSelected() && !this.cashierRadioButton.isSelected();
    }

    public void addOnAction(ActionEvent event) throws Exception {
        if (!this.isInputFieldEmpty()) {
            this.addAccount();
            UpdateListAccount("");
        } else {
            Notifications.create().text("Please fill in all fields.").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
        }

    }

    private void addAccount() {
        if(!BLLProject.CheckPhone(phoneTextField.getText()))
        {
            Notifications.create().text("Invalid phone number. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            return;
        }
        if(!BLLProject.CheckMail(gmailTextField.getText()))
        {
            Notifications.create().text("Invalid gmail. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            return;
        }
        String typeOfUser = this.managerRadioButton.isSelected() ? "Manager" : "Cashier";
        Random generator = new Random();
        int value = generator.nextInt(900000) + 100000;
        Account a = new Account(firstnameTextField.getText(),lastnameTextField.getText(),gmailTextField.getText(),
                phoneTextField.getText(),phoneTextField.getText(),value+"",addressTextField.getText(),typeOfUser);
        if(BLLAccounts.AddAccount(a))
        {
            Notifications.create().text("You have add account successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            BLLProject.SendMail(gmailTextField.getText(),"This is your account information. You can use this to log into ORIE.\nUsername: " + phoneTextField.getText() + "\nPassword: " + value, "Your ORIE Account Information");
            resetInputField();
            UpdateListAccount("");
        }
        else
        {
            Notifications.create().text("You have failed add account in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        UpdateListAccount("");
        AccountTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showButtonOnAction();
            }
        });
        searchButton.setOnAction(e -> {
            UpdateListAccount(searchTextField.getText());
        });
    }

    public void resetInputField() {
        firstnameTextField.setText("");
        lastnameTextField.setText("");
        gmailTextField.setText("");
        phoneTextField.setText("");
        addressTextField.setText("");
        managerRadioButton.setSelected(false);
        cashierRadioButton.setSelected(false);
        AccountTableView.getSelectionModel().clearSelection();
        gmailTextField.setEditable(true);
        gmailTextField.setStyle("-fx-background-color: #FFFFFF");
        addButton.setDisable(false);
    }

    private class JFXPasswordCellValueFactory implements Callback<TableColumn.CellDataFeatures<Account, PasswordField>, ObservableValue<PasswordField>> {

        @Override
        public ObservableValue<PasswordField> call(TableColumn.CellDataFeatures<Account, PasswordField> param) {
            Account item = param.getValue();

            PasswordField password = new PasswordField();
            password.setEditable(false);
            password.setPrefWidth(Col_Password.getWidth() / 0.5);
            password.setText(item.getPassword());
            return new SimpleObjectProperty<>(password);
        }
    }

    public void showButtonOnAction() {
        if (AccountTableView.getSelectionModel().getSelectedItem().getUsername() != "") {
            firstnameTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().getFirstName());
            lastnameTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().getLastName());
            gmailTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().getGmail());
            addressTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().getAddress());
            phoneTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().getPhone());
            managerRadioButton.setSelected(AccountTableView.getSelectionModel().getSelectedItem().isTypeOfUser().equals("Manager") ? true : false);
            cashierRadioButton.setSelected(AccountTableView.getSelectionModel().getSelectedItem().isTypeOfUser().equals("Cashier") ? true : false);
            addButton.setDisable(true);
            gmailTextField.setEditable(false);
            gmailTextField.setStyle("-fx-background-color:  rgba(255,255,255,0.4)");
        } else {
            Notifications.create().text("Please select an account to show")
                    .title("Notification").show();
        }
    }

    @FXML
    private void updateButtonOnAction() {
        if(!BLLProject.CheckPhone(phoneTextField.getText()))
        {
            Notifications.create().text("Invalid phone number. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            return;
        }
        if(!BLLProject.CheckMail(gmailTextField.getText()))
        {
            Notifications.create().text("Invalid gmail. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            return;
        }
        if (!AccountTableView.getSelectionModel().isEmpty()) {
            String type = managerRadioButton.isSelected() ? "Manager" : "Cashier";
            Account account = AccountTableView.getSelectionModel().getSelectedItem();
            Account account1 = new Account(firstnameTextField.getText(),lastnameTextField.getText(),gmailTextField.getText(),
                    phoneTextField.getText(),account.getUsername(),account.getPassword(),addressTextField.getText(),type);
            System.out.println(account.getPassword());
            if(BLLAccounts.UpdateAccount(account1))
            {
                Notifications.create().text("You have update account successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
                resetInputField();
                UpdateListAccount("");
            }
            else
            {
                Notifications.create().text("You have failed update account in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        } else {
            Notifications.create().text("Please select an account to update")
                    .title("Notification").show();
        }
    }

    @FXML
    private void deleteButtonOnAction() {
        if(AccountTableView.getSelectionModel().isEmpty())
        {
            System.out.println(12);
            Notifications.create().text("Please select an account to update")
                    .title("Notification").show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this account?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                if(BLLAccounts.DeleteAccount(AccountTableView.getSelectionModel().getSelectedItem().getUsername()))
                {
                    Notifications.create().text("successfully .").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
                    UpdateListAccount("");
                    resetInputField();
                }
                else
                {
                    Notifications.create().text("error!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
                }
            }
        }
    }
    @FXML
    void pressEnterOnAction(KeyEvent event) {
        if(event.getCode().toString() == "ENTER")
        {
            UpdateListAccount(searchTextField.getText());

        }
    }

    public void UpdateListAccount(String txt)
    {
        accountsList = FXCollections.observableArrayList(Search(txt));
        Col_FName.setCellValueFactory(new PropertyValueFactory<Account, String>("firstName"));
        Col_LName.setCellValueFactory(new PropertyValueFactory<Account, String>("lastName"));
        Col_Username.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
        Col_Password.setCellValueFactory(new JFXPasswordCellValueFactory());
        Col_Gmail.setCellValueFactory(new PropertyValueFactory<Account, String>("gmail"));
        Col_Phone.setCellValueFactory(new PropertyValueFactory<Account, String>("phone"));
        Col_Address.setCellValueFactory(new PropertyValueFactory<Account, String>("address"));
        Col_TypeOfUser.setCellValueFactory(new PropertyValueFactory<>("typeOfUser"));
        AccountTableView.setItems(accountsList);
    }
    public List<Account> Search(String txt)
    {
        List<Account> accounts = BLLAccounts.SearchAccount(txt);
        return accounts;
    }

    @FXML
    private void resetButtonOnAction() {
        resetInputField();
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
        if(BLLProject.typecashier == false)
        {
            account.setVisible(false);
        }
    }
}






