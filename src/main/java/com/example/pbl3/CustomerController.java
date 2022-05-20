package com.example.pbl3;

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
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private PreparedStatement add = null;
    private PreparedStatement update = null;
    private PreparedStatement delete = null;

    OpenUI openUI = new OpenUI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        birthdayDatePicker.setValue(LocalDate.now());
        DatabaseConnection connection = new DatabaseConnection();
        Connection link = connection.getConnection();
        try {
            String query1 = "insert into customer(firstname,lastname,gmail,phone,gender,birthday,address) values(?,?,?,?,?,?,?)";
            this.add = link.prepareStatement(query1);
            String query2 = "update customer set firstname = ? , lastname = ?, gmail = ?, phone = ?, gender = ?, birthday = ?,address =? where ID = ?";
            this.update = link.prepareStatement(query2);
            String query3 = "DELETE FROM customer WHERE ID  = ? ";
            this.delete = link.prepareStatement(query3);
        } catch (SQLException var7) {
            var7.printStackTrace();
        }
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
        ObservableList<Customer> list = FXCollections.observableArrayList();
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection connectDB = ConnectNow.getConnection();
        String sql = "SELECT * FROM customer WHERE lastname = " + "'" + txt + "' OR firstname LIKE '%" + txt
                + "%' OR gmail LIKE '%" + txt + "%' OR phone LIKE '%" + txt +"%'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);

            while(queryResult.next()) {
                int ID = queryResult.getInt("ID");
                String firstname = queryResult.getString("firstname");
                String lastname = queryResult.getString("lastname");
                String gmail = queryResult.getString("gmail");
                String phone = queryResult.getString("phone");
                String gender = queryResult.getString("gender");
                Date birthday = queryResult.getDate("birthday");
                String address = queryResult.getString("address");
                Customer customer = new Customer(ID, firstname, lastname, gmail, phone, gender, birthday,address);
                list.add(customer);
            }

            this.Col_ID.setCellValueFactory(new PropertyValueFactory("ID"));
            this.Col_FName.setCellValueFactory(new PropertyValueFactory("firstname"));
            this.Col_LName.setCellValueFactory(new PropertyValueFactory("lastname"));
            this.Col_Gmail.setCellValueFactory(new PropertyValueFactory("gmail"));
            this.Col_Phone.setCellValueFactory(new PropertyValueFactory("phone"));
            this.Col_Gender.setCellValueFactory(new PropertyValueFactory("gender"));
            this.Col_Birthday.setCellValueFactory(new PropertyValueFactory("birthday"));
            this.Col_Address.setCellValueFactory(new PropertyValueFactory("address"));
            this.CustomerTableView.setItems(list);
        } catch (SQLException var14) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }

    }

    public boolean ValidationField()
    {
        return((firstnameTextField.getText().trim().isEmpty()) || (lastnameTextField.getText().trim().isEmpty()) || (gmailTextField.getText().trim().isEmpty())
                || (phoneTextField.getText().trim().isEmpty()) || (birthdayDatePicker.getValue() == null));

    }

    public void addCustomer() {
        try {
            this.add.setString(1, this.firstnameTextField.getText());
            this.add.setString(2, this.lastnameTextField.getText());
            this.add.setString(3, this.gmailTextField.getText());
            this.add.setString(4,this.phoneTextField.getText());
            this.add.setString(7,this.addressTextField.getText());
            String gender;
            if(maleRadioButton.isSelected()) gender = "Male";
            else if(femaleRadioButton.isSelected()) gender = "Female";
            else gender = "Other";
            this.add.setString(5,gender);
            this.add.setDate(6, java.sql.Date.valueOf((LocalDate)this.birthdayDatePicker.getValue()));
            this.add.execute();
            Clear();
            Notifications.create().text("You have add customer successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            this.loadTable("");
        } catch (SQLException var2) {
            Notifications.create().text("You have failed add customer in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            System.err.println(var2);
            var2.printStackTrace();
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
    }

    public void Delete()
    {
        Customer selected = CustomerTableView.getSelectionModel().getSelectedItem();
        try {
            this.delete.setInt(1,selected.getID());
            delete.execute();
            Notifications.create().text("successfully .").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            loadTable("");
            Clear();
        } catch (Exception var15) {
            var15.printStackTrace();
            Notifications.create().text("error!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    public void SelectedRowAction()
    {
        if (((Customer)this.CustomerTableView.getSelectionModel().getSelectedItem()).getFirstname() != "")
        {
            addButton.setDisable(true);
            this.firstnameTextField.setText(((Customer)this.CustomerTableView.getSelectionModel().getSelectedItem()).getFirstname());
            this.lastnameTextField.setText(((Customer)this.CustomerTableView.getSelectionModel().getSelectedItem()).getLastname());
            this.gmailTextField.setText(((Customer)this.CustomerTableView.getSelectionModel().getSelectedItem()).getGmail());
            this.phoneTextField.setText(((Customer)this.CustomerTableView.getSelectionModel().getSelectedItem()).getPhone());
            Date date = this.CustomerTableView.getSelectionModel().getSelectedItem().getBirthday();
            this.addressTextField.setText(((Customer)this.CustomerTableView.getSelectionModel().getSelectedItem()).getAddress());
            this.birthdayDatePicker.setValue(LocalDate.of(date.getYear()+1900, date.getMonth()+1, date.getDate()));
            String gender = ((Customer)this.CustomerTableView.getSelectionModel().getSelectedItem()).getGender();
            if(gender.equals("Male")) maleRadioButton.setSelected(true);
            else if(gender.equals("Female")) maleRadioButton.setSelected(true);
            else otherRadioButton.setSelected(true);
        }
    }

    public void Update()
    {
        try {
            this.update.setString(1, this.firstnameTextField.getText());
            this.update.setString(2, this.lastnameTextField.getText());
            this.update.setString(3, this.gmailTextField.getText());
            this.update.setString(4, this.phoneTextField.getText());
            String gender;
            if(maleRadioButton.isSelected()) gender = "Male";
            else if(femaleRadioButton.isSelected()) gender = "Female";
            else gender = "Other";
            this.update.setString(5,gender);
            this.update.setDate(6, java.sql.Date.valueOf((LocalDate)this.birthdayDatePicker.getValue()));
            this.update.setString(7,this.addressTextField.getText());
            this.update.setInt(8,((Customer)CustomerTableView.getSelectionModel().getSelectedItem()).getID());
            System.out.print(update);
            this.update.execute();
            Notifications.create().text("You have add product successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            this.loadTable("");
        } catch (SQLException var2) {
            Notifications.create().text("You have failed add product in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            System.err.println(var2);
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
    public void logOutMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("LoginUI.fxml");
    }
    @FXML
    public void accountMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("AccountManagementUI.fxml");
    }

    @FXML
    public void homePageMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("HomePageUI.fxml");
    }

    @FXML
    public void productMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ProductManagementUI.fxml");
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
    public void importMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ImportUI.fxml");
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
    void myAccountMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("MyAccountUI.fxml");
    }
}
