//package com.example.pbl3;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.TextField;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//import org.controlsfx.control.Notifications;
//import org.controlsfx.control.action.Action;
//
//public class AccountController {
//    @FXML
//    private Button addButton;
//    @FXML
//    private Button signOutButton;
//    @FXML
//    private TextField firstnameTextField;
//    @FXML
//    private TextField lastnameTextField;
//    @FXML
//    private TextField usernameTextField;
//    @FXML
//    private PasswordField passwordTextField;
//    @FXML
//    private TextField gmailTextField;
//    @FXML
//    private TextField phoneTextField;
//    @FXML
//    private TextField addressTextField;
//    @FXML
//    private RadioButton managerRadioButton;
//    @FXML
//    private RadioButton cashierRadioButton;
//    @FXML
//    private Button closeButton;
//    @FXML
//    private Button minimizeButton;
//
//    public AccountController() {
//    }
//
//    public boolean isInputFieldEmpty() {
//        return this.passwordTextField.getText().trim().isEmpty() || this.firstnameTextField.getText().trim().isEmpty() || this.lastnameTextField.getText().trim().isEmpty() || this.usernameTextField.getText().trim().isEmpty() || this.gmailTextField.getText().trim().isEmpty() || this.phoneTextField.getText().trim().isEmpty() || this.addressTextField.getText().trim().isEmpty() || !this.managerRadioButton.isSelected() && !this.cashierRadioButton.isSelected();
//    }
//
//    public void addOnAction(ActionEvent event) throws Exception {
//        if (!this.isInputFieldEmpty()) {
//            this.addAccount();
//        } else {
//            Notifications.create().text("Please fill in all fields.").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
//        }
//
//    }
//
//    private void addAccount() throws SQLException {
//        DatabaseConnection connection = new DatabaseConnection();
//        Connection connectDB = connection.getConnection();
//        String firstname = this.firstnameTextField.getText();
//        String lastname = this.lastnameTextField.getText();
//        String username = this.usernameTextField.getText();
//        String password = this.passwordTextField.getText();
//        String gmail = this.gmailTextField.getText();
//        String phone = this.phoneTextField.getText();
//        String address = this.addressTextField.getText();
//        int isManager = this.managerRadioButton.isSelected() ? 1 : 0;
//        String insertFields = "insert into account(firstname, lastname, gmail, phone_number, username,password, address, type_customer) values ('";
//        String insertValues = firstname + "','" + lastname + "','" + gmail + "','" + phone + "','" + username + "','" + password + "','" + address + "','" + isManager + "')";
//        String insertToRegister = insertFields + insertValues;
//
//        try {
//            Statement statement = connectDB.createStatement();
//            statement.executeUpdate(insertToRegister);
//            Notifications.create().text("You have add account successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
//            resetInputField();
//        } catch (Exception var15) {
//            Notifications.create().text("You have failed add account in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
//        }
//
//    }
//
//    public void closeButtonOnAction(ActionEvent event) {
//        Stage stage = (Stage)this.closeButton.getScene().getWindow();
//        stage.close();
//    }
//
//    public void minimizeButtonOnAction(ActionEvent event) {
//        Stage stage = (Stage)this.closeButton.getScene().getWindow();
//        stage.setIconified(true);
//    }
//
//    public void tblCashierClick(MouseEvent mouseEvent) {
//    }
//
//    public void searchOnAction(ActionEvent event) {
//    }
//
//    public void cashierUpdateOnAction(ActionEvent event) {
//    }
//
//    public void cashierDeleteOnAction(ActionEvent event) {
//    }
//
//    public void setOnAction(ActionEvent event) {
//    }
//
//    @FXML
//    public void signOutButtonOnAction(ActionEvent event) {
//        Stage stage = (Stage)this.signOutButton.getScene().getWindow();
//        stage.close();
//
//        try {
//            Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("LoginUI.fxml"));
//            Stage loginStage = new Stage();
//            loginStage.setScene(new Scene(root));
//            loginStage.show();
//        } catch (Exception var5) {
//            var5.printStackTrace();
//            var5.getCause();
//        }
//
//    }
//
//    public void resetInputField() {
//        firstnameTextField.setText("");
//        lastnameTextField.setText("");
//        usernameTextField.setText("");
//        passwordTextField.setText("");
//        gmailTextField.setText("");
//        phoneTextField.setText("");
//        addressTextField.setText("");
//        managerRadioButton.setSelected(false);
//        cashierRadioButton.setSelected(false);
//    }
//}
//

package com.example.pbl3;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

public class AccountController implements Initializable {
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
    private TextField searchTextField;
    @FXML
    private Button minimizeButton;
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
            resetInputField();
            loadTable();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        AccountTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showButtonOnAction();
            }
        });
    }

    public void SelectRowHandler() {

    }
    public void loadTable()
    {
        ObservableList<Account> list = FXCollections.observableArrayList();
        //Connection database
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection connectDB = ConnectNow.getConnection();
        String accountQuery = "select firstname,lastname,gmail,phone_number,username,password,address,type_customer from account";
        try {

            Statement statement = connectDB.createStatement();
            ResultSet   queryResult = statement.executeQuery(accountQuery);
            //Moi dong database
            while(queryResult.next())
            {
                String firstName = queryResult.getString("firstname"); // get gia tri column firstname trong database
                String lastName = queryResult.getString("lastname");
                String gmail = queryResult.getString("gmail");
                String phone = queryResult.getString("phone_number");
                String username = queryResult.getString("username");
                String password = queryResult.getString("password");
                String address = queryResult.getString("address");
                Boolean type = queryResult.getBoolean("type_customer");
                String typeOfUser="";
                if(type == true)
                {
                    typeOfUser = "Manager";
                }
                else
                {
                    typeOfUser = "Cashier";
                }
                Account acc = new Account(firstName,lastName,gmail,phone,username,password,address,typeOfUser);
                list.add(acc); // Add vao list
                //System.out.println(acc.fName + acc.lName);
            }

            Col_FName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            Col_LName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            Col_Gmail.setCellValueFactory(new PropertyValueFactory<>("gmail"));
            Col_Password.setCellValueFactory(new JFXPasswordCellValueFactory());
            Col_Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            Col_Username.setCellValueFactory(new PropertyValueFactory<>("username"));
            Col_Address.setCellValueFactory(new PropertyValueFactory<>("address"));
            Col_TypeOfUser.setCellValueFactory(new PropertyValueFactory<>("typeOfUser"));
            AccountTableView.setItems(list);

        }
        catch (SQLException e)
        {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

    public void resetInputField() {
        firstnameTextField.setText("");
        lastnameTextField.setText("");
        usernameTextField.setText("");
        passwordTextField.setText("");
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

    public void showInputField() {

    }

    public void showButtonOnAction() {
        if (AccountTableView.getSelectionModel().getSelectedItem().username != "") {
            usernameTextField.setEditable(false);
            firstnameTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().firstName);
            lastnameTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().lastName);
            usernameTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().username);
            passwordTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().password);
            gmailTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().gmail);
            addressTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().address);
            phoneTextField.setText(AccountTableView.getSelectionModel().getSelectedItem().phone);
            managerRadioButton.setSelected(AccountTableView.getSelectionModel().getSelectedItem().typeOfUser == "Manager" ? true : false);
            cashierRadioButton.setSelected(AccountTableView.getSelectionModel().getSelectedItem().typeOfUser == "Cashier" ? true : false);

        } else {
            Notifications.create().text("Please select an account to show")
                    .title("Notification");
        }
    }

    @FXML
    private void updateButtonOnAction() {
        if (AccountTableView.getSelectionModel().getSelectedItem().username != "") {
            UpdateRow(firstnameTextField.getText(), lastnameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), gmailTextField.getText(), addressTextField.getText(), phoneTextField.getText(), managerRadioButton.isSelected());

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
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        String updateAccount = updateQuery(firstname,lastname,username,password,gmail,address,phone,isManager);

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(updateAccount);
            Notifications.create().text("You have update account successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            resetInputField();
            loadTable();
        } catch (Exception var15) {
            Notifications.create().text("You have failed update account in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }

    }
    @FXML
    private void deleteButtonOnAction() {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();
        Account selected = AccountTableView.getSelectionModel().getSelectedItem();
        String query = "DELETE FROM account WHERE username  = '" + selected.getUsername() + "'";
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);
            Notifications.create().text("successfully .").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            loadTable();
            resetInputField();
        } catch (Exception var15) {
            var15.printStackTrace();
            Notifications.create().text("error!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
    }
//    @FXML
//    void pressEnterOnAction(KeyEvent event) {
//        if(event.getCode().toString() == "ENTER")
//        {
//            UpdateListAccount(searchTextField.getText());
//
//        }
//    }
//    public void UpdateListAccount(String txt)
//    {
//        accountsList = FXCollections.observableArrayList(Search(txt)
//                new Account("Vo","Khang","khang@gmail.com","0111111111","black","123456","15 NLB","Cashier")
//        );
//        firstnameColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("firstName"));
//        lastnameColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("lastName"));
//        usernameColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
//        passwordColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("password"));
//        gmailColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("gmail"));
//        phoneColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("phone_number"));
//        addressColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("address"));
//        typeColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("type_admin"));
//        table.setItems(accountsList);
//    }
//    public List<Account> Search(String txt)
//    {
//        List<Account> accounts = new ArrayList<Account>();
//        String sql = "SELECT * FROM account WHERE lastname = " + "'" + txt + "' OR firstname LIKE '%" + txt
//                + "%' OR username LIKE '%" + txt + "%' OR gmail LIKE '%" + txt + "%' OR phone_number LIKE '%"
//                + txt +"%' OR address LIKE '%" + txt + "%'";
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDb = connectNow.getConnection();
//
//        try {
//            PreparedStatement pst = connectDb.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next())
//            {
//                String firsname = rs.getString(1);
//                String lastname = rs.getString(2);
//                String gmail = rs.getString(3);
//                String phone_number = rs.getString(4);
//                String username = rs.getString(5);
//                String password = rs.getString(6);
//                String address = rs.getString(7);
//                boolean type_admin = rs.getBoolean(8);
//                String type;
//                if(type_admin) type = "Manager";
//                else type = "Cashier";
//                accounts.add(new Account(firsname,lastname,gmail,phone_number,username,password,address,type));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                connectDb.close();
//            }
//            catch (SQLException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return accounts;
//    }

    @FXML
    private void resetButtonOnAction() {
        resetInputField();
    }
}


