package com.example.pbl3.View;

import com.example.pbl3.BLL.BLLCategories;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.DTO.Category;
import com.example.pbl3.OpenUI;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

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
    private TextField txtSearch;
    @FXML
    private JFXButton buttonSearch;
    @FXML
    private MenuItem account;

    OpenUI openUI = new OpenUI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        LabelCategory.setText("Add Category");
        this.loadTable("");
        this.resetButton.setOnAction(e -> this.butResetOnAction());
        this.addButton.setOnAction(e -> {
            if (!ValidationField()) {
                this.butAddOnAction();
            } else {
                Notifications.create().text("Please fill in all fields.").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).action().show();
            }
        });
        this.deleteButton.setOnAction(e -> this.butDeleteOnAction());
        this.updateButton.setOnAction(e -> this.butUpdateOnAction());
        this.CategoryTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.SelectedRowAction();
            }
        });
        this.buttonSearch.setOnAction(e -> loadTable(txtSearch.getText()));
    }

    /////////Phan quyen
    public void decentralization() {
        if (!BLLProject.typecashier) {
            account.setVisible(false);
        }
    }

    public void butResetOnAction() {
        addButton.setDisable(false);
        LabelCategory.setText("Add Category");
        CateNameTextField.setText("");
        this.CategoryTableView.getSelectionModel().clearSelection();
    }

    public void loadTable(String txt) {
        ObservableList<Category> list = FXCollections.observableArrayList(BLLCategories.SearchCategory(txt));
        this.Col_Id.setCellValueFactory(new PropertyValueFactory("Cate_Id"));
        this.Col_Name.setCellValueFactory(new PropertyValueFactory("Cate_Name"));
        this.CategoryTableView.setItems(list);
    }

    public boolean ValidationField() {
        return (CateNameTextField.getText().trim().isEmpty());
    }

    //Hien thi chi tiet danh muc
    public void SelectedRowAction() {
        if (!(this.CategoryTableView.getSelectionModel().getSelectedItem()).getCate_Name().equals("")) {
            addButton.setDisable(true);
            LabelCategory.setText("Category Details");
            this.CateNameTextField.setText((this.CategoryTableView.getSelectionModel().getSelectedItem()).getCate_Name());

        }
    }

    ////Update
    public void butUpdateOnAction() {
        if (CategoryTableView.getSelectionModel().isEmpty()) {
            Notifications.create().text("Please select a category to update").title("Oh Snap!")
                    .hideAfter(Duration.seconds(5.0D)).show();
        } else {
            Category c = new Category(CategoryTableView.getSelectionModel().getSelectedItem().getCate_Id(), CateNameTextField.getText());
            if (BLLCategories.UpdateCategory(c)) {
                butResetOnAction();
                this.loadTable("");
                Notifications.create().text("You have updated category successfully into our system.").title("Well-done!")
                        .hideAfter(Duration.seconds(5.0D)).action().show();
            } else {
                Notifications.create().text("You have failed to update category into our System. Try again!").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
            }
        }
    }

    //Delete
    public void butDeleteOnAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this category?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            if (BLLCategories.DeleteCategory(CategoryTableView.getSelectionModel().getSelectedItem().getCate_Id())) {
                Notifications.create().text("You have deleted this category successfully.").title("Well-done!")
                        .hideAfter(Duration.seconds(5.0D)).action().show();
                loadTable("");
                butResetOnAction();
            } else {
                Notifications.create().text("You have failed to delete this category!").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
            }
        }
    }

    /////Add
    public void butAddOnAction() {
        Category c = new Category(0, CateNameTextField.getText());
        if (BLLCategories.AddCategory(c)) {
            Notifications.create().text("You have added category successfully into our system.").title("Well-done!")
                    .hideAfter(Duration.seconds(5.0D)).action().show();
            butResetOnAction();
            this.loadTable("");
        } else {
            Notifications.create().text("You have failed to add category into our System. Try again!").title("Oh Snap!")
                    .hideAfter(Duration.seconds(5.0D)).show();
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