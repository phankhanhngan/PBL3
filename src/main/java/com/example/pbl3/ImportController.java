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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportController implements Initializable {
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private MenuItem homepage;
    @FXML
    private MenuItem account;
    @FXML
    private MenuItem product;
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem importPrd;
    @FXML
    private  Button submitButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private  Button search;
    @FXML
    private ComboBox prdSerialCBBox;
    @FXML
    private ComboBox supplierCBBox;
    @FXML
    private TextField quantityTxt;
    @FXML
    private DatePicker importDate;
    @FXML
    private TextField searchtxt;
    @FXML
    private TableView<Import> ImportTableView;
    @FXML
    private TableColumn<Import, String> Col_prdName;
    @FXML
    private TableColumn<Import, String> Col_supplier;
    @FXML
    private TableColumn<Import, String> Col_quantity;
    @FXML
    private TableColumn<Import, String> Col_importID;
    @FXML
    private TableColumn<Import, Date> Col_importDate;

    private PreparedStatement update = null;
    private PreparedStatement add = null;
    private PreparedStatement delete = null;


    private ObservableList<Import> importList;
    OpenUI openUI = new OpenUI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            UpdateListImport("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ImportTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showImportRecord();
                submitButton.setDisable(true);
            }
        });
        showSupplierComboBoxItem();
        showProductComboboxItem();

        DatabaseConnection con = new DatabaseConnection();
        Connection link = con.getConnection();
        try {
            String queryAdd = "Insert into import(product_name, supplier_name, quantity, import_date) values (?, ?, ?, ?)";
            String queryUpdate = "Update import set product_name = ?, supplier_name = ?, quantity = ?, import_date = ? where import_id = ?";
            String queryDelete = "Delete from import where import_id = ?";

            this.add = link.prepareStatement(queryAdd);
            this.update = link.prepareStatement(queryUpdate);
            this.delete = link.prepareStatement(queryDelete);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void showSupplierComboBoxItem() {
        this.supplierCBBox.setItems(this.getAllSupplierName());
    }

    private void showProductComboboxItem() {
        this.prdSerialCBBox.setItems(this.getAllProductName());
    }
    private void showImportRecord() {
        quantityTxt.setText(ImportTableView.getSelectionModel().getSelectedItem().getQuantity());
        this.supplierCBBox.setValue(this.ImportTableView.getSelectionModel().getSelectedItem().getSupplier_name());
        this.prdSerialCBBox.setValue(this.ImportTableView.getSelectionModel().getSelectedItem().getProduct_name());
        java.util.Date date = this.ImportTableView.getSelectionModel().getSelectedItem().getImport_date();
        this.importDate.setValue(LocalDate.of(date.getYear()+1900, date.getMonth()+1, date.getDate()));
    }

    public boolean isInputFieldEmpty() {
        return this.prdSerialCBBox.getSelectionModel().getSelectedItem() == null || this.supplierCBBox.getSelectionModel().getSelectedItem() == null
                || quantityTxt.getText().trim().isEmpty() || importDate.getValue() == null;
    }

    public void resetInputField() {
        prdSerialCBBox.getSelectionModel().clearSelection();
        supplierCBBox.getSelectionModel().clearSelection();
        quantityTxt.setText("");
        submitButton.setDisable(false);
        ImportTableView.getSelectionModel().clearSelection();
        importDate.setValue(null);
    }

    @FXML
    public List<Import> Search(String txt) {
        List<Import> imports = new ArrayList<>();
        String sql = "SELECT * FROM import WHERE product_name LIKE '%" + txt
                + "%' OR supplier_name LIKE '%" + txt + "%'" ;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        try {
            PreparedStatement pst = connectDb.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt(1);
                String prdName = rs.getString(2);
                int quantity = rs.getInt(3);
                String supplier= rs.getString(4);
                Date date = rs.getDate(5);
                imports.add(new Import(id,prdName,quantity,supplier, date));
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
        return imports;
    }

    private void UpdateListImport(String s) throws SQLException {
        importList = FXCollections.observableArrayList(Search(s));
        Col_prdName.setCellValueFactory(new PropertyValueFactory("product_name"));
        Col_supplier.setCellValueFactory(new PropertyValueFactory("supplier_name"));
        Col_quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        Col_importID.setCellValueFactory(new PropertyValueFactory("import_id"));
        Col_importDate.setCellValueFactory(new PropertyValueFactory("import_date"));
        ImportTableView.setItems(importList);
    }




    @FXML
    public void searchOnAction(ActionEvent event) throws SQLException {
        UpdateListImport(searchtxt.getText());
    }

    @FXML
    public void resetButtonOnAction(ActionEvent event) {
        resetInputField();
    }

    @FXML
    public void submitButtonOnAction(ActionEvent event) throws SQLException {
        if (!this.isInputFieldEmpty()) {
            this.addImport();
            UpdateListImport("");
            resetInputField();

        } else {
            Notifications.create().text("Please fill in all fields.").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
        }
    }

    private void addImport() {
        try {
            this.add.setString(1, prdSerialCBBox.getSelectionModel().getSelectedItem().toString());
            this.add.setString(2, supplierCBBox.getSelectionModel().getSelectedItem().toString());
            this.add.setInt(3, Integer.parseInt(quantityTxt.getText()));
            this.add.setDate(4, Date.valueOf(importDate.getValue()));
            this.add.execute();
            Notifications.create().text("Add successfully!")
                    .title("Notification")
                    .hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Notifications.create().text("Add Failure!")
                    .title("Notification")
                    .hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
        }
    }

    @FXML
    private void updateButtonOnAction(ActionEvent event) throws SQLException {
        if (ImportTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                this.update.setString(1,prdSerialCBBox.getSelectionModel().getSelectedItem().toString());
                this.update.setString(2,supplierCBBox.getSelectionModel().getSelectedItem().toString());
                this.update.setInt(3,Integer.parseInt(quantityTxt.getText()));
                this.update.setDate(4, Date.valueOf(importDate.getValue()));
                this.update.setInt(5,ImportTableView.getSelectionModel().getSelectedItem().getImport_id());
                this.update.execute();
                Notifications.create().text("Update successfully!")
                        .title("Notification")
                        .hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();;
                UpdateListImport("");
                resetInputField();

            } catch (SQLException e) {
                e.printStackTrace();
                Notifications.create().text("Update failure!")
                        .title("Notification") .hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();;
            }
        } else {
            Notifications.create().text("Please select an account to update")
                    .title("Notification");
        }
    }

    @FXML
    private void deleteButtonOnAction(ActionEvent event) {
        try {
            this.delete.setInt(1,ImportTableView.getSelectionModel().getSelectedItem().getImport_id());
            this.delete.execute();
            Notifications.create().text("successfully .").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            UpdateListImport("");
            resetInputField();
        } catch (Exception var15) {
            var15.printStackTrace();
            Notifications.create().text("error!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
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
    public void productMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ProductManagementUI.fxml", "");
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
}


