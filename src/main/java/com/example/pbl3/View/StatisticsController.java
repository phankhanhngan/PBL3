package com.example.pbl3.View;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.example.pbl3.BLL.BLLBills;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.DTO.TopProduct;
import com.example.pbl3.DTO.TopSeller;
import com.example.pbl3.OpenUI;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private PieChart PieChart;
    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private BarChart<String, Double> barchart_Revenue;

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
    private Label pieLabel;
    @FXML
    private Label chartLabel;
    @FXML
    private MenuItem account;
    OpenUI openUI = new OpenUI();

    XYChart.Series<String, Double> series = new XYChart.Series<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        setTypeCBB();
        typeCBB.setValue("Day");
        barchart_Revenue.setAnimated(false);
        loadTopProduct();
        loadTopSeller();
    }

    public void decentralization() {
        if (!BLLProject.typecashier) {
            account.setVisible(false);
        }
    }

    public void loadTopProduct() {
        ObservableList<TopProduct> listTopProduct = FXCollections.observableArrayList(BLLBills.loadTopProduct().topProductList);
        col_ProductNo.setCellValueFactory(new PropertyValueFactory("No"));
        col_ProductName.setCellValueFactory(new PropertyValueFactory("ProductName"));
        col_ProductQty.setCellValueFactory(new PropertyValueFactory("quantity"));
        tableViewTopProduct.setItems(listTopProduct);
    }

    public void loadTopSeller() {
        ObservableList<TopSeller> listTopSeller = FXCollections.observableArrayList(BLLBills.loadTopSeller().topSellerList);
        col_SellerNo.setCellValueFactory(new PropertyValueFactory("no"));
        col_SellerName.setCellValueFactory(new PropertyValueFactory("seller_Name"));
        col_SellerRevenue.setCellValueFactory(new PropertyValueFactory("revenue"));
        tableViewTopSeller.setItems(listTopSeller);
    }

    private void loadPieChart(String txtStart, String txtEnd) {
        List<String> listCategory = BLLBills.GetListCategory(txtStart, txtEnd);
        List<Double> listSales = BLLBills.GetListSales(txtStart, txtEnd);

        PieChart.getData().clear();
        for (int i = 0; i < listCategory.size(); i++) {
            PieChart.getData().add(new PieChart.Data(listCategory.get(i), listSales.get(i)));
        }
    }

    private void loadBarChart_Revenue(String txtStart, String txtEnd, boolean type) {
        series.getData().clear();
        series.setName("Revenue");

        int QuantityBill = BLLBills.GetQuantity(txtStart, txtEnd, type);
        double Renevue = 0;
        barchart_Revenue.getData().clear();
        for (int i = 0; i < BLLBills.GetListSalesBarchart(txtStart, txtEnd, type).size(); i++) {
            series.getData().add(new XYChart.Data(BLLBills.GetListDate(txtStart, txtEnd, type).get(i), BLLBills.GetListSalesBarchart(txtStart, txtEnd, type).get(i)));
            Renevue += BLLBills.GetListSalesBarchart(txtStart, txtEnd, type).get(i);
        }
        barchart_Revenue.getData().add(series);
        txtOrders.setText(QuantityBill + "");
        txtRenevue.setText("" + Renevue);
    }

    @FXML
    void startDateOnAction() {
        if (typeCBB.getSelectionModel().getSelectedItem().equals("Month")) {
            LocalDate Selected_datestart = start_date.getValue();
            LocalDate date_start = Selected_datestart.withDayOfMonth(1);
            start_date.setValue(date_start);
        }
    }

    @FXML
    void endDateOnAction() {
        Date endDate = Date.valueOf(end_date.getValue());
        Date startDate = Date.valueOf(start_date.getValue());
        if (endDate.compareTo(startDate) < 0) {
            Notifications.create().text("You need to choose valid date. Try again!").title("Oh Snap!")
                    .hideAfter(Duration.seconds(5.0D)).show();
            return;
        }
        if (typeCBB.getSelectionModel().getSelectedItem().equals("Day")) {
            pieLabel.setText("Doanh thu theo danh m???c s???n ph???m");
            chartLabel.setText("Doanh thu theo ng??y");
            long day = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
            if (day <= 31) {
                loadBarChart_Revenue(startDate.toString(), endDate.toString(), true);
                loadPieChart(startDate.toString(), endDate.toString());

            } else {
                Notifications.create().text("You need to choose valid date (<= 31 days). Try again!").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
                return;
            }
        }
        if (typeCBB.getSelectionModel().getSelectedItem().equals("Month")) {
            pieLabel.setText("Doanh thu theo danh m???c s???n ph???m");
            chartLabel.setText("Doanh thu theo th??ng");
            LocalDate Selected_dateend = end_date.getValue();
            LocalDate date_end = Selected_dateend.withDayOfMonth(Selected_dateend.lengthOfMonth());
            end_date.setValue(date_end);
            loadBarChart_Revenue(startDate.toString(), endDate.toString(), false);
            loadPieChart(startDate.toString(), endDate.toString());
        }
    }

    public void setTypeCBB() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Day");
        list.add("Month");
        typeCBB.setItems(list);
        start_date.setValue(LocalDate.now());
    }

    public void loadPiechartInfo() {
        for (PieChart.Data data : PieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    labelInfo.setText("");
                } else {
                    labelInfo.setTranslateX(event.getSceneX() - labelInfo.getLayoutX());
                    labelInfo.setTranslateY(event.getSceneY() - labelInfo.getLayoutY());
                    labelInfo.setText("" + data.getPieValue() + "$");
                }
            });
        }
    }

    public void loadBarchartInfo() {
        for (XYChart.Data<String, Double> data : series.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

                if (event.getButton() == MouseButton.SECONDARY) {
                    labelInfo.setText("");
                } else {
                    labelInfo.setTranslateX(event.getSceneX() - labelInfo.getLayoutX());
                    labelInfo.setTranslateY(event.getSceneY() - labelInfo.getLayoutY());
                    labelInfo.toFront();
                    labelInfo.setText("" + data.getYValue() + "$");
                }
            });
        }
    }

    @FXML
    public void productMenuItemOnAction() {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ProductUI.fxml");
    }

    @FXML
    public void logOutMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("LoginUI.fxml");
    }

    @FXML
    public void accountMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("AccountUI.fxml");
    }

    @FXML
    public void importMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ImportUI.fxml");
    }

    @FXML
    public void supplierMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("SupplierUI.fxml");
    }

    @FXML
    public void categoryMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CategoryUI.fxml");
    }

    @FXML
    public void customerMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CustomerUI.fxml");
    }

    @FXML
    public void orderMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CreateNewBillUI.fxml");
    }

    @FXML
    void billMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ViewBillUI.fxml");
    }

    @FXML
    public void homePageMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("HomePageUI.fxml");
    }

    @FXML
    public void statisticMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("StatisticsUI.fxml");
    }

    @FXML
    void myAccountMenuItemOnAction() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("MyAccountUI.fxml");
    }
}
