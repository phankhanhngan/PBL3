package com.example.pbl3;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StatisticsController implements Initializable {

    @FXML
    private JFXComboBox<Integer> cbbMonth;

    @FXML
    private JFXComboBox<Integer> cbbYear;
    @FXML
    private Label txtSales;

    @FXML
    private Label txtTotalproduct;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private NumberAxis Money;
    //private CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private BarChart<String, Double> chart;

    @FXML
    private javafx.scene.chart.PieChart PieChart;

    @FXML
    private MenuItem category;

    @FXML
    private MenuItem homepage;

    @FXML
    private MenuItem importPrd;

    @FXML
    private MenuItem logout;

    @FXML
    private CategoryAxis month;

    @FXML
    private MenuItem product;

    OpenUI openUI = new OpenUI();

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
    void myAccountMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("MyAccountUI.fxml");
    }

    DatabaseConnection ConnectNow = new DatabaseConnection();
    Connection connectDB = ConnectNow.getConnection();

    private void loadBarChart(int Year)
    {
        String query = "select sum(bill.Total) as Sale, Month(Date) as month from bill where Year(Date) = " +Year+" group by Month(Date)";
        double []sales = new double[13];
        String []Month = {"January", "February","March", "April","May", "June", "July", "August", "September", "October","November", "December"};
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next())
            {
                sales[queryResult.getInt("month")-1] = queryResult.getDouble("Sale");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }

        XYChart.Series<String,Double> series = new XYChart.Series<>();
        chart.getData().clear();
        for (int i =0; i<12;i++ )
        {
            series.getData().add(new XYChart.Data(Month[i],sales[i]));
        }
        chart.getData().add(series);

    }
    private void loadPieChart(int Year, int Month)
    {
        int quantity = 0;
        double Sumsales = 0;
        ObservableList<String> listCategory = FXCollections.observableArrayList();
        ObservableList<Double> listSales = FXCollections.observableArrayList();
        String query = "select sum(detailbill.Quantity*product.SalePrice) as Sale,sum(detailbill.Quantity) as Quantity, product.Category from detailbill inner join product on detailbill.Product = product.ProductName inner join bill on detailbill.ID_Bill = bill.ID_Bill where Year(Date) = " +Year+
                " and Month(Date) = "+ Month+ " group by product.Category";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                quantity +=queryResult.getInt("Quantity");
                Double Sales = queryResult.getDouble("Sale");
                Sumsales+=queryResult.getDouble("Sale");
                listSales.add(Sales);
                String Category_Name = queryResult.getString("Category");
                listCategory.add(Category_Name);
            }
            txtTotalproduct.setText(quantity+"");
            txtSales.setText(Sumsales+"");
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        PieChart.getData().clear();
        for(int i = 0; i<listCategory.size();i++)
        {
            PieChart.getData().add(new PieChart.Data(listCategory.get(i),listSales.get(i)));
        }

    }

    @FXML
    private void SelectMonthOnAction(ActionEvent event)
    {
        loadPieChart(cbbYear.getSelectionModel().getSelectedItem(),cbbMonth.getSelectionModel().getSelectedItem());

    }

    @FXML
    private void SelectYearOnAction(ActionEvent event)
    {
        System.out.print(cbbYear.getSelectionModel().getSelectedItem());
        loadBarChart(cbbYear.getSelectionModel().getSelectedItem());
        System.out.print(cbbYear.getSelectionModel().getSelectedItem());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> ListYear = FXCollections.observableArrayList();
        ListYear.add(2018);
        ListYear.add(2019);
        ListYear.add(2020);
        ListYear.add(2021);
        ListYear.add(2022);
        cbbYear.setItems(ListYear);
        ObservableList<Integer> ListMonth = FXCollections.observableArrayList();
        for(int i = 1; i<13; i++)
        {
            ListMonth.add(i);
        }
        cbbMonth.setItems(ListMonth);
        loadBarChart(2022);
        cbbYear.setValue(2022);
        loadPieChart(2022,5);
        cbbMonth.setValue(5);


    }
}