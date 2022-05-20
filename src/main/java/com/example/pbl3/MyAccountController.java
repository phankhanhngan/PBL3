package com.example.pbl3;

import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.mail.internet.PreencodedMimeBodyPart;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MyAccountController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private MenuItem account;

    @FXML
    private MenuItem logout;

    @FXML
    private MenuItem product;

    @FXML
    private MenuItem supplier;

    @FXML
    private Label nameLabel;

    @FXML
    private PieChart piechart;

    @FXML
    private TextField addressTxt;

    @FXML
    private JFXRadioButton cashierRadioBtn;

    @FXML
    private JFXRadioButton managerRadioBtn;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private Hyperlink changePasswordHyper;

    @FXML
    private TextField gmailTxt;

    @FXML
    private TextField nameTxt;

    private OpenUI openUI = new OpenUI();

    private Integer prdSoldQuantity = 0;
    private Integer prdImportedQuantity = 0;

    private PreparedStatement stmImportPrd = null;
    private PreparedStatement stmSoldPrd = null;
    private static DatabaseConnection  conn = new DatabaseConnection();


    public void DisableField() {
        nameTxt.setEditable(false);
        gmailTxt.setEditable(false);
        phoneTxt.setEditable(false);
        usernameTxt.setEditable(false);
        addressTxt.setEditable(false);
        managerRadioBtn.setDisable(true);
        cashierRadioBtn.setDisable(true);
    }

    public void FillInformation() {
        nameTxt.setText(openUI.namecashier);
        gmailTxt.setText(openUI.gmail);
        phoneTxt.setText(openUI.phonecashier);
        usernameTxt.setText(openUI.username);
        addressTxt.setText(openUI.address);
        if(openUI.typecashier) managerRadioBtn.setSelected(true);
        else cashierRadioBtn.setSelected(true);
        nameLabel.setText(nameTxt.getText() + "!");
    }

    public void changePassword() {
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        stage.close();
        openUI.Open_UI("RecoveryPasswordUI.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DisableField();
        FillInformation();

        changePasswordHyper.setOnAction(e -> {
            changePassword();
        });
        Connection link = conn.getConnection();
        try {
            String queryGetImportedPrd = "select sum(quantity) as quantity from detailimport inner join import " +
                    "on import.import_id = detailimport.import_id where cashier = ? and month(date) = month(CURRENT_DATE());";
            String queryGetSoldPrd = "select sum(Quantity) as quantity  from bill inner join detailbill " +
                    "on bill.ID_Bill = detailbill.ID_Bill where Cashier_Phone = ?;";
            this.stmImportPrd = link.prepareStatement(queryGetImportedPrd);
            this.stmSoldPrd = link.prepareStatement(queryGetSoldPrd);
            stmImportPrd.setString(1,openUI.namecashier);
            stmSoldPrd.setString(1,openUI.phonecashier);
            ResultSet rs = stmImportPrd.executeQuery();
            while(rs.next()) {
                prdImportedQuantity = rs.getInt("quantity");
            }
            rs = null;
            rs = stmSoldPrd.executeQuery();
            while(rs.next()) {
                prdSoldQuantity = rs.getInt("quantity");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList(
                new PieChart.Data("Sold:", prdSoldQuantity),
                new PieChart.Data("Imported:", prdImportedQuantity));
        piechart.setData(piechartData);
        piechartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), "\n", data.pieValueProperty(), " Products"
                        )
                )
        );
    }

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
}
