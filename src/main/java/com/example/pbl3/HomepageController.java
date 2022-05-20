package com.example.pbl3;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private MenuItem product;
    @FXML
    private MenuItem account;
    @FXML
    private MenuItem logout;

    @FXML
    private TableColumn<TopProduct, Integer> col_No;

    @FXML
    private TableColumn<TopProduct, Double> col_Price;

    @FXML
    private TableColumn<TopProduct, String> col_ProductName;

    @FXML
    private TableColumn<TopProduct, JFXButton> col_ProductStatus;

    @FXML
    private TableColumn<TopProduct, Double> col_Sale;

    @FXML
    private Label labelWelcome;
    @FXML
    private Label labelTotalCus;

    @FXML
    private Label labelTotalProd;

    @FXML
    private Label labelTotalquotes;


    @FXML
    private TableView<TopProduct> tableViewTopProduct;


    OpenUI openUI = new OpenUI();
    Statement statement = null;

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
    void myAccountMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("MyAccountUI.fxml");
    }

    public void setDecentralization()
    {
        if(openUI.typecashier == true)
        {
            account.setDisable(false);
        }
        else {
            account.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection link = connection.getConnection();
        try
        {
            statement = link.createStatement();
        }
        catch (SQLException e)
        {

        }
        if(openUI.typecashier == true)
        {
            labelWelcome.setText("Welcome back,Manager!");
        }
        else
        {
            labelWelcome.setText("Welcome back,Cashier!");
        }

        loadTable();
        setDecentralization();
        setTotal();
    }
    public void setTotal()
    {
        labelTotalCus.setText(DatabaseHelper.getTotalCustomer());
        labelTotalProd.setText(DatabaseHelper.getTotalProduct());
        labelTotalquotes.setText(DatabaseHelper.getTotalQuote());
    }
    public void loadTable()
    {
        ObservableList<TopProduct> list = FXCollections.observableArrayList();
        try{
            String selectQuery = "select ProductName,SalePrice,sum(detailbill.Quantity) as Sale from product inner join detailbill on product.ProductName = detailbill.Product group by ProductName order by Sale desc limit 10";
            ResultSet rs  = statement.executeQuery(selectQuery);
            int no =1;
            String Status = "404";
            while(rs.next())
            {
                String Name = rs.getString("ProductName");
                Double Price = rs.getDouble("SalePrice");
                Double Sale = rs.getDouble("Sale");
                int quantity = DatabaseHelper.GetQuantityByProductname(Name);
                if(quantity != 0) {
                     Status = "In Stock";
                }
                else
                {
                    Status = "Out Of Stock";
                }

                TopProduct topProduct = new TopProduct(no,Name,Price,Sale,Status);
                no++;
                list.add(topProduct);
            }
            this.col_No.setCellValueFactory(new PropertyValueFactory("No"));
            this.col_Price.setCellValueFactory(new PropertyValueFactory("Price"));
            this.col_ProductName.setCellValueFactory(new PropertyValueFactory("ProductName"));
            this.col_Sale.setCellValueFactory(new PropertyValueFactory("Sale"));
            this.col_ProductStatus.setCellValueFactory(new JFXButtonTypeUserCellValueFactory());
            this.tableViewTopProduct.setItems(list);

        }
        catch (SQLException e10)
        {

        }
    }
    private class JFXButtonTypeUserCellValueFactory implements Callback<TableColumn.CellDataFeatures<TopProduct, JFXButton>, ObservableValue<JFXButton>> {

        @Override
        public ObservableValue<JFXButton> call(TableColumn.CellDataFeatures<TopProduct, JFXButton> param) {
            TopProduct item = param.getValue();

            JFXButton button = new JFXButton();
            button.setPrefWidth(col_ProductStatus.getWidth() / 0.5);
            button.setText(item.getProductStatus());

            if (item.getProductStatus().equals("In Stock")) {
                button.setStyle("-fx-background-color: #69e067; -fx-background-radius: 10px");
            } else {
                button.setStyle("-fx-background-color: #ff3363; -fx-background-radius: 10px");
            }
            return new SimpleObjectProperty<>(button);
        }
    }
}
