package com.example.pbl3.View;

import com.example.pbl3.BLL.BLLCategories;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.DTO.Category;
import com.example.pbl3.OpenUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.ResourceBundle;

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
        if ((this.CategoryTableView.getSelectionModel().getSelectedItem()).getCate_Name() != "") {
            addButton.setDisable(true);
            LabelCategory.setText("Category Details");
            this.CateNameTextField.setText((this.CategoryTableView.getSelectionModel().getSelectedItem()).getCate_Name());

        }
    }
    public void butUpdateOnAction()
    {
        if(CategoryTableView.getSelectionModel().isEmpty())
        {
            Notifications.create().text("Please choice Category").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();

        }
        else
        {
            Category c = new Category(CategoryTableView.getSelectionModel().getSelectedItem().getCate_Id(),CateNameTextField.getText());
            if(BLLCategories.UpdateCategory(c))
            {
                butResetOnAction();
                this.loadTable();
                Notifications.create().text("You have update category successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            }
            else
            {
                Notifications.create().text("You have failed update category in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        }
    }
    public void butDeleteOnAction()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this category?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            if(BLLCategories.DeleteCategory(CategoryTableView.getSelectionModel().getSelectedItem().getCate_Id()))
            {
                Notifications.create().text("Successfully.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
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
        Category c = new Category(0,CateNameTextField.getText());
        if(BLLCategories.AddCategory(c))
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
        LabelCategory.setText("Add Category");
        CateNameTextField.setText("");
        this.CategoryTableView.getSelectionModel().clearSelection();
    }
    public void loadTable()
    {
        ObservableList<Category> list = FXCollections.observableArrayList(BLLCategories.getListCategory());
        this.Col_Id.setCellValueFactory(new PropertyValueFactory("Cate_Id"));
        this.Col_Name.setCellValueFactory(new PropertyValueFactory("Cate_Name"));
        this.CategoryTableView.setItems(list);
    }
    public boolean ValidationField()
    {
        return( CateNameTextField.getText().trim().isEmpty());
    }

}