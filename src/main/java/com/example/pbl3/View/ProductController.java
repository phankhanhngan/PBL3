//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.pbl3.View;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.pbl3.BLL.BLLCategories;
import com.example.pbl3.BLL.BLLProducts;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.DTO.Product;
import com.example.pbl3.OpenUI;
import com.jfoenix.controls.JFXDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

public class ProductController implements Initializable {
    @FXML
    private Label quantityLabel;
    @FXML
    Button butBrowse;
    @FXML
    Label ProductLabel;
    @FXML
    ComboBox<String> cbbCategory;
    @FXML
    TextField txtProductName;
    @FXML
    TextField txtSalePrice;
    @FXML
    TextField txtBarcode;
    @FXML
    TextField txtCategory;
    @FXML
    TextField txtQuantity;
    @FXML
    AnchorPane AnchorPane;
    @FXML
    ImageView ProductImgView;
    @FXML
    TableView<Product> ProductTableView;
    @FXML
    TableColumn<Product, String> Col_Category;
    @FXML
    TableColumn<Product, String> Col_Name;
    @FXML
    TableColumn<Product, Float> Col_SalePrice;
    @FXML
    TableColumn<Product, String> Col_Barcode;
    @FXML
    TableColumn<Product, Integer> Col_Quantity;
    @FXML
    private AnchorPane AnchorPaneProduct;
    @FXML
    private MenuItem menuAdd;

    @FXML
    private MenuItem menuDelete;

    @FXML
    private MenuItem menuDetail;

    @FXML
    private MenuItem menuEdit;

    @FXML
    private StackPane stackPane;

    @FXML
    private Button butCancle;

    @FXML
    private Button butAddOK;
    @FXML
    private Button butUpdateOK;
    @FXML
    private Button butDetailCancle;
    @FXML
    private TextField searchTextField;
    @FXML
    private ComboBox<String> cbbprice;
    @FXML
    private MenuItem account;

    JFXDialog dialog = new JFXDialog();

    OpenUI openUI = new OpenUI();
    private File file;
    private FileChooser fileChooser;
    private Image image;
    private FileInputStream fileinputstream;
    int min = 0, max = 0;


    public void initialize(URL url, ResourceBundle resourceBundle) {

        decentralization();
        AnchorPaneProduct.setVisible(false);
        this.loadTable("");
        this.CompletedCombobox();
        min = max = 0;
        this.butBrowse.setOnAction((e) -> this.OpenImageBrowse());
        menuDetail.setOnAction(e -> {
            if (!ProductTableView.getSelectionModel().isEmpty()) {
                quantityLabel.setVisible(true);
                txtQuantity.setVisible(true);
                ProductLabel.setText("Product detail");
                showDialogDetail();
                SelectedRowAction();
            } else {
                Notifications.create().text("You need to pick a row to see detail. Try again!").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
            }
        });
        menuAdd.setOnAction(e -> {
            quantityLabel.setVisible(false);
            txtQuantity.setVisible(false);
            ProductLabel.setText("Add Product");
            showDialogAdd();
        });
        menuEdit.setOnAction(e -> {
            if (!ProductTableView.getSelectionModel().isEmpty()) {
                quantityLabel.setVisible(false);
                txtQuantity.setVisible(false);
                ProductLabel.setText("Edit Product");
                showDialogUpdate();
                SelectedRowAction();
            } else {
                Notifications.create().text("You need to pick a row to update. Try again!").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
            }

        });
        menuDelete.setOnAction(e -> {
            if (!ProductTableView.getSelectionModel().isEmpty()) {
                Delete();
            } else {
                Notifications.create().text("You need to pick a row to delete. Try again!").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
            }
        });
        butAddOK.setOnAction(e -> AddProduct());
        butUpdateOK.setOnAction(e -> {
            if (!ValidationField()) {
                UpdateProduct();
                loadTable("");
                closeDialog();
            } else {
                Notifications.create().text("Please fill in all fields. Try again!").title("Oh Snap!").
                        hideAfter(Duration.seconds(5.0D)).show();
            }
        });
        butCancle.setOnAction(e -> {
            closeDialog();
            Clear();
        });
        butDetailCancle.setOnAction(e -> {
            closeDialog();
            Clear();
        });

    }

    public void decentralization() {
        if (!BLLProject.typecashier) {
            account.setVisible(false);
        }
    }

    public void showDialog() {
        AnchorPane.setEffect(new BoxBlur(3, 3, 3));
        AnchorPaneProduct.setVisible(true);
        dialog.setOverlayClose(false);
        dialog.setContent(AnchorPaneProduct);
        dialog.setDialogContainer(stackPane);
        dialog.getStyleClass().add("jfx-dialog-overlay-pane");
        dialog.setStyle("-fx-background-color: transparent");
        dialog.show();
    }

    public void closeDialog() {
        dialog.close();
        AnchorPane.setEffect(null);
        AnchorPane.setVisible(true);
        txtBarcode.setEditable(true);
        ProductTableView.getSelectionModel().clearSelection();
    }

    public void showDialogAdd() {
        showDialog();
        butBrowse.setVisible(true);
        cbbCategory.setVisible(true);
        txtCategory.setVisible(false);
        txtProductName.setEditable(true);
        txtSalePrice.setEditable(true);
        txtBarcode.setEditable(true);
        butUpdateOK.setVisible(false);
        butAddOK.setVisible(true);
        butDetailCancle.setVisible(false);
        butCancle.setVisible(true);
    }

    public void showDialogDetail() {
        showDialog();
        butBrowse.setVisible(false);
        cbbCategory.setVisible(false);
        txtCategory.setVisible(true);
        txtProductName.setEditable(false);
        txtSalePrice.setEditable(false);
        txtBarcode.setEditable(false);
        butUpdateOK.setVisible(false);
        butAddOK.setVisible(false);
        butCancle.setVisible(false);
        butDetailCancle.setVisible(true);
    }

    public void showDialogUpdate() {
        showDialog();
        butBrowse.setVisible(true);
        cbbCategory.setVisible(true);
        txtCategory.setVisible(false);
        txtProductName.setEditable(true);
        txtSalePrice.setEditable(true);
        txtBarcode.setEditable(true);
        butUpdateOK.setVisible(true);
        butAddOK.setVisible(false);
        butDetailCancle.setVisible(false);
        butCancle.setVisible(true);
        txtBarcode.setEditable(false);
    }

    public boolean ValidationField() {
        return ((txtBarcode.getText().trim().isEmpty()) || (txtSalePrice.getText().trim().isEmpty())
                || (txtProductName.getText().trim().isEmpty()) ||
                (cbbCategory.getSelectionModel().getSelectedItem() == null));
    }

    public void SelectedRowAction() {
        if ((this.ProductTableView.getSelectionModel().getSelectedItem()).getProductName() != "") {
            this.txtBarcode.setEditable(false);
            this.txtBarcode.setText((this.ProductTableView.getSelectionModel().getSelectedItem()).getSerial());
            this.txtProductName.setText((this.ProductTableView.getSelectionModel().getSelectedItem()).getProductName());
            this.txtSalePrice.setText(Double.toString((this.ProductTableView.getSelectionModel().getSelectedItem()).getSalePrice()));
            this.cbbCategory.setValue(this.ProductTableView.getSelectionModel().getSelectedItem().getCategory());
            this.txtCategory.setText(this.ProductTableView.getSelectionModel().getSelectedItem().getCategory());
            this.txtQuantity.setText(this.ProductTableView.getSelectionModel().getSelectedItem().getQuantity() + "");
            Image image1 = new Image(this.ProductTableView.getSelectionModel().getSelectedItem().getImage());
            this.ProductImgView.setImage(image1);
        }
    }

    public void Clear() {
        loadTable("");
        ProductLabel.setText("Add Product");
        txtProductName.setText("");
        txtSalePrice.setText("");
        txtBarcode.setText("");
        txtBarcode.setEditable(true);
        ProductImgView.setImage(null);
        cbbCategory.getSelectionModel().clearSelection();
        ProductTableView.getSelectionModel().clearSelection();
        searchTextField.setText("");
        this.file = null;
    }

    public void AddProduct() {
        if (!ValidationField() && file != null) {
            if (BLLProject.CheckSerial(txtBarcode.getText())) {
                Notifications.create().text("Serial already exists. Try again!").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
                return;
            }
            try {
                this.fileinputstream = new FileInputStream(this.file);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Product p = new Product(txtBarcode.getText(), txtProductName.getText(), Double.parseDouble(txtSalePrice.getText()),
                    this.fileinputstream, this.cbbCategory.getSelectionModel().getSelectedItem(), 0);
            if (BLLProducts.AddProduct(p)) {
                Notifications.create().text("You have added product successfully into our system.").title("Well-done!")
                        .hideAfter(Duration.seconds(5.0D)).action().show();
                Clear();
                min = max = 0;
            } else {
                Notifications.create().text("You have failed to add product into our System. Try again!").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
            }
            loadTable("");
            closeDialog();
        } else {
            Notifications.create().text("You need to fill in all fields. Try again!").title("Oh Snap!")
                    .hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    public void CompletedCombobox() {
        loadCbbCategory();
        this.cbbprice.setItems(FXCollections.observableArrayList(new String[]{"0-10", "10-50", "50-100", "100-500", "500-5000", "5000 or more", "All"}));
    }

    public void loadCbbCategory() {
        this.cbbCategory.setItems(this.getAllCategory());
    }

    private ObservableList<String> getAllCategory() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i = 0; i < BLLCategories.getListCategory().size(); i++)
            list.add(BLLCategories.getListCategory().get(i).getCate_Name());
        return list;
    }

    public void loadTable(String txt) {
        ObservableList<Product> list = FXCollections.observableArrayList(BLLProducts.searchProduct(txt, min, max));
        this.Col_Barcode.setCellValueFactory(new PropertyValueFactory("serial"));
        this.Col_Name.setCellValueFactory(new PropertyValueFactory("ProductName"));
        this.Col_SalePrice.setCellValueFactory(new PropertyValueFactory("salePrice"));
        this.Col_Category.setCellValueFactory(new PropertyValueFactory("Category"));
        this.Col_Quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        this.ProductTableView.setItems(list);
    }

    public void OpenImageBrowse() {
        this.fileChooser = new FileChooser();
        Stage stage = (Stage) this.AnchorPane.getScene().getWindow();
        this.file = this.fileChooser.showOpenDialog(stage);
        if (this.file != null) {
            this.image = new Image(this.file.toURI().toString(), 248.0D, 196.0D, true, true);
            this.ProductImgView.setImage(this.image);
            this.ProductImgView.fitHeightProperty();
            this.ProductImgView.fitWidthProperty();
            this.ProductImgView.setPreserveRatio(true);
        }
    }

    @FXML
    void searchOnAction() {
        String[] price;
        if (this.cbbprice.getSelectionModel().getSelectedItem() != null) {
            if (this.cbbprice.getSelectionModel().getSelectedItem().contains("or more")) {
                min = 5000;
                max = 0;
            } else if (this.cbbprice.getSelectionModel().getSelectedItem().contains("All")) {
                min = max = 0;
            } else {
                price = this.cbbprice.getSelectionModel().getSelectedItem().split("-");
                min = Integer.parseInt(price[0]);
                max = Integer.parseInt(price[1]);
            }
        }
        loadTable(searchTextField.getText());
        min = max = 0;
    }

    @FXML
    private void UpdateProduct() {
        fileinputstream = null;
        if (file != null) {
            try {
                this.fileinputstream = new FileInputStream(this.file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Product p = this.ProductTableView.getSelectionModel().getSelectedItem();
        Product product = new Product(p.getSerial(), txtProductName.getText(), Double.parseDouble(txtSalePrice.getText()),
                fileinputstream, cbbCategory.getSelectionModel().getSelectedItem(), p.getQuantity());
        if (BLLProducts.UpdateProduct(product)) {
            Clear();
            this.loadTable("");
            Notifications.create().text("You have updated product successfully into our system.").title("Well-done!")
                    .hideAfter(Duration.seconds(5.0D)).action().show();
            min = max = 0;
        } else {
            Notifications.create().text("You have failed to update account into our System. Try again!").title("Oh Snap!")
                    .hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    public void Delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this product?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            if (BLLProducts.DeleteProduct(ProductTableView.getSelectionModel().getSelectedItem().getSerial())) {
                Notifications.create().text("You have deleted this product successfully.").title("Well-done!")
                        .hideAfter(Duration.seconds(5.0D)).action().show();
                loadTable("");
                Clear();
            } else {
                Notifications.create().text("You have failed to delete this product!").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
            }
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