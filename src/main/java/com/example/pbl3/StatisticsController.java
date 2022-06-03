package com.example.pbl3;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.Date;


public class StatisticsController implements Initializable {

    @FXML
    private StackedBarChart<String, Double> StackedBarchart;
    @FXML
    private PieChart PieChart;
    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private NumberAxis Money;

    @FXML
    private BarChart<String, Double> barchart_Revenue;

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

    @FXML
    private DatePicker end_date;

    @FXML
    private DatePicker start_date;
    @FXML
    private JFXComboBox<String> typeCBB;

    @FXML
    private Label txtOrders;
    @FXML
    private TableColumn<TopProduct, String> col_ProductName;

    @FXML
    private TableColumn<TopProduct, Integer> col_ProductNo;

    @FXML
    private TableColumn<TopProduct, Integer> col_ProductQty;

    @FXML
    private TableColumn<TopSeller, String> col_SellerName;

    @FXML
    private TableColumn<TopSeller, Integer> col_SellerNo;

    @FXML
    private TableColumn<TopSeller, Double> col_SellerRevenue;

    @FXML
    private TableView<TopProduct> tableViewTopProduct;

    @FXML
    private TableView<TopSeller> tableViewTopSeller;
    @FXML
    private Label txtRenevue;
    @FXML
    private Label labelInfo;
    @FXML
    private Label piechartNameLabel;
    @FXML
    private Label columnChartNameLabel;
    @FXML
            private MenuItem account;
    OpenUI openUI = new OpenUI();

    XYChart.Series<String,Double> series = new XYChart.Series<>();
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

    DatabaseConnection ConnectNow = new DatabaseConnection();
    Connection connectDB = ConnectNow.getConnection();
    public void loadTopProduct()
    {
        ObservableList<TopProduct> listTopProduct = FXCollections.observableArrayList();
        String query = "select ProductName,SalePrice,sum(detailbill.Quantity) as quantity from product inner join detailbill on product.ProductName = detailbill.Product group by product.ProductName order by quantity desc limit 5";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            int no =1;
            while(queryResult.next()) {
                String Name = queryResult.getString("ProductName");
                int Quantity = queryResult.getInt("quantity");
                TopProduct topProduct = new TopProduct(no,Name,Quantity);
                listTopProduct.add(topProduct);
                no++;
            }
            col_ProductNo.setCellValueFactory(new PropertyValueFactory("No"));
            col_ProductName.setCellValueFactory(new PropertyValueFactory("ProductName"));
            col_ProductQty.setCellValueFactory(new PropertyValueFactory("quantity"));
            tableViewTopProduct.setItems(listTopProduct);

        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }

    }
    public void loadTopSeller()
    {
        ObservableList<TopSeller> listTopSeller = FXCollections.observableArrayList();
        String query = "select sum(Total) as Sale, concat(account.firstname,' ',account.lastname) as cashier_Name from bill inner join account on bill.Cashier_Phone = account.phone_number group by cashier_Name order by Sale desc limit 5";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            int no =1;
            while(queryResult.next()) {
                String Name = queryResult.getString("cashier_Name");
                double sale = queryResult.getInt("Sale");
                TopSeller topSeller = new TopSeller(no,Name,sale);
                listTopSeller.add(topSeller);
                no++;
            }
            col_SellerNo.setCellValueFactory(new PropertyValueFactory("no"));
            col_SellerName.setCellValueFactory(new PropertyValueFactory("seller_Name"));
            col_SellerRevenue.setCellValueFactory(new PropertyValueFactory("revenue"));
            tableViewTopSeller.setItems(listTopSeller);

        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }

    }
    private void loadPieChart(String query)
    {
        int quantity = 0;
        double Sumsales = 0;

        ObservableList<String> listCategory = FXCollections.observableArrayList();
        ObservableList<Double> listSales = FXCollections.observableArrayList();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                listSales.add(queryResult.getDouble("Sale"));
                listCategory.add(queryResult.getString("Category"));
            }
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



    private void loadBarChart_Revenue(String query)
    {
        series.getData().clear();
        series.setName("Renevue");
        ObservableList<String> Date= FXCollections.observableArrayList();
        ObservableList<Double> Sales = FXCollections.observableArrayList();
        int QuantityBill = 0;
        double Renevue = 0;

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next())
            {
                Date.add(queryResult.getString("NewDate"));
                Sales.add(queryResult.getDouble("Sale"));
                QuantityBill+= queryResult.getInt("quantityBill");
                Renevue+=queryResult.getDouble("Sale");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        barchart_Revenue.getData().clear();
        for (int i =0; i<Sales.size();i++ )
        {
            series.getData().add(new XYChart.Data(Date.get(i), Sales.get(i)));
        }
        barchart_Revenue.getData().add(series);
        txtOrders.setText(QuantityBill+"");
        txtRenevue.setText(""+ Renevue);
    }
    @FXML
    void startDateOnAction(ActionEvent event) {
        if(typeCBB.getSelectionModel().getSelectedItem().equals("Month") )
        {
            LocalDate Selected_datestart = start_date.getValue();
            LocalDate date_start = Selected_datestart.withDayOfMonth(1);
            start_date.setValue(date_start);
        }
    }

    @FXML
    void endDateOnAction(ActionEvent event) {
        Date endDate = Date.valueOf(end_date.getValue());
        Date startDate = Date.valueOf(start_date.getValue());
        if(endDate.compareTo(startDate) < 0)
        {
            Notifications.create().text("You need to choose valid date. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            return;
        }
        if(typeCBB.getSelectionModel().getSelectedItem().equals("Day"))
        {
            long day = (endDate.getTime() - startDate.getTime())/(24 * 60 * 60 * 1000);
            if(day<=31){
                String query = "select sum(bill.Total) as Sale, count(bill.ID_Bill) as quantityBill,concat(Day(Date),'/',Month(Date)) as NewDate from bill where (Date>= '"+startDate.toString()+"'and Date <='"+endDate.toString()+"') group by Date order by Date";
                loadBarChart_Revenue(query);
                String query2= "select sum(detailbill.Quantity*product.SalePrice) as Sale,sum(detailbill.Quantity) as Quantity, product.Category from detailbill inner join product on detailbill.Product = product.ProductName inner join bill on detailbill.ID_Bill = bill.ID_Bill where(Date>= '"+startDate.toString()+"'and Date <='"+endDate.toString()+"') group by product.Category";
                loadPieChart(query2);
                piechartNameLabel.setText("Doanh thu theo danh mục các sản phẩm bán được theo ngày");
                columnChartNameLabel.setText("Doanh thu theo ngày");
            }
            else{
                Notifications.create().text("You need to choose valid date (<= 31 days). Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
                return;
            }
        }
        if(typeCBB.getSelectionModel().getSelectedItem().equals("Month") )
        {
            LocalDate Selected_dateend = end_date.getValue();
            LocalDate date_end = Selected_dateend.withDayOfMonth(Selected_dateend.lengthOfMonth());
            end_date.setValue(date_end);
            Date end_Date = Date.valueOf(end_date.getValue());
            Date start_Date = Date.valueOf(start_date.getValue());
            String query = "select sum(bill.Total) as Sale,count(bill.ID_Bill) as quantityBill, concat(Month(Date),'/',Year(Date)) as NewDate from bill where (Date>= '"+start_Date.toString()+"'and Date <='"+end_Date.toString()+"') group by Month(Date) order by Date";
            loadBarChart_Revenue(query);
            String query2= "select sum(detailbill.Quantity*product.SalePrice) as Sale,sum(detailbill.Quantity) as Quantity, product.Category from detailbill inner join product on detailbill.Product = product.ProductName inner join bill on detailbill.ID_Bill = bill.ID_Bill where(Date>= '"+startDate.toString()+"'and Date <='"+endDate.toString()+"') group by product.Category";
            loadPieChart(query2);
            piechartNameLabel.setText("Doanh thu theo danh mục các sản phẩm bán được theo tháng");
            columnChartNameLabel.setText("Doanh thu theo tháng");
        }
    }



    public void setTypeCBB()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Day");
        list.add("Month");
        typeCBB.setItems(list);
        start_date.setValue(LocalDate.now());
    }
    public void loadPiechartInfo()
    {
        for (PieChart.Data data: PieChart.getData())
        {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
                if(event.getButton() == MouseButton.SECONDARY)
                {
                    labelInfo.setText("");
                }
                else
                {
                    labelInfo.setTranslateX(event.getSceneX() - labelInfo.getLayoutX());
                    labelInfo.setTranslateY(event.getSceneY() - labelInfo.getLayoutY());
                    labelInfo.setText(""+data.getPieValue()+"$");
                }

            });
        }
    }
    public void loadBarchartInfo()
    {
        for ( XYChart.Data<String, Double> data: series.getData())
        {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{

                if(event.getButton() == MouseButton.SECONDARY)
                {
                    labelInfo.setText("");
                }
                else
                {
                    labelInfo.setTranslateX(event.getSceneX() - labelInfo.getLayoutX());
                    labelInfo.setTranslateY(event.getSceneY() - labelInfo.getLayoutY());
                    labelInfo.toFront();
                    labelInfo.setText("" +data.getYValue()+"$");
                }

            });
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        setTypeCBB();
        typeCBB.setValue("Day");
        barchart_Revenue.setAnimated(false);
        loadTopProduct();
        loadTopSeller();

    }

}
