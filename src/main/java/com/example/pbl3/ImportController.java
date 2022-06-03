package com.example.pbl3;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import com.jfoenix.controls.JFXDialog;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportController implements Initializable {
    @FXML
    private ContextMenu inventoryContextMenu;
    @FXML
    private MenuItem Addmenu;
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
    private MenuItem homepage;
    @FXML
    private MenuItem account;
    @FXML
    private MenuItem product;
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem importPrd;
    @FXML
    private  Button addSubmitButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button printReceiptButton;
    @FXML
    private Hyperlink newSupplierHyper;
    @FXML
    private Hyperlink newItemHyper;
    @FXML
    private  Button search;
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
    private TableColumn<Import,String> Col_cashier;
    @FXML
    private TableColumn<Import, String> Col_importID;
    @FXML
    private TableColumn<Import, Date> Col_importDate;
    @FXML
    private TableColumn<Import, Date> Col_total;



    @FXML
    private TableView<DetailImport> DetailImportTableView;
    @FXML
    private TableColumn<DetailImport,Integer> Col_id;
    @FXML
    private TableColumn<DetailImport,Integer> Col_productName;
    @FXML
    private TableColumn<DetailImport,String> Col_quantity;
    @FXML
    private TableColumn<DetailImport,String> Col_unitPrice;
    @FXML
    private TableColumn<DetailImport,String> Col_amount;

    @FXML
    private TableView<DetailImport> detailTablePrint;
    @FXML
    private TableColumn<DetailImport,Integer> Col_printProduct;
    @FXML
    private TableColumn<DetailImport,String> Col_printQuantity;
    @FXML
    private TableColumn<DetailImport,String> Col_printUnit;
    @FXML
    private TableColumn<DetailImport,String> Col_printAmount;
    @FXML
    private TableColumn<DetailImport,Integer> Col_printID;


    @FXML
    private Button addItemButton;

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
    private Double totalCal = Double.valueOf(0);

    private PreparedStatement update = null;
    private PreparedStatement add = null;
    private PreparedStatement delete = null;
    private PreparedStatement loadSupplierInfo = null;
    private PreparedStatement addDetail = null;
    private PreparedStatement getImportID = null;
    private PreparedStatement loadDetailImport = null;
    private PreparedStatement deleteDetailImport = null;

    private ObservableList<Import> importList;
    private ObservableList<DetailImport> importDetailList = FXCollections.observableArrayList();
    private ObservableList<Double> amountList = FXCollections.observableArrayList();
    OpenUI openUI = new OpenUI();
    private static DatabaseConnection  conn = new DatabaseConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        this.lbStaff.setText(openUI.namecashier);
        receiptAnchorpane.setVisible(false);
        printAnchorPane.setVisible(false);
        this.makeReceiptButton.setOnAction((e) -> {
            ShowDialogReceipt();
        });
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
        this.newItemHyper.setOnAction((e) -> {
            productMenuItemOnAction(e);
        });
        this.newSupplierHyper.setOnAction((e) -> {
            supplierMenuItemOnAction(e);
        });
        this.printReceiptButton.setOnAction((e) -> {
            printReceipt();
            AnchorPane.setEffect(null);
            receiptAnchorpane.setEffect(null);
            importDetailList.clear();
            resetInputField();
        });
        this.addItemButton.setOnAction((e) -> {
            addAnItem();
        });
        try {
            UpdateListImport("");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        showSupplierComboBoxItem();
        showItemComboboxItem();
        new AutoCompleteBox(itemCBBox);
        new AutoCompleteBox(supplierCBBox);
        supplierCBBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (supplierCBBox.getSelectionModel().getSelectedItem() != null) {
                    fillSupplierInfo();
                    lbSupplier.setText(supplierCBBox.getSelectionModel().getSelectedItem().toString());
                }
            }
        });



        ImportTableView.setOnMouseClicked( event -> {
            if( event.getClickCount() == 2 && !ImportTableView.getSelectionModel().isEmpty()) {
                ShowDialogReceipt();
                loadReceipt();
            }});


        //
        showContextMenu();
        deleteDetailContextMenu.setOnAction((e) -> {
            deleteAnItem();
        });
        //


        Connection link = conn.getConnection();
        try {
            String queryAdd = "Insert into import(supplier, date , cashier, total) values (?, ?, ?, ?)";
            String queryDelete = "Delete from import where import_id = ?";
            String queryLoadSupplier = "Select * from supplier where supplier_name = ?";
            String queryAddDetail = "Insert into detailimport(import_id, quantity, product, unit_price, amount) values (?,?,?,?,?)";
            String queryLoadDetailImport = "Select * from detailimport where import_id = ?";
            String queryDeleteDetailImport = "delete from detailimport where import_id = ?";
            String queryImportID = "select * from import ORDER BY import_id DESC LIMIT 1";
            this.add = link.prepareStatement(queryAdd);
            this.delete = link.prepareStatement(queryDelete);
            this.loadSupplierInfo = link.prepareStatement(queryLoadSupplier);
            this.addDetail = link.prepareStatement(queryAddDetail);
            this.loadDetailImport = link.prepareStatement(queryLoadDetailImport);
            this.getImportID = link.prepareStatement(queryImportID);
            this.deleteDetailImport = link.prepareStatement(queryDeleteDetailImport);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }



    private void showSupplierComboBoxItem() {
        this.supplierCBBox.setItems(this.getAllSupplierName());
    }

    private void showItemComboboxItem() {
        this.itemCBBox.setItems(this.getAllProductName());
    }

    private void fillSupplierInfo() {
        try {
            this.loadSupplierInfo.setString(1,supplierCBBox.getValue().toString());
            ResultSet rs = this.loadSupplierInfo.executeQuery();
            while(rs.next()) {
                supplierPhoneTxt.setText(rs.getString("Supplier_Phone"));
                supplierAddressTxt.setText(rs.getString("Supplier_Address"));
                supplierPhonePrint.setText(rs.getString("Supplier_Phone"));
                supplierAddressPrint.setText(rs.getString("Supplier_Address"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadReceipt() {
        this.supplierCBBox.setValue(this.ImportTableView.getSelectionModel().getSelectedItem().getSupplier_name());
        fillSupplierInfo();
        java.util.Date date = this.ImportTableView.getSelectionModel().getSelectedItem().getImport_date();
        this.importDate.setValue(LocalDate.of(date.getYear()+1900, date.getMonth()+1, date.getDate()));
        receiptDatePrint.setText(date.toString());
        this.lbSupplier.setText(this.ImportTableView.getSelectionModel().getSelectedItem().getSupplier_name());
        this.lbTotal.setText(String.valueOf(this.ImportTableView.getSelectionModel().getSelectedItem().getTotal()));
        try {
            this.loadDetailImport.setInt(1,ImportTableView.getSelectionModel().getSelectedItem().getImport_id());
            ResultSet rs = loadDetailImport.executeQuery();
            while (rs.next())
            {
                importDetailList.add(new DetailImport(rs.getString("product"),rs.getInt("quantity"),rs.getDouble("unit_price"),rs.getDouble("amount")));
            }
        loadDetailImportTable(importDetailList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        return  this.supplierCBBox.getSelectionModel().getSelectedItem() == null
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
        List<Import> imports = new ArrayList<>();
        String sql = "SELECT * FROM import WHERE supplier LIKE '%" + txt
                + "%'";
        Connection connectDb = conn.getConnection();
        try {
            PreparedStatement pst = connectDb.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("import_id");
                String supplier= rs.getString("supplier");
                Date date = rs.getDate("date");
                Double total = rs.getDouble("total");
                String cashier = rs.getString("cashier");

                imports.add(new Import(id,supplier,date,total,cashier));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                connectDb.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return imports;
    }

    private void UpdateListImport(String s) throws SQLException {
        importList = FXCollections.observableArrayList(Search(s));
        Col_supplier.setCellValueFactory(new PropertyValueFactory("supplier_name"));
        Col_importID.setCellValueFactory(new PropertyValueFactory("import_id"));
        Col_importDate.setCellValueFactory(new PropertyValueFactory("import_date"));
        Col_total.setCellValueFactory(new PropertyValueFactory("total"));
        Col_cashier.setCellValueFactory(new PropertyValueFactory("cashier"));
        ImportTableView.setItems(importList);
    }

    @FXML
    public void searchOnAction(ActionEvent event) throws SQLException {
        UpdateListImport(searchtxt.getText());
    }

    public String getIDImport() throws SQLException {
        ResultSet rs = this.getImportID.executeQuery();
        while(rs.next()) {
            return rs.getString(1);
        }
        return "";
    }

    public void addSubmitButtonOnAction() throws SQLException {

            if (!supplierCBBox.getSelectionModel().isEmpty() && DetailImportTableView.getItems().size() > 0 && importDate.getValue() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This receipt can not be edited. Add these products to inventory? ", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    this.addImport();
                    this.addDetailImport(getIDImport());
                    importDetailList.clear();
                    UpdateListImport("");
                    CloseDialogReceipt();
                    resetInputField();
                    resetDetailInputField();
                }
            } else {
                Notifications.create().text("Please fill in all fields.").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            }
        }


    public void addDetailImport(String idImport) {
        importDetailList.forEach(DetailImport -> {
            try {
                System.out.println(DetailImport.getProduct() + " " + idImport);
                this.addDetail.setString(1,idImport);
                this.addDetail.setInt(2,DetailImport.getQuantity());
                this.addDetail.setString(3, DetailImport.getProduct());
                this.addDetail.setDouble(4,DetailImport.getUnit_price());
                this.addDetail.setDouble(5,DetailImport.getAmount());
                this.addDetail.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        importDetailList.clear();
    }

    public void addImport() {
            try {
                this.add.setString(1, (String) supplierCBBox.getValue());
                this.add.setDate(2, Date.valueOf(importDate.getValue()));
                this.add.setString(3, lbStaff.getText());
                this.add.setDouble(4,Double.parseDouble(lbTotal.getText()));
                this.add.execute();
                Notifications.create().text("Add successfully!")
                        .title("Notification")
                        .hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            } catch (SQLException e) {
                e.printStackTrace();
                Notifications.create().text("Add Failure!")
                        .title("Notification")
                        .hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            }

    }

    public void deleteButtonOnAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete these products out of inventory?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            if(!ImportTableView.getSelectionModel().isEmpty()) {
                try {
                    this.delete.setInt(1, ImportTableView.getSelectionModel().getSelectedItem().getImport_id());
                    this.delete.execute();
                    this.deleteDetailImport.setInt(1, ImportTableView.getSelectionModel().getSelectedItem().getImport_id());
                    this.deleteDetailImport.execute();
                    Notifications.create().text("Successfully .").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
                    UpdateListImport("");
                    resetInputField();
                } catch (Exception var15) {
                    var15.printStackTrace();
                    Notifications.create().text("error!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
                }
            }
            else
            {
                Notifications.create().text("Please pick a row to delete.").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            }
        }
    }


    public ObservableList<String> getAllSupplierName()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        Connection connectDB = conn.getConnection();
        String query = "select Supplier_Name from supplier";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                String Supplier_Name = queryResult.getString("Supplier_Name");
                list.add(Supplier_Name);
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> getAllProductName()
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        Connection connectDB = conn.getConnection();
        String query = "select ProductName from product";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                String Supplier_Name = queryResult.getString("ProductName");
                list.add(Supplier_Name);
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return list;
    }

    public void ShowDialogReceipt()
    {
        unitPriceTxt.setEditable(true);
        supplierCBBox.setDisable(false);
        itemCBBox.setDisable(false);
        importDate.setDisable(false);
        addItemButton.setDisable(false);
        AnchorPane.setEffect(new BoxBlur(3, 3, 3));
        receiptAnchorpane.setVisible(true);
        dialog.setOverlayClose(false);
        dialog.setContent(receiptAnchorpane);
        //dialog.setBackground(Background.EMPTY);
        dialog.setDialogContainer(StackPane);
        dialog.getStyleClass().add("jfx-dialog-overlay-pane");
        dialog.setStyle("-fx-background-color: transparent");
        printReceiptButton.setVisible(false);
        addSubmitButton.setVisible(true);
        dialog.show();
    }



    public void CloseDialogReceipt()
    {
        dialog.close();
        AnchorPane.setEffect(null);
        AnchorPane.setVisible(true);
        ImportTableView.getSelectionModel().clearSelection();
    }

    public void showContextMenu()
    {
        Deletemenu.setOnAction(e->{
            this.deleteButtonOnAction();
        });
    }

    //confirm dialog
    private void deleteAnItem() {
        if (!DetailImportTableView.getSelectionModel().isEmpty()) {
            importDetailList.forEach(DetailImport -> {
                if (DetailImportTableView.getSelectionModel().getSelectedItem().getImportID() == DetailImport.getImportID()) {
                    importDetailList.remove(DetailImport);
                    return;
                }
            });
        }
    }


    private void addAnItem() {
        if (!itemCBBox.getSelectionModel().isEmpty() && !quantityTxt.getText().isEmpty() && !unitPriceTxt.getText().isEmpty()) {
            DetailImport di = new DetailImport(itemCBBox.getSelectionModel().getSelectedItem().toString(),Integer.parseInt(quantityTxt.getText()), Double.parseDouble(unitPriceTxt.getText()),
                    Double.parseDouble(quantityTxt.getText()) * Double.parseDouble(unitPriceTxt.getText()));
            importDetailList.add(di);
            totalCal += Double.parseDouble(quantityTxt.getText()) * Double.parseDouble(unitPriceTxt.getText());
            lbTotal.setText(totalCal.toString());
            loadDetailImportTable(importDetailList);
            resetDetailInputField();
        }
        else {
            Notifications.create().text("Add failure! Please fill in all fields.")
                    .title("Notification") .hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();;
        }
    }

    private void loadDetailImportTable(ObservableList<DetailImport> importDetailList) {
        Col_id.setCellValueFactory(new PropertyValueFactory("detail_id"));
        Col_productName.setCellValueFactory(new PropertyValueFactory("product"));
        Col_quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        Col_unitPrice.setCellValueFactory(new PropertyValueFactory("unit_price"));
        Col_amount.setCellValueFactory(new PropertyValueFactory("amount"));
        DetailImportTableView.setItems(importDetailList);

        Col_printID.setCellValueFactory(new PropertyValueFactory("detail_id"));
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
        staffPrint.setText(openUI.namecashier);
        totalPrint.setText(String.valueOf(this.ImportTableView.getSelectionModel().getSelectedItem().getTotal()));
    }

    private void printReceipt() {
        loadPrintReceipt();
        PrinterJob job = PrinterJob.createPrinterJob();
        AnchorPane.setEffect(new BoxBlur(3, 3, 3));
        receiptAnchorpane.setEffect(new BoxBlur(3, 3, 3));
        printAnchorPane.setVisible(true);
        dialog.setOverlayClose(true);
        dialog.setContent(printAnchorPane);
        //dialog.setBackground(Background.EMPTY);
        dialog.setDialogContainer(StackPane);
        dialog.getStyleClass().add("jfx-dialog-overlay-pane");
        dialog.setStyle("-fx-background-color: transparent");
        dialog.show();

        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 50,0,40,0);
            printerJob.showPageSetupDialog(printAnchorPane.getScene().getWindow());
            printerJob.showPrintDialog(printAnchorPane.getScene().getWindow());
            boolean success = printerJob.printPage(pageLayout, printAnchorPane);
            if (success) {
                printerJob.endJob();
                dialog.close();
            }
        }
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

}



//delete bị exception