package com.example.pbl3.View;

import com.example.pbl3.BLL.*;
import com.example.pbl3.DTO.AutoCompleteBox;
import com.example.pbl3.DTO.*;
import com.example.pbl3.OpenUI;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CreateNewBillController implements Initializable {

    @FXML
    private TableColumn<DetailBill, Double> Col_IntoMoney;
    @FXML
    private TableColumn<DetailBill, String> Col_Product;
    @FXML
    private TableColumn<DetailBill, Integer> Col_Quantity;
    @FXML
    private TableColumn<DetailBill, Integer> Col_STT;
    @FXML
    private TableColumn<DetailBill, Double> Col_UnitPrice;
    @FXML
    private TableView<DetailBill> DetailBillTableView;
    @FXML
    private TableColumn<DetailBill, Double> Col_IntoMoney1;
    @FXML
    private TableColumn<DetailBill, String> Col_Product1;
    @FXML
    private TableColumn<DetailBill, Integer> Col_Quantity1;
    @FXML
    private TableColumn<DetailBill, Integer> Col_STT1;
    @FXML
    private TableColumn<DetailBill, Double> Col_UnitPrice1;
    @FXML
    private TableView<DetailBill> DetailBillTableView1;
    @FXML
    private Label addressCustomerTextField;
    @FXML
    private Label addressCustomerTextField1;
    @FXML
    private JFXButton butAdd;
    @FXML
    private JFXButton button;
    @FXML
    private JFXButton button1;
    @FXML
    private Label cashierTextField;
    @FXML
    private Label cashierTextField1;
    @FXML
    private ComboBox<String> cbbCustomer;
    @FXML
    private ComboBox<String> cbbPay;
    @FXML
    private ComboBox<String> cbbProduct;
    @FXML
    private ComboBox<String> cbbQuantity;
    @FXML
    private Label customerTextField;
    @FXML
    private Label customerTextField1;
    @FXML
    private Label dateTextField;
    @FXML
    private Label dateTextField1;
    @FXML
    private Label totalMoneyTextField;
    @FXML
    private Label totalMoneyTextField1;
    @FXML
    private Label customerLabel;
    @FXML
    private Label customerLabel1;
    @FXML
    private AnchorPane printAnchorPane;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Label payLabel;
    @FXML
    private Label payLabel1;
    @FXML
    private Hyperlink newCustomerHyperLink;
    @FXML
    private MenuItem deleteItem;

    JFXDialog dialog = new JFXDialog();
    OpenUI openUI = new OpenUI();
    ObservableList<DetailBill> list = FXCollections.observableArrayList();

    double Total = 0;
    int ID_Bill = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        printAnchorPane.setVisible(false);
        addCBBCustomer();
        addCBBProduct();
        addCBBPay();
        setDate();
        deleteItem.setOnAction(e -> deleteRow());
        new AutoCompleteBox(cbbCustomer);
        new AutoCompleteBox(cbbProduct);
        if (BLLProject.IDBill == 0) {
            DetailBill.setSTT();
            newCustomerHyperLink.setVisible(true);
            cashierTextField.setText(BLLProject.namecashier);
            button.setText("Create New Bill");
            button1.setText("Cancel");
        } else {
            newCustomerHyperLink.setVisible(false);
            button.setText("Print");
            button1.setText("Cancel");
            cashierTextField.setText(BLLBills.getBillByIDBill(BLLProject.IDBill).getCashierName());
            customerTextField.setText(BLLBills.getBillByIDBill(BLLProject.IDBill).getCustomerName());
            totalMoneyTextField.setText(BLLBills.getBillByIDBill(BLLProject.IDBill).getTotal_Money() + "");
            loadDetailBill();
        }
    }

    public void addCBBCustomer() {

        if (BLLProject.IDBill == 0) {
            customerLabel.setVisible(false);
            cbbCustomer.setVisible(true);
            cbbQuantity.setVisible(true);
            butAdd.setVisible(true);
            List<Customer> customers = BLLCustomers.getListCustomer();
            ObservableList<String> List = FXCollections.observableArrayList();
            for (Customer customer : customers) {
                String item = customer.getPhone() + " - " + customer.getFirstname() + " " + customer.getLastname();
                List.add(item);
            }
            cbbCustomer.setItems(List);
        } else {
            cbbCustomer.setVisible(false);
            customerLabel.setVisible(true);
            customerLabel.setText(BLLBills.getBillByIDBill(BLLProject.IDBill).getCustomerName() + "     SDT:    " + BLLCustomers.getCustomerByID(BLLBills.getBillByIDBill(BLLProject.IDBill).getID_Customer()).getPhone());
            addressCustomerTextField.setText(BLLCustomers.getCustomerByID(BLLBills.getBillByIDBill(BLLProject.IDBill).getID_Customer()).getAddress());
            cbbQuantity.setVisible(false);
            butAdd.setVisible(false);
        }
    }

    public void addCBBProduct() {
        if (BLLProject.IDBill == 0) {
            cbbProduct.setVisible(true);
            List<Product> productList = BLLProducts.getListProduct();
            ObservableList<String> List = FXCollections.observableArrayList();
            for (Product product : productList) List.add(product.getProductName());
            cbbProduct.setItems(List);
        } else {
            cbbProduct.setVisible(false);
        }
    }

    public void addCBBPay() {
        if (BLLProject.IDBill == 0) {
            payLabel.setVisible(false);
            cbbPay.setVisible(true);
            ObservableList<String> List = FXCollections.observableArrayList();
            List.add("Transfer money");
            List.add("Cash");
            cbbPay.setItems(List);
        } else {
            payLabel.setVisible(true);
            cbbPay.setVisible(false);
            payLabel.setText(BLLBills.getBillByIDBill(BLLProject.IDBill).getPay());
        }
    }

    public void addCBBQuantity() {
        if (BLLProject.IDBill == 0) {
            cbbQuantity.setVisible(true);
            ObservableList<String> List = FXCollections.observableArrayList();

            if (cbbProduct.getSelectionModel().getSelectedItem() != null) {
                int quantity = BLLImports.GetQuantityProductImport(cbbProduct.getSelectionModel().getSelectedItem())
                        - BLLBills.GetQuantityProductBought(cbbProduct.getSelectionModel().getSelectedItem())
                        - getQuanityInTable(cbbProduct.getSelectionModel().getSelectedItem());
                if (quantity == 0) {
                    List.add("Out of Stock");
                    butAdd.setDisable(true);
                } else {
                    for (int i = 1; i <= quantity; i++)
                        List.add(i + "");
                    butAdd.setDisable(false);
                }
                cbbQuantity.setItems(List);
            }
        } else {
            cbbQuantity.setVisible(false);
        }
    }

    public void setDate() {
        if (BLLProject.IDBill == 0) {
            LocalDate date = LocalDate.now();
            String s = "Day " + date.getDayOfMonth() + " Month " + date.getMonthValue() + " Year " + date.getYear();
            dateTextField.setText(s);
        } else {
            Date item = BLLBills.getBillByIDBill(BLLProject.IDBill).getDate();
            String s = "Day " + item.getDate() + " Month " + (item.getMonth() + 1) + " Year " + (item.getYear() + 1900);
            dateTextField.setText(s);
        }
    }

    public void setCustomer() {
        if (!cbbCustomer.getSelectionModel().isEmpty()) {
            String[] s = cbbCustomer.getSelectionModel().getSelectedItem().split(" ", 3);
            addressCustomerTextField.setText(BLLCustomers.getCustomerByCustomerPhone(s[0]).getAddress());
            customerTextField.setText(s[2]);
        }
    }

    public boolean Check() {
        if (cbbCustomer.getSelectionModel().getSelectedItem() == null) return false;
        return cbbPay.getSelectionModel().getSelectedItem() != null;
    }

    public void addDetailBill(int Quantity, String Product, String ProductSerial, double unit_price) {
        DetailBillDB detailBillDB = new DetailBillDB(0, BLLBills.getMaxIDBill(), Quantity, Product, ProductSerial, unit_price);
        BLLBills.AddDetailBill(detailBillDB);
    }

    public int getQuanityInTable(String productname) {
        int sl = 0;
        if (!DetailBillTableView.getItems().isEmpty()) {
            for (DetailBill detailBill : this.DetailBillTableView.getItems()) {
                if (detailBill.getProduct().equals(productname)) {
                    sl = sl + detailBill.getQuantity();
                }
            }
        }
        return sl;

    }

    @FXML
    void addOnAction() {
        if (Check() && !cbbProduct.getSelectionModel().isEmpty() && !cbbQuantity.getSelectionModel().isEmpty()
                && !cbbQuantity.getSelectionModel().toString().equals("0")) {
            DetailBill db = new DetailBill(cbbProduct.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(cbbQuantity.getSelectionModel().getSelectedItem()));
            list.add(db);
            double total = Double.parseDouble(totalMoneyTextField.getText());
            total += db.getIntoMoney();
            totalMoneyTextField.setText(total + "");
            SetCol(Col_STT, Col_Product, Col_Quantity, Col_UnitPrice, Col_IntoMoney);
            this.DetailBillTableView.setItems(list);
            ClearSelection();
        } else {
            Notifications.create().text("You have failed to add products into bill. Try again!").title("Oh Snap!")
                    .hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    @FXML
    void buttonOnAction() {
        if (BLLProject.IDBill == 0) {
            if (Check()) {
                if (list.size() == 0) {
                    Notifications.create().text("Please add product!").title("Oh Snap!")
                            .hideAfter(Duration.seconds(5.0D)).show();
                    return;
                }
                String[] cus = cbbCustomer.getSelectionModel().getSelectedItem().split(" ", 3);
                list.forEach(detailbill -> Total += BLLProducts.getProductByProductName(detailbill.getProduct()).getSalePrice() * detailbill.getQuantity());
                BLLBills.AddBill(new Bill(0, BLLCustomers.getCustomerByCustomerPhone(cus[0]).getID(), cus[2],
                        BLLProject.gmail, BLLProject.namecashier, java.sql.Date.valueOf(LocalDate.now()), Total,
                        cbbPay.getSelectionModel().getSelectedItem(), true));
                ID_Bill = BLLBills.getMaxIDBill();
                list.forEach(detailbill -> addDetailBill(detailbill.getQuantity(), detailbill.getProduct(),
                        BLLProducts.getProductByProductName(detailbill.getProduct()).getSerial(),
                        BLLProducts.getProductByProductName(detailbill.getProduct()).getSalePrice()));
                Notifications.create().text("You have created new bill successfully!").title("Well-done!")
                        .hideAfter(Duration.seconds(5.0D)).show();
                Stage stage = (Stage) this.button.getScene().getWindow();
                stage.close();
                BLLProject.setIDBill(0);
                openUI.Open_UI("ViewBillUI.fxml");
            } else {
                Notifications.create().text("You have failed to create new bill. Try again!").title("Oh Snap!")
                        .hideAfter(Duration.seconds(5.0D)).show();
            }
        } else {
            printBill();
            Stage stage = (Stage) this.button.getScene().getWindow();
            stage.close();
            BLLProject.setIDBill(0);
            openUI.Open_UI("ViewBillUI.fxml");
        }
    }

    //cancel
    @FXML
    void button1OnAction() {
        DetailBill.setSTT();
        Stage stage = (Stage) this.button1.getScene().getWindow();
        stage.close();
        BLLProject.setIDBill(0);
        openUI.Open_UI("ViewBillUI.fxml");
    }

    void deleteRow() {
        if (BLLProject.IDBill == 0) {
            if (DetailBillTableView.getSelectionModel().getSelectedItem() != null) {
                double total = Double.parseDouble(totalMoneyTextField.getText());
                total -= DetailBillTableView.getSelectionModel().getSelectedItem().getIntoMoney();
                totalMoneyTextField.setText(total + "");
                list.remove(DetailBillTableView.getSelectionModel().getSelectedItem());
                ObservableList<DetailBill> newlist = FXCollections.observableArrayList();
                DetailBill.setSTT();
                list.forEach(detailBill -> newlist.add(new DetailBill(detailBill.getProduct(), detailBill.getQuantity())));
                list = newlist;
                DetailBillTableView.setItems(list);
            }
        }
    }

    public static void SetCol(TableColumn<DetailBill, Integer> col_STT, TableColumn<DetailBill, String> col_Product,
                              TableColumn<DetailBill, Integer> col_Quantity, TableColumn<DetailBill, Double> col_UnitPrice,
                              TableColumn<DetailBill, Double> col_IntoMoney) {
        col_STT.setCellValueFactory(new PropertyValueFactory("STT"));
        col_Product.setCellValueFactory(new PropertyValueFactory("Product"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory("Quantity"));
        col_UnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        col_IntoMoney.setCellValueFactory(new PropertyValueFactory("intoMoney"));
    }

    public void loadDetailBill() {
        list.clear();
        DetailBill.setSTT();
        List<DetailBillDB> detailBillDBS = BLLBills.getDetailBillByIDBill(BLLProject.IDBill);
        for (DetailBillDB detailBillDB : detailBillDBS)
            list.add(new DetailBill(detailBillDB.getProductName(), detailBillDB.getQuantity(),
                    detailBillDB.getUnit_price()));
        SetCol(Col_STT, Col_Product, Col_Quantity, Col_UnitPrice, Col_IntoMoney);
        DetailBillTableView.setItems(list);
    }

    public void loadDetailBill1() {
        list.clear();
        DetailBill.setSTT();
        List<DetailBillDB> detailBillDBS = BLLBills.getDetailBillByIDBill(BLLProject.IDBill);
        for (DetailBillDB detailBillDB : detailBillDBS)
            list.add(new DetailBill(detailBillDB.getProductName(), detailBillDB.getQuantity()));
        SetCol(Col_STT1, Col_Product1, Col_Quantity1, Col_UnitPrice1, Col_IntoMoney1);
        DetailBillTableView1.setItems(list);
    }

    public void ClearSelection() {
        cbbProduct.getSelectionModel().clearSelection();
        cbbQuantity.getSelectionModel().clearSelection();
    }

    public void loadPrintBill() {
        customerLabel1.setText(BLLBills.getBillByIDBill(BLLProject.IDBill).getCustomerName());
        cashierTextField1.setText(BLLBills.getBillByIDBill(BLLProject.IDBill).getCashierName());
        customerTextField1.setText(BLLBills.getBillByIDBill(BLLProject.IDBill).getCustomerName());
        totalMoneyTextField1.setText(BLLBills.getBillByIDBill(BLLProject.IDBill).getTotal_Money() + "");
        payLabel1.setText(BLLBills.getBillByIDBill(BLLProject.IDBill).getPay());
        addressCustomerTextField1.setText(BLLCustomers.getCustomerByID(BLLBills.getBillByIDBill(BLLProject.IDBill)
                .getID_Customer()).getAddress());
        loadDetailBill1();
        Date item = BLLBills.getBillByIDBill(BLLProject.IDBill).getDate();
        String s = "Day " + item.getDate() + " Month " + (item.getMonth() + 1) + " Year " + (item.getYear() + 1900);
        dateTextField1.setText(s);
    }

    private void printBill() {
        printAnchorPane.setVisible(true);
        loadPrintBill();
        PrinterJob job = PrinterJob.createPrinterJob();
        AnchorPane.setEffect(new BoxBlur(3, 3, 3));
        BLLProject.SetUpPrintJob(dialog, printAnchorPane, stackPane);
    }

    @FXML
    void newCustomerOnAction(ActionEvent event) {
        BLLProject.setIDBill(0);
        Stage stage = (Stage) this.newCustomerHyperLink.getScene().getWindow();
        stage.close();
        openUI.Open_UI("CustomerUI.fxml");
    }
}
