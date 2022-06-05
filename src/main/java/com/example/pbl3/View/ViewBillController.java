package com.example.pbl3;

import com.example.pbl3.View.ProductController;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ViewBillController implements Initializable {
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private MenuItem product;
    @FXML
    private MenuItem account;
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem importPrd;
    @FXML
    private MenuItem billMenuItem;
    @FXML
    TextField txtDay;
    @FXML
    TextField txtMonth;
    @FXML
    TextField txtYear;
    @FXML
    JFXButton butSearch;
    @FXML
    TableView<Bill> BillTableView;
    @FXML
    TableColumn<Bill,Integer> Col_IDBill;
    @FXML
    TableColumn<Bill,String> Col_CustomerName;
    @FXML
    TableColumn<Bill, Date> Col_Date;
    @FXML
    TableColumn<Bill,String> Col_CashierName;
    @FXML
    TableColumn<Bill,Double> Col_Total;
    @FXML
    private JFXButton butCreatNewBill;
    @FXML
    private TextField searchTextField;

    @FXML
    JFXButton createNewBillBut;

    OpenUI openUI = new OpenUI();
    ObservableList<Bill> list = FXCollections.observableArrayList();

    private Statement bill = null;
    private Statement detailBill = null;
    private Statement cbb = null;
    private PreparedStatement search = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        DatabaseConnection connection = new DatabaseConnection();
        Connection link = connection.getConnection();
        try {
//            String searchQuery = "select bill.ID_Bill, customer.ID as IDCustomer, concat(customer.firstname, ? , customer.lastname) as CustomerName," +
//                    "Date,phone_number,concat(account.firstname, ? , account.lastname) as CashierName,Total from bill " +
//                    "inner join customer on bill.ID_Customer = customer.ID " +
//                    "inner join account on bill.Cashier_Phone = account.phone_number " +
//                    "inner join detailbill on bill.ID_Bill = detailbill.ID_Bill " +
//                    "inner join product on detailbill.Product = product.ProductName " +
//                    "inner join category on product.Category = category.Category_Name " +
//                    "where year(bill.Date) like ? and month(bill.Date) like ? and day(bill.Date) like ? " +
//                    "and customer.phone like ? and bill.Cashier_Phone like ? and category.Category_Name like ? " +
//                    "group by bill.ID_Bill order by bill.ID_Bill asc";

            String searchQuery = "select distinct bill.ID_Bill, customer.ID as IDCustomer, concat(customer.firstname, ? , customer.lastname) as CustomerName," +
                    "Date,phone_number,concat(account.firstname, ? , account.lastname) as CashierName,Total from bill " +
                    "inner join customer on bill.ID_Customer = customer.ID " +
                    "inner join account on bill.Cashier_Phone = account.phone_number " +
                    "inner join detailbill on bill.ID_Bill = detailbill.ID_Bill " +
                    "inner join product on detailbill.Product = product.ProductName " +
                    "inner join category on product.Category = category.Category_Name " +
                    "where customer.firstname like ? or customer.lastname like ? or account.firstname like ? or account.lastname like ?" +
                    "or ProductName like ?";

            bill = link.createStatement();
            detailBill = link.createStatement();
            cbb = link.createStatement();
            search = link.prepareStatement(searchQuery);
        } catch (SQLException var111) {
            var111.printStackTrace();
        }
        loadTableBill();


        BillTableView.setOnMouseClicked( event -> {
            if( event.getClickCount() == 2 && !BillTableView.getSelectionModel().isEmpty()) {
                openUI.setIDBill(BillTableView.getSelectionModel().getSelectedItem().getID_Bill());
                Stage stage = (Stage) this.BillTableView.getScene().getWindow();
                stage.close();
                openUI.Open_UI("CreateNewBillUI.fxml");
            }});

    }
    public void search() throws SQLException {
        list.clear();

        search.setString(1," ");
        search.setString(2," ");
        search.setString(3,"%" + searchTextField.getText() + "%");
        search.setString(4,"%"+ searchTextField.getText() + "%");
        search.setString(5,"%"+ searchTextField.getText() + "%");
        search.setString(6,"%"+ searchTextField.getText() + "%");
        search.setString(7,"%"+ searchTextField.getText() + "%");
//        search.setString(8,"%"+ searchTextField.getText() + "%");
        try {
            ResultSet queryResult =  search.executeQuery();

            while(queryResult.next()) {
                int Bill_ID = queryResult.getInt("ID_Bill");
                String CusName = queryResult.getString("CustomerName");
                String CashierName = queryResult.getString("CashierName");
                Double total = queryResult.getDouble("Total");
                Date date = queryResult.getDate("Date");
                String cashierphone = queryResult.getString("phone_number");
                int IDCustomer = queryResult.getInt("IDCustomer");
                Bill bill = new Bill(Bill_ID,IDCustomer,CusName,cashierphone,CashierName,date,total);
                list.add(bill);
            }
            this.Col_IDBill.setCellValueFactory(new PropertyValueFactory("ID_Bill"));
            this.Col_CustomerName.setCellValueFactory(new PropertyValueFactory("customerName"));
            this.Col_Date.setCellValueFactory(new PropertyValueFactory("date"));
            this.Col_CashierName.setCellValueFactory(new PropertyValueFactory("cashierName"));
            this.Col_Total.setCellValueFactory(new PropertyValueFactory("Total_Money"));
            this.BillTableView.setItems(list);
        } catch (SQLException var14) {
            Logger.getLogger(ViewBillController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
    }


    public void loadTableBill()
    {
        String selectBill = "select ID_Bill, customer.ID as IDCustomer, concat(customer.firstname, ' ', customer.lastname) as CustomerName,Date,phone_number,concat(account.firstname, ' ', account.lastname) as CashierName,Total from bill \n" +
                "inner join customer on bill.ID_Customer = customer.ID\n" +
                "inner join account on bill.Cashier_Phone = account.phone_number";

        try {
            ResultSet queryResult = bill.executeQuery(selectBill);

            while(queryResult.next()) {
                int Bill_ID = queryResult.getInt("ID_Bill");
                String CusName = queryResult.getString("CustomerName");
                String CashierName = queryResult.getString("CashierName");
                Double total = queryResult.getDouble("Total");
                Date date = queryResult.getDate("Date");
                String cashierphone = queryResult.getString("phone_number");
                int IDCustomer = queryResult.getInt("IDCustomer");
                Bill bill = new Bill(Bill_ID,IDCustomer,CusName,cashierphone,CashierName,date,total);
                list.add(bill);
            }

            this.Col_IDBill.setCellValueFactory(new PropertyValueFactory("ID_Bill"));
            this.Col_CustomerName.setCellValueFactory(new PropertyValueFactory("customerName"));
            this.Col_Date.setCellValueFactory(new PropertyValueFactory("date"));
            this.Col_CashierName.setCellValueFactory(new PropertyValueFactory("cashierName"));
            this.Col_Total.setCellValueFactory(new PropertyValueFactory("total_Money"));
            this.BillTableView.setItems(list);
        } catch (SQLException var14) {
            Logger.getLogger(ViewBillController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
    }

    public ObservableList<String> getAllCustomerPhone()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "select concat(firstname,' ',lastname) as nameCustomer, phone from customer";
        try {
            ResultSet queryResult = cbb.executeQuery(query);
            while(queryResult.next()) {
                String CustomerPhone = queryResult.getString("phone") + " - " + queryResult.getString("nameCustomer");
                list.add(CustomerPhone);
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> getAllCategory()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "select Category_Name from category";
        try {
            ResultSet queryResult = cbb.executeQuery(query);
            while(queryResult.next()) {
                String CustomerPhone = queryResult.getString("Category_Name");
                list.add(CustomerPhone);
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> getAllCashierPhone()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "select concat(firstname,' ', lastname) as nameCashier, phone_number from account where type_customer = 0";
        try {
            ResultSet queryResult = cbb.executeQuery(query);
            while(queryResult.next()) {
                String CustomerPhone = queryResult.getString("phone_number") + " - " + queryResult.getString("nameCashier"); ;
                list.add(CustomerPhone);
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return list;
    }

    @FXML
    void createNewBillOnAction(ActionEvent event) {
        Stage stage = (Stage)this.butCreatNewBill.getScene().getWindow();
        stage.close();
        OpenUI openUI = new OpenUI();
        openUI.Open_UI("CreateNewBillUI.fxml");
    }

    @FXML
    void deleteRow(KeyEvent event) {
        if(BillTableView.getSelectionModel().getSelectedItem() != null)
        {
            if(event.getCode() == KeyCode.BACK_SPACE)
            {
                list.remove(BillTableView.getSelectionModel().getSelectedItem() != null);
                BillTableView.setItems(list);
                String query = "delete from bill where ID_Bill = " + BillTableView.getSelectionModel().getSelectedItem().getID_Bill();
                DatabaseConnection ConnectNow = new DatabaseConnection();
                Connection connectDB = ConnectNow.getConnection();
                try {
                    PreparedStatement add = null;
                    add = connectDB.prepareStatement(query);
                    add.execute();
                } catch (SQLException var14) {
                    Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
                    var14.printStackTrace();
                }
                BillTableView.getSelectionModel().clearSelection();
                list.clear();
                loadTableBill();
            }
            else
            {
                Notifications.create().text("Please select row!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
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
