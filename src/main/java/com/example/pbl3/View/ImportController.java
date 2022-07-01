package com.example.pbl3.View;

import com.example.pbl3.BLL.BLLImports;
import com.example.pbl3.BLL.BLLProducts;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.BLL.BLLSuppliers;
import com.example.pbl3.DTO.AutoCompleteBox;
import com.example.pbl3.DTO.Import;
import com.example.pbl3.DTO.Product;
import com.example.pbl3.DTO.Supplier;
import com.example.pbl3.DTO.DetailImport;
import com.example.pbl3.OpenUI;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import com.jfoenix.controls.JFXDialog;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ImportController implements Initializable {
    @FXML
    private MenuItem Deletemenu;
    @FXML
    private MenuItem deleteDetailContextMenu;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private AnchorPane receiptAnchorpane;
    @FXML
    private AnchorPane printAnchorPane;
    @FXML
    private StackPane StackPane;
    @FXML
    private Button makeReceiptButton;
    @FXML
    private Button cancelButton;
    @FXML
    private MenuItem account;
    @FXML
    private MenuItem statistics;
    @FXML
    private Button addSubmitButton;
    @FXML
    private Button printReceiptButton;
    @FXML
    private Hyperlink newSupplierHyper;
    @FXML
    private Hyperlink newItemHyper;
    @FXML
    private ComboBox itemCBBox;
    @FXML
    private ComboBox supplierCBBox;
    @FXML
    private TextField quantityTxt;
    @FXML
    private DatePicker importDate;
    @FXML
    private TextField searchtxt;
    @FXML
    private TextField supplierPhoneTxt;
    @FXML
    private TextField supplierAddressTxt;
    @FXML
    private TextField unitPriceTxt;
    @FXML
    private Label lbTotal;
    @FXML
    private Label lbSupplier;
    @FXML
    private Label lbStaff;
    @FXML
    private TableView<Import> ImportTableView;

    @FXML
    private TableColumn<Import, String> Col_supplier;
    @FXML
    private TableColumn<Import, String> Col_cashier;
    @FXML
    private TableColumn<Import, String> Col_importID;
    @FXML
    private TableColumn<Import, Date> Col_importDate;
    @FXML
    private TableColumn<Import, Date> Col_total;


    @FXML
    private TableView<DetailImport> DetailImportTableView;
    @FXML
    private TableColumn<DetailImport, Integer> Col_id;
    @FXML
    private TableColumn<DetailImport, Integer> Col_productName;
    @FXML
    private TableColumn<DetailImport, String> Col_quantity;
    @FXML
    private TableColumn<DetailImport, String> Col_unitPrice;
    @FXML
    private TableColumn<DetailImport, String> Col_amount;

    @FXML
    private TableView<DetailImport> detailTablePrint;
    @FXML
    private TableColumn<DetailImport, Integer> Col_printProduct;
    @FXML
    private TableColumn<DetailImport, String> Col_printQuantity;
    @FXML
    private TableColumn<DetailImport, String> Col_printUnit;
    @FXML
    private TableColumn<DetailImport, String> Col_printAmount;
    @FXML
    private TableColumn<DetailImport, Integer> Col_printID;


    @FXML
    private Button addItemButton;
    @FXML
    private  Menu statistics_parent;

    @FXML
    private Label supplierNamePrint;
    @FXML
    private Label supplierPhonePrint;
    @FXML
    private Label supplierAddressPrint;
    @FXML
    private Label receiptIDPrint;
    @FXML
    private Label receiptDatePrint;
    @FXML
    private Label totalPrint;
    @FXML
    private Label supplierPrint;
    @FXML
    private Label staffPrint;

    JFXDialog dialog = new JFXDialog();
    private Double totalCal = (double) 0;

    private ObservableList<Import> importList;
    private ObservableList<DetailImport> importDetailList = FXCollections.observableArrayList();
    OpenUI openUI = new OpenUI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        this.lbStaff.setText(BLLProject.namecashier);
        receiptAnchorpane.setVisible(false);
        printAnchorPane.setVisible(false);
        this.makeReceiptButton.setOnAction((e) -> ShowDialogReceipt());
        this.cancelButton.setOnAction((e) -> {
            resetInputField();
            CloseDialogReceipt();
            importDetailList.clear();
        });
        this.addSubmitButton.setOnAction((e) -> {
            try {
                addSubmitButtonOnAction();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        this.newItemHyper.setOnAction((e) -> productMenuItemOnAction());
        this.newSupplierHyper.setOnAction((e) -> supplierMenuItemOnAction());
        this.printReceiptButton.setOnAction((e) -> {
            printReceipt();
            AnchorPane.setEffect(null);
            receiptAnchorpane.setEffect(null);
            importDetailList.clear();
            resetInputField();
        });
        this.addItemButton.setOnAction((e) -> addAnItem());
        UpdateListImport("");

        showSupplierComboBoxItem();
        showItemComboboxItem();
        new AutoCompleteBox(itemCBBox);
        new AutoCompleteBox(supplierCBBox);
        supplierCBBox.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
            if (supplierCBBox.getSelectionModel().getSelectedItem() != null) {
                fillSupplierInfo();
                lbSupplier.setText(supplierCBBox.getSelectionModel().getSelectedItem().toString());
            }
        });

        ImportTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !ImportTableView.getSelectionModel().isEmpty()) {
                ShowDialogReceipt();
                loadReceipt();
            }
        });

        showContextMenu();
        deleteDetailContextMenu.setOnAction((e) -> deleteAnItem());
    }

    public void decentralization() {
        if (!BLLProject.typecashier) {
            account.setVisible(false);
            statistics_parent.setVisible(false);
        }
    }

    private void showSupplierComboBoxItem() {
        this.supplierCBBox.setItems(this.getAllSupplierName());
    }

    private void showItemComboboxItem() {
        this.itemCBBox.setItems(this.getAllProductName());
    }

    private void fillSupplierInfo() {
        supplierPhoneTxt.setText(BLLSuppliers.getSupplierByNameSupplier(supplierCBBox.getSelectionModel().getSelectedItem().toString()).getSup_Phone());
        supplierAddressTxt.setText(BLLSuppliers.getSupplierByNameSupplier(supplierCBBox.getSelectionModel().getSelectedItem().toString()).getSup_Address());
        supplierPhonePrint.setText(BLLSuppliers.getSupplierByNameSupplier(supplierCBBox.getSelectionModel().getSelectedItem().toString()).getSup_Phone());
        supplierAddressPrint.setText(BLLSuppliers.getSupplierByNameSupplier(supplierCBBox.getSelectionModel().getSelectedItem().toString()).getSup_Address());
    }

    private void loadReceipt() {
        this.supplierCBBox.setValue(this.ImportTableView.getSelectionModel().getSelectedItem().getSupplier_name());
        fillSupplierInfo();
        Date date = this.ImportTableView.getSelectionModel().getSelectedItem().getImport_date();
        this.importDate.setValue(LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate()));
        receiptDatePrint.setText(date.toString());
        this.lbSupplier.setText(this.ImportTableView.getSelectionModel().getSelectedItem().getSupplier_name());
        this.lbTotal.setText(String.valueOf(this.ImportTableView.getSelectionModel().getSelectedItem().getTotal()));
        List<DetailImport> listDetailImport = BLLImports.getListDetailImportByIDImport(ImportTableView.getSelectionModel()
                .getSelectedItem().getImport_id());
        importDetailList.clear();
        for (DetailImport detailImport : listDetailImport) {
            importDetailList.add(detailImport);
        }
        loadDetailImportTable(importDetailList);
        deleteDetailContextMenu.setDisable(true);
        printReceiptButton.setVisible(true);
        addSubmitButton.setVisible(false);
        supplierCBBox.setDisable(true);
        itemCBBox.setDisable(true);
        quantityTxt.setEditable(true);
        unitPriceTxt.setEditable(false);
        importDate.setDisable(true);
        addItemButton.setDisable(true);
    }

    public boolean isInputFieldEmpty() {
        return this.supplierCBBox.getSelectionModel().getSelectedItem() == null
                || importDate.getValue() == null || DetailImportTableView.getItems().size() == 0;
    }

    public void resetInputField() {
        lbSupplier.setText("");
        supplierCBBox.getSelectionModel().clearSelection();
        lbTotal.setText("0");
        supplierAddressTxt.setText("");
        supplierPhoneTxt.setText("");
        ImportTableView.getSelectionModel().clearSelection();
        importDate.setValue(null);
    }

    public void resetDetailInputField() {
        quantityTxt.setText("");
        unitPriceTxt.setText("");
        itemCBBox.getSelectionModel().clearSelection();
    }

    @FXML
    public List<Import> Search(String txt) {
        return BLLImports.searchImport(txt);
    }

    private void UpdateListImport(String s) {
        importList = FXCollections.observableArrayList(Search(s));
        Col_supplier.setCellValueFactory(new PropertyValueFactory("supplier_name"));
        Col_importID.setCellValueFactory(new PropertyValueFactory("import_id"));
        Col_importDate.setCellValueFactory(new PropertyValueFactory("import_date"));
        Col_total.setCellValueFactory(new PropertyValueFactory("total"));
        Col_cashier.setCellValueFactory(new PropertyValueFactory("cashier"));
        ImportTableView.setItems(importList);
    }

    @FXML
    public void searchOnAction() {
        UpdateListImport(searchtxt.getText());
    }

    public void addSubmitButtonOnAction() throws SQLException {

        if (!supplierCBBox.getSelectionModel().isEmpty() && DetailImportTableView.getItems().size() > 0
                && importDate.getValue() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This receipt can not be edited. " +
                    "Add these products to inventory? ", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                this.addImport();
                this.addDetailImport();
                importDetailList.clear();
                UpdateListImport("");
                CloseDialogReceipt();
                resetInputField();
                resetDetailInputField();
            }
        } else {
            Notifications.create().text("Please fill in all fields.").title("Oh Snap!")
                    .hideAfter(Duration.seconds(5.0D)).action().show();
        }
    }

    public void addImport() {
        if (!isInputFieldEmpty()) {
            Import i = new Import(0, BLLSuppliers.getSupplierByNameSupplier((String) supplierCBBox.getValue()).getSup_Id(),
                    (String) supplierCBBox.getValue(), Date.valueOf(importDate.getValue()), Double.parseDouble(lbTotal.getText()),
                    lbStaff.getText(), BLLProject.gmail);
            if (BLLImports.AddImport(i)) {
                Notifications.create().text("You have added products into inventory successfully!")
                        .title("Well-done!")
                        .hideAfter(Duration.seconds(5.0D)).action().show();
            } else {
                Notifications.create().text("You have failed to add products into inventory!")
                        .title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).action().show();
            }
        } else {
            Notifications.create().text("Please fill in all fields!")
                    .title("Notification")
                    .hideAfter(Duration.seconds(5.0D)).action().show();
        }
    }

    public void addDetailImport() {
        importDetailList.forEach(DetailImport -> {
            DetailImport detailImport = new DetailImport(BLLImports.getMaxImportID(), DetailImport.getProduct(),
                    BLLProducts.getProductByProductName(DetailImport.getProduct()).getSerial(), DetailImport.getQuantity(),
                    DetailImport.getUnit_price(), DetailImport.getAmount());
            BLLImports.AddDetailImport(detailImport);
        });
        importDetailList.clear();
    }

    public void deleteButtonOnAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete these products out of inventory?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            if (!ImportTableView.getSelectionModel().isEmpty()) {
                if (BLLImports.DeleteImport(ImportTableView.getSelectionModel().getSelectedItem().getImport_id())) {
                    Notifications.create().text("You have deleted this receipt successfully.").title("Well-done!")
                            .hideAfter(Duration.seconds(5.0D)).action().show();
                    UpdateListImport("");
                    resetInputField();
                } else {
                    Notifications.create().text("You have failed to delete this receipt!").title("Oh Snap!")
                            .hideAfter(Duration.seconds(5.0D)).show();

                }
            } else {
                Notifications.create().text("Please pick a row to delete.").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).action().show();
            }
        }
    }

    public void ShowDialogReceipt() {
        unitPriceTxt.setEditable(true);
        supplierCBBox.setDisable(false);
        itemCBBox.setDisable(false);
        importDate.setDisable(false);
        addItemButton.setDisable(false);
        AnchorPane.setEffect(new BoxBlur(3, 3, 3));
        receiptAnchorpane.setVisible(true);
        dialog.setOverlayClose(false);
        dialog.setContent(receiptAnchorpane);
        dialog.setDialogContainer(StackPane);
        dialog.getStyleClass().add("jfx-dialog-overlay-pane");
        dialog.setStyle("-fx-background-color: transparent");
        printReceiptButton.setVisible(false);
        addSubmitButton.setVisible(true);
        dialog.show();
    }


    public void CloseDialogReceipt() {
        dialog.close();
        AnchorPane.setEffect(null);
        AnchorPane.setVisible(true);
        ImportTableView.getSelectionModel().clearSelection();
    }

    public void showContextMenu() {
        Deletemenu.setOnAction(e -> this.deleteButtonOnAction());
    }

    //confirm dialog
    private void deleteAnItem() {
        if (!DetailImportTableView.getSelectionModel().isEmpty()) {
            double total_old = Double.parseDouble(lbTotal.getText());
            lbTotal.setText((total_old - DetailImportTableView.getSelectionModel().getSelectedItem().getAmount()) + "");
            importDetailList.remove(DetailImportTableView.getSelectionModel().getSelectedItem());
//            importDetailList.forEach(DetailImport -> {
//                if (DetailImportTableView.getSelectionModel().getSelectedItem().getImportID() == DetailImport.getImportID()) {
//                    importDetailList.remove(DetailImport);
//                    return;
//                }
//            });
        }
    }

    public ObservableList<String> getAllSupplierName() {
        ObservableList<String> list = FXCollections.observableArrayList();
        List<Supplier> supplierList = BLLSuppliers.getListSupplier();
        for (Supplier supplier : supplierList) list.add(supplier.getSup_Name());
        return list;
    }

    public ObservableList<String> getAllProductName() {
        ObservableList<String> list = FXCollections.observableArrayList();
        List<Product> productList = BLLProducts.getListProduct();
        for (Product product : productList) list.add(product.getProductName());
        return list;
    }

    private void addAnItem() {
        if (!itemCBBox.getSelectionModel().isEmpty() && !quantityTxt.getText().isEmpty() && !unitPriceTxt.getText().isEmpty()) {
            DetailImport di = new DetailImport(0, itemCBBox.getSelectionModel().getSelectedItem().toString(),
                    BLLProducts.getProductByProductName(itemCBBox.getSelectionModel().getSelectedItem().toString()).getSerial(),
                    Integer.parseInt(quantityTxt.getText()), Double.parseDouble(unitPriceTxt.getText()),
                    Double.parseDouble(quantityTxt.getText()) * Double.parseDouble(unitPriceTxt.getText()));
            importDetailList.add(di);
            totalCal += Double.parseDouble(quantityTxt.getText()) * Double.parseDouble(unitPriceTxt.getText());
            lbTotal.setText(totalCal.toString());
            loadDetailImportTable(importDetailList);
            resetDetailInputField();
        } else {
            Notifications.create().text("Add failure! Please fill in all fields.")
                    .title("Notification").hideAfter(Duration.seconds(5.0D)).action().show();
            ;
        }
    }

    private void loadDetailImportTable(ObservableList<DetailImport> importDetailList) {
        Col_id.setCellValueFactory(new PropertyValueFactory("product_id"));
        Col_productName.setCellValueFactory(new PropertyValueFactory("product"));
        Col_quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        Col_unitPrice.setCellValueFactory(new PropertyValueFactory("unit_price"));
        Col_amount.setCellValueFactory(new PropertyValueFactory("amount"));
        DetailImportTableView.setItems(importDetailList);

        Col_printID.setCellValueFactory(new PropertyValueFactory("product_id"));
        Col_printProduct.setCellValueFactory(new PropertyValueFactory("product"));
        Col_printQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        Col_printUnit.setCellValueFactory(new PropertyValueFactory("unit_price"));
        Col_printAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        detailTablePrint.setItems(importDetailList);
    }

    public void loadPrintReceipt() {
        receiptIDPrint.setText(String.valueOf(ImportTableView.getSelectionModel().getSelectedItem().getImport_id()));
        supplierNamePrint.setText(this.ImportTableView.getSelectionModel().getSelectedItem().getSupplier_name());
        supplierPrint.setText(supplierNamePrint.getText());
        staffPrint.setText(BLLProject.namecashier);
        totalPrint.setText(String.valueOf(this.ImportTableView.getSelectionModel().getSelectedItem().getTotal()));
    }

    private void printReceipt() {
        loadPrintReceipt();
        AnchorPane.setEffect(new BoxBlur(3, 3, 3));
        receiptAnchorpane.setEffect(new BoxBlur(3, 3, 3));
        printAnchorPane.setVisible(true);
        BLLProject.SetUpPrintJob(dialog, printAnchorPane, StackPane);
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