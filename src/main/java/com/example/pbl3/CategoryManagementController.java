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

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryManagementController implements Initializable {

    @FXML
    private Label LabelCategory;
    @FXML
    private javafx.scene.layout.AnchorPane AnchorPane;
    @FXML
    private Button addButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private TableView<Category> CategoryTableView;
    @FXML
    private TableColumn<Category, Integer> Col_Id;
    @FXML
    private TableColumn<Category, String> Col_Name;
    @FXML
    private TextField CateNameTextField;

    OpenUI openUI = new OpenUI();
    private PreparedStatement add = null;
    private PreparedStatement delete = null;
    private PreparedStatement update = null;

    @FXML
    public void logOutMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("LoginUI.fxml");
    }

    public void accountMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("AccountManagementUI.fxml");
    }

    public void homePageMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("HomePageUI.fxml");
    }
    public void productMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ProductManagementUI.fxml");
    }
    public void supplierMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("SupplierManagementUI.fxml");
    }

    @FXML
    public void importMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ImportUI.fxml");
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
    void myAccountMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("MyAccountUI.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection link = connection.getConnection();
        try {

            String queryAdd = "insert into category (Category_Name) values (?)";
            this.add = link.prepareStatement(queryAdd);
            String queryDelete = "DELETE FROM category WHERE Id  = ? ";
            this.delete = link.prepareStatement(queryDelete);
            String queryUpdate = "update supplier set Category_Name = ? where Id = ?";
            this.update = link.prepareStatement(queryUpdate);

        } catch (SQLException var7) {
            var7.printStackTrace();
        }
        LabelCategory.setText("Add Category");
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
        this.CategoryTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.SelectedRowAction();
            }

        });
    }
    public void SelectedRowAction()
    {
        if (((Category)this.CategoryTableView.getSelectionModel().getSelectedItem()).getCate_Name() != "") {
            addButton.setDisable(true);
            LabelCategory.setText("Category Details");
            this.CateNameTextField.setText(((Category)this.CategoryTableView.getSelectionModel().getSelectedItem()).getCate_Name());

        }
    }
    public void butUpdateOnAction()
    {
        try {
            this.update.setString(1, this.CateNameTextField.getText());
            this.update.setInt(2,CategoryTableView.getSelectionModel().getSelectedItem().getCate_Id());
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
        Category selected = CategoryTableView.getSelectionModel().getSelectedItem();
        try {
            this.delete.setInt(1,selected.getCate_Id());
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
            this.add.setString(1, this.CateNameTextField.getText());
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
        LabelCategory.setText("Add Category");
        CateNameTextField.setText("");
        this.CategoryTableView.getSelectionModel().clearSelection();
    }

    public ObservableList<Category> getAllCategory()
    {
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection connectDB = ConnectNow.getConnection();
        String query = "select * from category";
        ObservableList<Category> listCategory = FXCollections.observableArrayList();
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);

            while(queryResult.next()) {
                int Category_Id = queryResult.getInt("Id");
                String Category_Name = queryResult.getString("Category_Name");
                Category category = new Category(Category_Id,Category_Name);
                listCategory.add(category);
            }
            this.Col_Id.setCellValueFactory(new PropertyValueFactory("Cate_Id"));
            this.Col_Name.setCellValueFactory(new PropertyValueFactory("Cate_Name"));
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return listCategory;
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
        this.CategoryTableView.setItems(this.getAllCategory());
    }
    public boolean ValidationField()
    {
        return( CateNameTextField.getText().trim().isEmpty());
    }
}