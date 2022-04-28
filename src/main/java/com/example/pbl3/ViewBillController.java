package com.example.pbl3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewBillController implements Initializable {
    @FXML
    private TableView<Bill> BillTableView;
    @FXML
    private javafx.scene.layout.AnchorPane AnchorPane;
    @FXML
    private TableColumn<Bill, Integer> Col_IDBill;
    @FXML
    private TableColumn<Bill, Integer> Col_IDCustomer;
    @FXML
    private TableColumn<Bill, String> Col_CashierPhone;
    @FXML
    private TableColumn<Bill, Date> Col_Date;
    @FXML
    private TableColumn<Bill, Double> Col_TotalMoney;
    @FXML
    private MenuItem category;
    @FXML
    private MenuItem homepage;
    @FXML
    private MenuItem importPrd;
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem product;
    @FXML
    private Button createNewBillBut;
    @FXML
    private Button searchBut;
    @FXML
    private ComboBox<String> ccbCashier;
    @FXML
    private ComboBox<String> ccbCategory;
    @FXML
    private ComboBox<String> ccbCustomer;
    @FXML
    private ComboBox<String> ccbDay;
    @FXML
    private ComboBox<String> ccbMonth;
    @FXML
    private ComboBox<String> ccbTotalMoney;
    @FXML
    private ComboBox<String> ccbYear;

    OpenUI openUI = new OpenUI();
    DatabaseConnection ConnectNow = new DatabaseConnection();
    Connection connectDB = ConnectNow.getConnection();

    @FXML
    public void logOutMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("LoginUI.fxml","");
    }

    @FXML
    public void accountMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("AccountManagementUI.fxml", "");
    }

    @FXML
    public void homePageMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("HomePageUI.fxml","");
    }

    @FXML
    public void importMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ImportUI.fxml","");
    }

    @FXML
    public void supplierMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("SupplierManagementUI.fxml", "");
    }

    @FXML
    public void categoryMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CategoryManagementUI.fxml", "");
    }

    @FXML
    public void customerMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CustomerUI.fxml", "");
    }
    @FXML
    public void productMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ProductManagementUI.fxml", "");
    }

    @FXML
    public void searchOnAction()
    {

    }

    @FXML
    public void createNewBillOnAction()
    {
        Stage stage = (Stage)this.createNewBillBut.getScene().getWindow();
        stage.close();
        OpenUI openUI = new OpenUI();
        openUI.Open_UI("CreateNewBillUI.fxml","");
    }

    public void loadTable() {
        ObservableList<Bill> list = FXCollections.observableArrayList();
        String BillQuery = "select * from bill";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(BillQuery);
            while(queryResult.next()) {
                int ID_Bill = queryResult.getInt("ID_Bill");
                int ID_Customer = queryResult.getInt("ID_Customer");
                Date date = queryResult.getDate("Date");
                String Cashier_Phone = queryResult.getString("Cashier_Phone");
                Double TotalMoney = queryResult.getDouble("Total");
                list.add(new Bill(ID_Bill,ID_Customer,Cashier_Phone,date,TotalMoney));
            }
            this.Col_IDBill.setCellValueFactory(new PropertyValueFactory("ID_Bill"));
            this.Col_IDCustomer.setCellValueFactory(new PropertyValueFactory("ID_Customer"));
            this.Col_CashierPhone.setCellValueFactory(new PropertyValueFactory("Cashier_Phone"));
            this.Col_Date.setCellValueFactory(new PropertyValueFactory("date"));
            this.Col_TotalMoney.setCellValueFactory(new PropertyValueFactory("Total_Money"));
            this.BillTableView.setItems(list);
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }

    }

    public void addCCBCustomer()
    {
        String Query = "select firstname, lastname, phone from customer";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(Query);
            ObservableList<String> list = FXCollections.observableArrayList();
            while(queryResult.next())
            {
                String item = queryResult.getString("firstname") + " " +  queryResult.getString("lastname") + " - " + queryResult.getString("phone");
                list.add(item);
            }
            ccbCustomer.setItems(list);
        }
        catch (SQLException var14)
        {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
    }

    public void addCCBCategory() {
        String Query = "select Category_Name from category";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(Query);
            ObservableList<String> list = FXCollections.observableArrayList();
            while (queryResult.next()) {
                String item = queryResult.getString("Category_Name");
                list.add(item);
            }
            ccbCategory.setItems(list);
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String) null, var14);
            var14.printStackTrace();
        }
    }

    public void addCCBTotalMoney()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("0 - 50");
        list.add("50 - 200");
        list.add("200 - 500");
        list.add("500 - 1000");
        list.add("1000 - 2000");
        list.add("2000 - 5000");
        list.add("5000 - 10000");
        list.add("1000 or more");
        ccbTotalMoney.setItems(list);
    }

    public void addCCBCashier()
    {
        String Query = "select firstname, lastname, phone_number from account where type_customer = 0";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(Query);
            ObservableList<String> list = FXCollections.observableArrayList();
            while (queryResult.next()) {
                String item = queryResult.getString("firstname") + " " +  queryResult.getString("lastname") + " - " + queryResult.getString("phone_number");
                list.add(item);
            }
            ccbCashier.setItems(list);
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String) null, var14);
            var14.printStackTrace();
        }
    }

    public void addCCBYear()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i = 2000; i <= 2022; i++)
        {
            list.add(i + "");
        }
        ccbYear.setItems(list);
    }

    public void addCCBMonth()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i = 1; i <= 12; i++)
        {
            list.add(i + "");
        }
        ccbMonth.setItems(list);
    }

    public void addCCBDay()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i = 1; i <= 31; i++)
        {
            list.add(i + "");
        }
        ccbDay.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        addCCBCustomer();
        addCCBCategory();
        addCCBTotalMoney();
        addCCBCashier();
        addCCBYear();
        addCCBMonth();
    }
}
