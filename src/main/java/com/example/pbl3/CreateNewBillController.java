package com.example.pbl3;

import com.sun.mail.imap.protocol.ID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.LocalDateStringConverter;
import org.controlsfx.control.Notifications;


import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateNewBillController implements Initializable {

    @FXML
    private TableView<DetailBill> DetailBillTableView;

    @FXML
    private TableColumn<DetailBill, Integer> Col_ProductSerial;

    @FXML
    private TableColumn<DetailBill, Integer> Col_Quantity;

    @FXML
    private ComboBox<String> ccbCashier;

    @FXML
    private ComboBox<String> ccbCustomer;

    @FXML
    private ComboBox<String> ccbProduct;

    @FXML
    private ComboBox<Integer> ccbQuantity;


    ObservableList<DetailBill> list = FXCollections.observableArrayList();
    DatabaseConnection ConnectNow = new DatabaseConnection();
    Connection connectDB = ConnectNow.getConnection();

    double Total = 0;
    String query = "";
    int ID_Bill = 0;

    public void addCCBCustomer()
    {
        String Query = "select firstname, lastname, phone from customer";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(Query);
            ObservableList<String> list = FXCollections.observableArrayList();
            while(queryResult.next())
            {
                String item = queryResult.getString("phone") + " - " + queryResult.getString("firstname") + " " +  queryResult.getString("lastname");
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

    public void addCCBCashier()
    {
        String Query = "select firstname, lastname, phone_number from account where type_customer = 0";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(Query);
            ObservableList<String> list = FXCollections.observableArrayList();
            while (queryResult.next()) {
                String item = queryResult.getString("phone_number") + " - " + queryResult.getString("firstname") + " " +  queryResult.getString("lastname");
                list.add(item);
            }
            ccbCashier.setItems(list);
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String) null, var14);
            var14.printStackTrace();
        }
    }

    public void addCCBProduct()
    {
        String Query = "select Barcode, ProductName from product";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(Query);
            ObservableList<String> list = FXCollections.observableArrayList();
            while (queryResult.next()) {
                String item = queryResult.getString("ProductName");
                list.add(item);
            }
            ccbProduct.setItems(list);
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String) null, var14);
            var14.printStackTrace();
        }
    }

    public void addCCBQuantity()
    {
        if(ccbProduct.getSelectionModel().getSelectedItem() != null)
        {
            String Query = "select quantity from import where product_name = '" + ccbProduct.getSelectionModel().getSelectedItem() + "'";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(Query);
                ObservableList<Integer> list = FXCollections.observableArrayList();
                while (queryResult.next()) {
                    for(int i=1; i<=queryResult.getInt("quantity"); i++)
                        list.add(i);
                }
                ccbQuantity.setItems(list);
            } catch (SQLException var14) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String) null, var14);
                var14.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCCBCustomer();
        addCCBCashier();
        addCCBProduct();
        new AutoCompleteBox(ccbCustomer);
        new AutoCompleteBox(ccbCashier);
    }

    public boolean Check()
    {
        if(ccbCustomer.getSelectionModel().getSelectedItem() == null) return false;
        if(ccbCashier.getSelectionModel().getSelectedItem() == null) return false;
        if(ccbProduct.getSelectionModel().getSelectedItem() == null) return false;
        if(ccbQuantity.getSelectionModel().getSelectedItem() == null) return false;
        return true;
    }

    public double getPrice(String Product)
    {
        double price = 0;
        String query = "select SalePrice from product where ProductName = '" + Product + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                price = queryResult.getDouble("SalePrice");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return price;
    }


    @FXML
    void addOnAcTion(ActionEvent event) {
        if(Check())
        {
            list.add(new DetailBill(ccbProduct.getSelectionModel().getSelectedItem(),ccbQuantity.getSelectionModel().getSelectedItem()));
            this.Col_ProductSerial.setCellValueFactory(new PropertyValueFactory("Product"));
            this.Col_Quantity.setCellValueFactory(new PropertyValueFactory("Quantity"));
            this.DetailBillTableView.setItems(list);
        }
        else
        {
            Notifications.create().text("Error. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    public int getIDCustomer(String phone)
    {
        int IDCustomer = 0;
        query = "select ID from customer where phone = '" + phone + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                IDCustomer = queryResult.getInt("ID");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return IDCustomer;
    }

    public void addBill(int IDCustomer, String cashier_phone, Double Total)
    {
        query = "insert into bill(ID_Customer, Date, Cashier_Phone, Total) value(" + IDCustomer + ",'" + java.sql.Date.valueOf(LocalDate.now()) + "','" + cashier_phone + "'," + Total + ")";
        try {
            PreparedStatement add = null;
            add = connectDB.prepareStatement(query);
            add.execute();
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
    }

    public int getIDBill()
    {
        int IDBill = 0;
        query = "select MAX(ID_Bill) from bill";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                IDBill = queryResult.getInt("MAX(ID_Bill)");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return IDBill;
    }

    public void addDetailBill(int IDBill, int Quantity, String Product)
    {
        query = "insert into detailbill(ID_Bill, Quantity, Product) value(" + ID_Bill + "," + Quantity + ",'" + Product + "')";
        try {
            PreparedStatement add = null;
            add = connectDB.prepareStatement(query);
            add.execute();
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        int soluong = 0;
        query = "select quantity from import where product_name = '" + Product + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                soluong = queryResult.getInt("quantity");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        query = "update import set quantity = " + (soluong - Quantity) + " where product_name = '" + Product + "'";
        try {
            PreparedStatement add = null;
            add = connectDB.prepareStatement(query);
            add.execute();
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
    }

    @FXML
    void createOnAction(ActionEvent event) {
        if(Check())
        {
            ccbProduct.getSelectionModel().clearSelection();
            ccbQuantity.getSelectionModel().clearSelection();
            if(list.size() == 0)
            {
                Notifications.create().text("Please add product!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
                return;
            }
            String[] cus = ccbCustomer.getSelectionModel().getSelectedItem().split(" ");
            String[] cas = ccbCashier.getSelectionModel().getSelectedItem().split(" ");
            String phone_cashier = cas[0];
            list.forEach(detailbill -> {
                    Total += getPrice(detailbill.getProduct());
            });
            addBill(getIDCustomer(cus[0]),phone_cashier, Total);
            ID_Bill = getIDBill();
            System.out.print(ID_Bill);
            list.forEach(detailbill -> {
                addDetailBill(ID_Bill,detailbill.getQuantity(),detailbill.getProduct());
            });
            list.clear();
            DetailBillTableView.setItems(list);
            Notifications.create().text("You have create new bill successfully into our system!").title("Well-done!").hideAfter(Duration.seconds(5.0D)).show();
        }
        else
        {
            Notifications.create().text("Error. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    @FXML
    void cancelOnAction(ActionEvent event) {
        list = null;
        Stage stage = (Stage)this.ccbQuantity.getScene().getWindow();
        stage.close();
        OpenUI openUI = new OpenUI();
        openUI.Open_UI("ViewBillUI.fxml","");
    }

    @FXML
    void deleteRow(KeyEvent event) {
        if(DetailBillTableView.getSelectionModel().getSelectedItem() != null)
        {
            if(event.getCode() == KeyCode.BACK_SPACE)
            {
                System.out.print("KAAAA");
                list.remove(DetailBillTableView.getSelectionModel().getSelectedItem());
                DetailBillTableView.setItems(list);
            }
        }
    }


}
