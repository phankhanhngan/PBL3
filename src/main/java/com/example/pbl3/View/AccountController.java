package com.example.pbl3;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.pbl3.DTO.Account;
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
    private MenuItem product;
    @FXML
    private MenuItem homepage;
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem importPrd;
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

    private void addAccount() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        String firstname = this.firstnameTextField.getText();
        String lastname = this.lastnameTextField.getText();
        String username = this.phoneTextField.getText();
        String password = "123456";
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
            resetInputField();
            UpdateListAccount("");
        } catch (Exception var15) {
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
    }


    public void resetInputField() {
        firstnameTextField.setText("");
        lastnameTextField.setText("");
        gmailTextField.setText("");
        phoneTextField.setText("");
        addressTextField.setText("");
        managerRadioButton.setSelected(false);
        cashierRadioButton.setSelected(false);
    }
    private class JFXPasswordCellValueFactory implements Callback<TableColumn.CellDataFeatures<Account, PasswordField>, ObservableValue<PasswordField>> {

        @Override
        public ObservableValue<PasswordField> call(TableColumn.CellDataFeatures<Account, PasswordField> param) {
            Account item = param.getValue();

            PasswordField password = new PasswordField();
            password.setEditable(false);
            password.setPrefWidth(Col_Password.getWidth() / 0.5);
            password.setText(item.getPassword());
            password.getStyleClass().addAll("password-field-cell", "table-row-cell");

            return new SimpleObjectProperty<>(password);
        }
    }


    public void showButtonOnAction() {
        if (AccountTableView.getSelectionModel().getSelectedItem().username != "") {
            firstnameTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().firstName);
            lastnameTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().lastName);
            gmailTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().gmail);
            addressTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().address);
            phoneTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().phone);
            managerRadioButton.setSelected(AccountTableView.getSelectionModel().getSelectedItem().typeOfUser == "Manager" ? true : false);
            cashierRadioButton.setSelected(AccountTableView.getSelectionModel().getSelectedItem().typeOfUser == "Cashier" ? true : false);
            addButton.setDisable(true);
        } else {
            Notifications.create().text("Please select an account to show")
                    .title("Notification");
        }
    }

    @FXML
    private void updateButtonOnAction() {
        if (AccountTableView.getSelectionModel().getSelectedItem() != null) {
            Account account = AccountTableView.getSelectionModel().getSelectedItem();
            UpdateRow(firstnameTextField.getText(), lastnameTextField.getText(), account.username, account.password, gmailTextField.getText(), addressTextField.getText(), phoneTextField.getText(), managerRadioButton.isSelected());

        } else {
            Notifications.create().text("Please select an account to update")
                    .title("Notification");
        }
    }

    public String updateQuery(String firstname, String lastname, String username, String password, String gmail, String address, String phone, boolean isManager) {
        int type;
        if (isManager) type = 1;
        else type = 0;
        String query = "update account set firstname = '" + firstname + "', lastname = '" + lastname + "', password = '" + password + "', gmail = '" + gmail
                + "', address = '" + address + "', phone_number = '" + phone + "', type_customer = '" + type + "' where username = '" + username + "'";
        return query;
    }

    public void UpdateRow(String firstname, String lastname, String username, String password, String gmail, String address, String phone, boolean isManager) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update the account?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();
            String updateAccount = updateQuery(firstname,lastname,username,password,gmail,address,phone,isManager);

            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(updateAccount);
                Notifications.create().text("You have update account successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
                resetInputField();
                UpdateListAccount("");
            } catch (Exception var15) {
                Notifications.create().text("You have failed update account in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        }
    }
    @FXML
    private void deleteButtonOnAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the account?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            DatabaseConnection connection = new DatabaseConnection();
            Connection connectDB = connection.getConnection();
            Account selected = AccountTableView.getSelectionModel().getSelectedItem();
            String query = "DELETE FROM account WHERE username  = '" + selected.getUsername() + "'";
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(query);
                Notifications.create().text("successfully .").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
                UpdateListAccount("");
                resetInputField();
            } catch (Exception var15) {
                var15.printStackTrace();
                Notifications.create().text("error!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
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
        List<Account> accounts = new ArrayList<Account>();
        String sql = "SELECT * FROM account WHERE lastname = " + "'" + txt + "' OR firstname LIKE '%" + txt
                + "%' OR username LIKE '%" + txt + "%' OR gmail LIKE '%" + txt + "%' OR phone_number LIKE '%"
                + txt +"%' OR address LIKE '%" + txt + "%'";
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        try {
            PreparedStatement pst = connectDb.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String firstname = rs.getString(1);
                String lastname = rs.getString(2);
                String gmail = rs.getString(3);
                String phone = rs.getString(4);
                String username = rs.getString(5);
                String password = rs.getString(6);
                String address = rs.getString(7);
                boolean type_admin = rs.getBoolean(8);
                String type;
                if(type_admin) type = "Manager";
                else type = "Cashier";
                accounts.add(new Account(firstname,lastname,gmail,phone,username,password,address,type));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connectDb.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
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
        if(openUI.typecashier == false)
        {
            account.setVisible(false);
        }
    }
}






