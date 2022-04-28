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
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SupplierManagementController implements Initializable {
    @FXML
    private Label LabelSupplier;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button addButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private TableView<Supplier> SupplierTableView;
    @FXML
    private TableColumn<Supplier, Integer> Col_Id;
    @FXML
    private TableColumn<Supplier, String> Col_Name;
    @FXML
    private TableColumn<Supplier, String> Col_Address;
    @FXML
    private TextField SupNameTextField;
    @FXML
    private TextField SupAddressTextField;

    OpenUI openUI = new OpenUI();
    private PreparedStatement add = null;
    private PreparedStatement delete = null;
    private PreparedStatement update = null;
    @FXML
    public void logOutMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("LoginUI.fxml","");
    }

    public void accountMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("AccountManagementUI.fxml", "");
    }

    public void homePageMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("HomePageUI.fxml","");
    }
    public void productMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ProductManagementUI.fxml", "");
    }
    public void supplierMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("SupplierManagement.fxml", "");
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
    public void importMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ImportUI.fxml","");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseConnection connection = new DatabaseConnection();
        Connection link = connection.getConnection();
        try {

            String queryAdd = "insert into supplier (Supplier_Name,Supplier_Address) values (?,?)";
            this.add = link.prepareStatement(queryAdd);
            String queryDelete = "DELETE FROM supplier WHERE Id  = ? ";
            this.delete = link.prepareStatement(queryDelete);
            String queryUpdate = "update supplier set Supplier_Name = ? , Supplier_Address = ? where Id = ?";
            this.update = link.prepareStatement(queryUpdate);

        } catch (SQLException var7) {
            var7.printStackTrace();
        }
        LabelSupplier.setText("Add Supplier");
        this.loadTable();
        this.resetButton.setOnAction(e->{
            this.butResetOnAction();
        });
        this.addButton.setOnAction(e->{
            if(!ValidationField())
            {
                this.butAddOnAction();
            }
            else
            {
                Notifications.create().text("Please fill in all fields.").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            }
        });
        this.deleteButton.setOnAction(e-> {
            this.butDeleteOnAction();
        });
        this.updateButton.setOnAction(e->{
            this.butUpdateOnAction();
        });
        this.SupplierTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.SelectedRowAction();
            }

        });
    }
    public void SelectedRowAction()
    {
        if (((Supplier)this.SupplierTableView.getSelectionModel().getSelectedItem()).getSup_Name() != "") {
            addButton.setDisable(true);
            LabelSupplier.setText("Supplier Details");
            this.SupNameTextField.setText(((Supplier)this.SupplierTableView.getSelectionModel().getSelectedItem()).getSup_Name());
            this.SupAddressTextField.setText(((Supplier)this.SupplierTableView.getSelectionModel().getSelectedItem()).getSup_Address());
        }
    }
    public void butUpdateOnAction()
    {
        try {
            this.update.setString(1, this.SupNameTextField.getText());
            this.update.setString(2, this.SupAddressTextField.getText());
            this.update.setInt(3,Integer.parseInt(String.valueOf(((Supplier)this.SupplierTableView.getSelectionModel().getSelectedItem()).getSup_Id())));
            this.update.execute();
            butResetOnAction();
            this.loadTable();
            Notifications.create().text("You have update product successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
        } catch (SQLException e) {
            Notifications.create().text("You have failed update account in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }

    }
    public void butDeleteOnAction()
    {
        Supplier selected = SupplierTableView.getSelectionModel().getSelectedItem();
        try {
            this.delete.setInt(1,selected.getSup_Id());
            delete.execute();
            Notifications.create().text("successfully .").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            loadTable();
            butResetOnAction();
        } catch (Exception var15) {
            var15.printStackTrace();
            Notifications.create().text("error!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
    }
    public void butAddOnAction()
    {
        try {
            this.add.setString(1, this.SupNameTextField.getText());
            this.add.setString(2,this.SupAddressTextField.getText());
            this.add.execute();
            Notifications.create().text("You have add product successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            butResetOnAction();
            this.loadTable();
        } catch (SQLException e ) {
            Notifications.create().text("You have failed add product in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();

        }

    }
    public void butResetOnAction()
    {
        //this.loadTable();
        addButton.setDisable(false);
        LabelSupplier.setText("Add Supplier");
        SupNameTextField.setText("");
        SupAddressTextField.setText("");
        this.SupplierTableView.getSelectionModel().clearSelection();
    }

    public ObservableList<Supplier> getAllSupplier()
    {
        ObservableList<Supplier> list = FXCollections.observableArrayList();
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection connectDB = ConnectNow.getConnection();
        String query = "select * from supplier";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                int Supplier_Id = queryResult.getInt("Id");
                String Supplier_Name = queryResult.getString("Supplier_Name");
                String Supplier_Address = queryResult.getString("Supplier_Address");

                Supplier supplier = new Supplier(Supplier_Id,Supplier_Name,Supplier_Address);
                list.add(supplier);
            }

            this.Col_Id.setCellValueFactory(new PropertyValueFactory("Sup_Id"));
            this.Col_Name.setCellValueFactory(new PropertyValueFactory("Sup_Name"));
            this.Col_Address.setCellValueFactory(new PropertyValueFactory("Sup_Address"));

        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> getAllSupplierName()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection connectDB = ConnectNow.getConnection();
        String query = "select Supplier_Name from supplier";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                String Supplier_Name = queryResult.getString("Supplier_Name");
                list.add(Supplier_Name);
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> getAllProductName()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection connectDB = ConnectNow.getConnection();
        String query = "select ProductName from products";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                String Supplier_Name = queryResult.getString("ProductName");
                list.add(Supplier_Name);
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return list;
    }
    public void loadTable()
    {
        this.SupplierTableView.setItems(this.getAllSupplier());
    }
    public boolean ValidationField()
    {
        return(SupAddressTextField.getText().trim().isEmpty() || SupNameTextField.getText().trim().isEmpty());
    }

}
