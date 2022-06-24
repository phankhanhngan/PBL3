package com.example.pbl3.View;

import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.BLL.BLLSuppliers;
import com.example.pbl3.DTO.Supplier;
import com.example.pbl3.OpenUI;
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
import java.util.*;

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
    private TextField SupNameTextField;
    @FXML
    private TextField SupAddressTextField;
    @FXML
    private TextField SupPhoneTextField;
    @FXML
            private MenuItem account;

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
        if(BLLProject.typecashier == false)
        {
            account.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
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
        if(!BLLProject.CheckPhone(SupPhoneTextField.getText()))
        {
            Notifications.create().text("Invalid phone number. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            return;
        }
        if(!SupplierTableView.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update the supplier?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                Supplier s = new Supplier(SupplierTableView.getSelectionModel().getSelectedItem().getSup_Id(),SupNameTextField.getText(),
                        SupAddressTextField.getText(),SupPhoneTextField.getText());
                if(BLLSuppliers.UpdateSupplier(s))
                {
                    butResetOnAction();
                    this.loadTable();
                    Notifications.create().text("You have update product successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
                }
                else
                {
                    Notifications.create().text("You have failed update account in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
                }
            }
        }
        else
        {
            Notifications.create().text("Please choice supplier !").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();

        }

    }
    public void butDeleteOnAction()
    {
        Supplier selected = SupplierTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this supplier?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            if(BLLSuppliers.DeleteSupplier(selected.getSup_Id()))
            {
                Notifications.create().text("Successfully .").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
                loadTable();
                butResetOnAction();
            }
            else
            {
                Notifications.create().text("Error!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        }
    }
    public void butAddOnAction()
    {
        if(!BLLProject.CheckPhone(SupPhoneTextField.getText()))
        {
            Notifications.create().text("Invalid phone number. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            return;
        }
        Supplier s = new Supplier(0,SupNameTextField.getText(),SupAddressTextField.getText(),SupPhoneTextField.getText());
        if(BLLSuppliers.AddSupplier(s))
        {
            Notifications.create().text("You have add product successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            butResetOnAction();
            this.loadTable();
        }
        else
        {
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

    public void loadTable()
    {
        ObservableList<Supplier> list = FXCollections.observableArrayList(BLLSuppliers.getListSupplier());
            this.Col_Id.setCellValueFactory(new PropertyValueFactory("Sup_Id"));
            this.Col_Name.setCellValueFactory(new PropertyValueFactory("Sup_Name"));
            this.Col_Address.setCellValueFactory(new PropertyValueFactory("Sup_Address"));
            this.Col_Phone.setCellValueFactory(new PropertyValueFactory("Sup_Phone"));
            this.SupplierTableView.setItems(list);
    }
    public boolean ValidationField()
    {
        return(SupAddressTextField.getText().trim().isEmpty() || SupNameTextField.getText().trim().isEmpty());
    }

}