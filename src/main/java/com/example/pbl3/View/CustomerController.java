package com.example.pbl3.View;

import com.example.pbl3.BLL.BLLCustomers;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.DTO.Customer;
import com.example.pbl3.OpenUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;


import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerController implements Initializable  {
    @FXML
    private javafx.scene.layout.AnchorPane AnchorPane;
    @FXML
    private TableColumn<Customer, Integer> Col_ID;
    @FXML
    private TableColumn<Customer, Date> Col_Birthday;
    @FXML
    private TableColumn<Customer, String> Col_FName;
    @FXML
    private TableColumn<Customer, String> Col_Gender;
    @FXML
    private TableColumn<Customer, String> Col_Gmail;
    @FXML
    private TableColumn<Customer, String> Col_LName;
    @FXML
    private TableColumn<Customer, String> Col_Phone;
    @FXML
    private TableColumn<Customer,String> Col_Address;
    @FXML
    private TableView<Customer> CustomerTableView;
    @FXML
    private Button addButton;
    @FXML
    private DatePicker birthdayDatePicker;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private ToggleGroup gender_type;
    @FXML
    private TextField gmailTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton otherRadioButton;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button updateButton;
    @FXML
    private MenuItem account;
    @FXML
    private MenuItem statistics;

    OpenUI openUI = new OpenUI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        this.loadTable("");
        this.addButton.setOnAction((e) ->
        {
            if(!ValidationField())
            {
                this.addCustomer();
            }
            else
            {
                Notifications.create().text("Please fill in all fields.").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            }
        });
        this.resetButton.setOnAction((e) -> {
            this.Clear();
        });
        this.deleteButton.setOnAction(e->{
            this.Delete();
        });
        this.CustomerTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.SelectedRowAction();
            }
        });
        this.updateButton.setOnAction((e) -> {
            this.Update();
        });
    }

    public void loadTable(String txt) {
        ObservableList<Customer> list = FXCollections.observableArrayList(BLLCustomers.searchCustomer(txt));
        this.Col_ID.setCellValueFactory(new PropertyValueFactory("ID"));
        this.Col_FName.setCellValueFactory(new PropertyValueFactory("firstname"));
        this.Col_LName.setCellValueFactory(new PropertyValueFactory("lastname"));
        this.Col_Gmail.setCellValueFactory(new PropertyValueFactory("gmail"));
        this.Col_Address.setCellValueFactory(new PropertyValueFactory("address"));
        this.Col_Phone.setCellValueFactory(new PropertyValueFactory("phone"));
        this.Col_Gender.setCellValueFactory(new PropertyValueFactory("gender"));
        this.Col_Birthday.setCellValueFactory(new PropertyValueFactory("birthday"));
        this.CustomerTableView.setItems(list);
    }

    public boolean ValidationField()
    {
        return((firstnameTextField.getText().trim().isEmpty()) || (lastnameTextField.getText().trim().isEmpty()) || (gmailTextField.getText().trim().isEmpty())
                || (phoneTextField.getText().trim().isEmpty()) || (birthdayDatePicker.getValue() == null));

    }

    public void addCustomer() {
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
        String gender;
        if(maleRadioButton.isSelected()) gender = "Male";
        else if(femaleRadioButton.isSelected()) gender = "Female";
        else gender = "Other";
        Customer c = new Customer(0,firstnameTextField.getText(),lastnameTextField.getText(),gmailTextField.getText(),
                phoneTextField.getText(),gender,java.sql.Date.valueOf(this.birthdayDatePicker.getValue()),addressTextField.getText());
        if(BLLCustomers.AddCustomer(c))
        {
            Clear();
            Notifications.create().text("You have added customer successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            this.loadTable("");
        }
        else
        {
            Notifications.create().text("You have failed add customer in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    public void Clear()
    {
        firstnameTextField.setText("");
        lastnameTextField.setText("");
        gmailTextField.setText("");
        phoneTextField.setText("");
        addressTextField.setText("");
        otherRadioButton.setSelected(true);
        birthdayDatePicker.setValue(LocalDate.now());
        addButton.setDisable(false);
        CustomerTableView.getSelectionModel().clearSelection();
        birthdayDatePicker.setValue(null);
    }

    public void Delete()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this customer?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            if(BLLCustomers.DeleteCustomer(CustomerTableView.getSelectionModel().getSelectedItem().getID()))
            {
                Notifications.create().text("Successfully .").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
                loadTable("");
                Clear();
            }
            else
            {
                Notifications.create().text("Error!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        }
    }

    public void SelectedRowAction()
    {
        if ((this.CustomerTableView.getSelectionModel().getSelectedItem()).getFirstname() != "")
        {
            addButton.setDisable(true);
            this.firstnameTextField.setText((this.CustomerTableView.getSelectionModel().getSelectedItem()).getFirstname());
            this.lastnameTextField.setText((this.CustomerTableView.getSelectionModel().getSelectedItem()).getLastname());
            this.gmailTextField.setText((this.CustomerTableView.getSelectionModel().getSelectedItem()).getGmail());
            this.phoneTextField.setText((this.CustomerTableView.getSelectionModel().getSelectedItem()).getPhone());
            Date date = this.CustomerTableView.getSelectionModel().getSelectedItem().getBirthday();
            this.addressTextField.setText((this.CustomerTableView.getSelectionModel().getSelectedItem()).getAddress());
            this.birthdayDatePicker.setValue(LocalDate.of(date.getYear()+1900, date.getMonth()+1, date.getDate()));
            String gender = (this.CustomerTableView.getSelectionModel().getSelectedItem()).getGender();
            if(gender.equals("Male")) maleRadioButton.setSelected(true);
            else if(gender.equals("Female")) femaleRadioButton.setSelected(true);
            else otherRadioButton.setSelected(true);
        }
    }

    public void Update()
    {
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
        int id = CustomerTableView.getSelectionModel().getSelectedItem().getID();
        String gender;
        if(maleRadioButton.isSelected()) gender = "Male";
        else if(femaleRadioButton.isSelected()) gender = "Female";
        else gender = "Other";
        Customer c = new Customer(id,firstnameTextField.getText(),lastnameTextField.getText(),gmailTextField.getText(),
                phoneTextField.getText(),gender,java.sql.Date.valueOf((LocalDate)this.birthdayDatePicker.getValue()),addressTextField.getText());
        if(BLLCustomers.UpdateCustomer(c))
        {
            Notifications.create().text("You have updated customer successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            this.loadTable("");
            Clear();
        }
        else
        {
            Notifications.create().text("You have failed update customer in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
    }
    @FXML
    void pressEnterOnAction(KeyEvent event) {
        if(event.getCode().toString() == "ENTER")
        {
            loadTable(searchTextField.getText());

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
        if(BLLProject.typecashier == false)
        {
            account.setVisible(false);
            statistics.setVisible(false);
        }
    }
}