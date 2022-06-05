package com.example.pbl3;

import com.example.pbl3.DTO.Supplier;
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

import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private TableColumn<Supplier, String> Col_Phone;
    @FXML
    private TextField SupIdTextField;
    @FXML
    private TextField SupNameTextField;
    @FXML
    private TextField SupAddressTextField;
    @FXML
    private TextField SupPhoneTextField;
    @FXML
            private MenuItem account;

    OpenUI openUI = new OpenUI();
    private PreparedStatement add = null;
    private PreparedStatement delete = null;
    private PreparedStatement update = null;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();

        DatabaseConnection connection = new DatabaseConnection();
        Connection link = connection.getConnection();
        try {

            String queryAdd = "insert into supplier (Supplier_Name,Supplier_Address,Supplier_Phone) values (?,?,?)";
            this.add = link.prepareStatement(queryAdd);
            String queryDelete = "DELETE FROM supplier WHERE Id  = ? ";
            this.delete = link.prepareStatement(queryDelete);
            String queryUpdate = "update supplier set Supplier_Name = ? , Supplier_Address = ?, Supplier_Phone = ? where Id = ?";
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
            this.SupPhoneTextField.setText(((Supplier)this.SupplierTableView.getSelectionModel().getSelectedItem()).getSup_Phone());
        }
    }
    public void butUpdateOnAction()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update the supplier?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                this.update.setString(1, this.SupNameTextField.getText());
                this.update.setString(2, this.SupAddressTextField.getText());
                this.update.setInt(3,Integer.parseInt(this.SupIdTextField.getText()));
                this.update.setString(4,this.SupPhoneTextField.getText());
                this.update.execute();
                butResetOnAction();
                this.loadTable();
                Notifications.create().text("You have update product successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            } catch (SQLException e) {
                Notifications.create().text("You have failed update account in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        }
    }
    public void butDeleteOnAction()
    {
        Supplier selected = SupplierTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the supplier?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
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
    }
    public void butAddOnAction()
    {
        try {
            this.add.setString(1, this.SupNameTextField.getText());
            this.add.setString(2,this.SupAddressTextField.getText());
            this.add.setString(3,this.SupPhoneTextField.getText());
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
        SupPhoneTextField.setText("");
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
                String Supplier_Phone = queryResult.getString("Supplier_Phone");

                Supplier supplier = new Supplier(Supplier_Id,Supplier_Name,Supplier_Address,Supplier_Phone);
                list.add(supplier);
            }

            this.Col_Id.setCellValueFactory(new PropertyValueFactory("Sup_Id"));
            this.Col_Name.setCellValueFactory(new PropertyValueFactory("Sup_Name"));
            this.Col_Address.setCellValueFactory(new PropertyValueFactory("Sup_Address"));
            this.Col_Phone.setCellValueFactory(new PropertyValueFactory("Sup_Phone"));

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