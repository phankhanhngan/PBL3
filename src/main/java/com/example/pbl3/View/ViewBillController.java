package com.example.pbl3.View;

import com.example.pbl3.BLL.BLLBills;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.DTO.Bill;
import com.example.pbl3.OpenUI;
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
import java.util.ResourceBundle;


public class ViewBillController implements Initializable {
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private MenuItem account;
    @FXML
    JFXButton butSearch;
    @FXML
    TableView<Bill> BillTableView;
    @FXML
    TableColumn<Bill, Integer> Col_IDBill;
    @FXML
    TableColumn<Bill, String> Col_CustomerName;
    @FXML
    TableColumn<Bill, Date> Col_Date;
    @FXML
    TableColumn<Bill, String> Col_CashierName;
    @FXML
    TableColumn<Bill, Double> Col_Total;
    @FXML
    private JFXButton butCreateNewBill;
    @FXML
    private TextField searchTextField;

    private OpenUI openUI = new OpenUI();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        loadTableBill("");
        BillTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !BillTableView.getSelectionModel().isEmpty()) {
                BLLProject.setIDBill(BillTableView.getSelectionModel().getSelectedItem().getID_Bill());
                Stage stage = (Stage) this.BillTableView.getScene().getWindow();
                stage.close();
                openUI.Open_UI("CreateNewBillUI.fxml");
            }
        });
    }

    public void decentralization() {
        if (BLLProject.typecashier == false) {
            account.setVisible(false);
        }
    }

    public void loadTableBill(String txt) {
        ObservableList<Bill> list = FXCollections.observableArrayList(BLLBills.searchBill(txt));
        this.Col_IDBill.setCellValueFactory(new PropertyValueFactory("ID_Bill"));
        this.Col_CustomerName.setCellValueFactory(new PropertyValueFactory("customerName"));
        this.Col_Date.setCellValueFactory(new PropertyValueFactory("date"));
        this.Col_CashierName.setCellValueFactory(new PropertyValueFactory("cashierName"));
        this.Col_Total.setCellValueFactory(new PropertyValueFactory("Total_Money"));
        this.BillTableView.setItems(list);
    }

    @FXML
    void search() {
        loadTableBill(searchTextField.getText());
    }

    @FXML
    void createNewBillOnAction() {
        Stage stage = (Stage) this.butCreateNewBill.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CreateNewBillUI.fxml");
    }

    @FXML
    void deleteRow(KeyEvent event) {
        if (BillTableView.getSelectionModel().getSelectedItem() != null) {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this bill?", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    BLLBills.DeleteBill(BillTableView.getSelectionModel().getSelectedItem().getID_Bill());
                    BillTableView.getSelectionModel().clearSelection();
                    loadTableBill("");
                } else {
                    Notifications.create().text("Please select row!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
                }
            } else {
                Notifications.create().text("Please select row!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        }
    }

    @FXML
    public void productMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ProductUI.fxml");
    }

    @FXML
    public void logOutMenuItemOnAction(ActionEvent event) {
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
    public void importMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ImportUI.fxml");
    }

    @FXML
    public void supplierMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("SupplierUI.fxml");
    }

    @FXML
    public void categoryMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CategoryUI.fxml");
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
    void myAccountMenuItemOnAction(ActionEvent event) {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("MyAccountUI.fxml");
    }
}
