//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.pbl3;

import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private ContextMenu contextMenu;
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
    private Button searchBT;
    @FXML
    private Button butDetailCancle;

    @FXML
    private TextField searchTextField;
    @FXML
    private ComboBox<String> cbbprice;
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem homepage;
    @FXML
    private MenuItem account;
    @FXML
    private MenuItem importPrd;
    @FXML
    private MenuItem statistics;

    JFXDialog dialog = new JFXDialog();
    private PreparedStatement quantityImport = null;
    private PreparedStatement quantityBill = null;
    //private PreparedStatement prdQuantity = null;

    OpenUI openUI = new OpenUI();
    private File file404 = new File("D:\\PBL3-ORIE\\PBL3\\src\\main\\resources\\assets\\404.jpg");
    private File file;
    private FileChooser fileChooser;
    private Image image;
    private FileInputStream fileinputstream;
    private PreparedStatement store = null;
    private PreparedStatement retrive = null;
    private PreparedStatement update = null;
    private PreparedStatement delete = null;
    int min = 0, max = 0;
    String condition = "";


    public ProductController() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        DatabaseConnection connection = new DatabaseConnection();
        Connection link = connection.getConnection();
        try {
            String query = "select ProductImage from product where Barcode = (?)";
            this.retrive = link.prepareStatement(query, 1005, 1008);
            String query1 = "insert into product (Barcode,ProductName,SalePrice,ProductImage,Category) values (?,?,?,?,?)";
            this.store = link.prepareStatement(query1);
            String query2 = "update product set ProductName = ? , SalePrice = ?, Category = ?, ProductImage = ? where Barcode = ?";
            this.update = link.prepareStatement(query2);
            String query3 = "DELETE FROM product WHERE Barcode  = ? ";
            this.delete = link.prepareStatement(query3);
            String queryQuantityImport = "select sum(quantity) as quantity from detailimport where product = ?  group by product";
            this.quantityImport = link.prepareStatement(queryQuantityImport);
            String queryQuantityBill = "select sum(Quantity) as quantity from detailbill left join product " +
                    "on detailbill.Product = product.ProductName where product.ProductName = ? group by product.ProductName";
            this.quantityBill = link.prepareStatement(queryQuantityBill);
        } catch (SQLException var7) {
            var7.printStackTrace();
        }
        AnchorPaneProduct.setVisible(false);
        this.CompletedCombobox();
        this.loadTable("");
        min = max = 0;
        this.butBrowse.setOnAction((e) -> {
            this.OpenImageBrowse();
        });
        menuDetail.setOnAction(e->{
            if(!ProductTableView.getSelectionModel().isEmpty())
            {
                showDialogDetail();
                SelectedRowAction();
            }
            else
            {
                Notifications.create().text("You need to choose a row to show detail. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        });
        menuAdd.setOnAction(e->{
            this.file = file404;
            showDialogAdd();
        });
        menuEdit.setOnAction(e->{
            if(!ProductTableView.getSelectionModel().isEmpty())
            {
                showDialogUpdate();
                SelectedRowAction();
            }
            else
            {
                Notifications.create().text("You need to choose a row to update. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }

        });
        menuDelete.setOnAction(e->{
            if(!ProductTableView.getSelectionModel().isEmpty())
            {
                Delete();
            }
            else
            {
                Notifications.create().text("You need to choose a row to delete. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        });
        butAddOK.setOnAction(e->{
            if(!ValidationField())
            {
                AddProduct();
                //closeDialog();
            }
            else
            {
                Notifications.create().text("You need to fill in all the information. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        });
        butUpdateOK.setOnAction(e->{
            if(!ValidationField())
            {
                UpdateProduct();
                //closeDialog();
            }
            else
            {
                Notifications.create().text("You need to fill in all the information. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        });
        butCancle.setOnAction(e->{
            closeDialog();
            Clear();
        });
        butDetailCancle.setOnAction(e->{
            closeDialog();
            Clear();
        });

    }
    public void showDialog()
    {
        AnchorPane.setEffect(new BoxBlur(3, 3, 3));
        AnchorPaneProduct.setVisible(true);
        dialog.setOverlayClose(false);
        dialog.setContent(AnchorPaneProduct);
        //dialog.setBackground(Background.EMPTY);
        dialog.setDialogContainer(stackPane);
        dialog.getStyleClass().add("jfx-dialog-overlay-pane");
        dialog.setStyle("-fx-background-color: transparent");
        txtQuantity.setEditable(false);
        dialog.show();
    }
    public void closeDialog()
    {
        dialog.close();
        AnchorPane.setEffect(null);
        AnchorPane.setVisible(true);
        txtBarcode.setEditable(true);
        ProductTableView.getSelectionModel().clearSelection();
    }
    public void showDialogAdd()
    {
        showDialog();
        butUpdateOK.setVisible(false);
        butAddOK.setVisible(true);
        butDetailCancle.setVisible(false);
        butCancle.setVisible(true);

    }
    public void showDialogDetail()
    {
        showDialog();
        butUpdateOK.setVisible(false);
        butAddOK.setVisible(false);
        butCancle.setVisible(false);
        butDetailCancle.setVisible(true);
    }
    public void showDialogUpdate()
    {
        showDialog();
        butUpdateOK.setVisible(true);
        butAddOK.setVisible(false);
        butDetailCancle.setVisible(false);
        butCancle.setVisible(true);
        txtBarcode.setEditable(false);
    }

    public boolean ValidationField()
    {
        return((txtBarcode.getText().trim().isEmpty())  || (txtSalePrice.getText().trim().isEmpty())
                || (txtProductName.getText().trim().isEmpty()) ||
                (cbbCategory.getSelectionModel().getSelectedItem() == null));

    }

    public void SelectedRowAction() {
        if (((Product)this.ProductTableView.getSelectionModel().getSelectedItem()).getProductName() != "") {
            this.txtBarcode.setEditable(false);
            this.txtBarcode.setText(((Product)this.ProductTableView.getSelectionModel().getSelectedItem()).getBarcode());
            this.txtProductName.setText(((Product)this.ProductTableView.getSelectionModel().getSelectedItem()).getProductName());
            this.txtSalePrice.setText(Float.toString(((Product)this.ProductTableView.getSelectionModel().getSelectedItem()).getSalePrice()));
            this.cbbCategory.setValue(this.ProductTableView.getSelectionModel().getSelectedItem().getCategory());
            this.txtQuantity.setText(Integer.toString(this.ProductTableView.getSelectionModel().getSelectedItem().getQuantity()));

            try {
                this.retrive.setString(1, ((Product)this.ProductTableView.getSelectionModel().getSelectedItem()).getBarcode());
                ResultSet result = this.retrive.executeQuery();
                if (result.first()) {
                    Blob blob = result.getBlob("ProductImage");
                    InputStream input = blob.getBinaryStream();
                    Image image = new Image(input);
                    this.ProductImgView.setImage(image);

                }
            } catch (SQLException var5) {
                var5.printStackTrace();
            }
        }

    }

    public void Clear() {
        ProductLabel.setText("Add Product");
        txtProductName.setText("");
        txtSalePrice.setText("");
        txtBarcode.setText("");
        txtBarcode.setEditable(true);
        ProductImgView.setImage(null);
        cbbCategory.getSelectionModel().clearSelection();
        ProductTableView.getSelectionModel().clearSelection();
        searchTextField.setText("");
        txtQuantity.setText("");
        this.file = null;
    }

    public void DisableEditableField()
    {
        txtProductName.setEditable(false);
        txtSalePrice.setEditable(false);
        txtBarcode.setEditable(false);
        cbbCategory.setEditable(false);
        //ButDelete.setDisable(true);
        //ButUpdate.setDisable(true);
        butBrowse.setDisable(true);
    }
    public void EnableEditableField()
    {
        txtProductName.setEditable(true);
        txtSalePrice.setEditable(true);
        txtBarcode.setEditable(true);
        cbbCategory.setEditable(true);
        //ButDelete.setDisable(false);
        //ButUpdate.setDisable(false);
        butBrowse.setDisable(false);
    }

    public void AddProduct() {
        try {
            this.store.setString(1, this.txtBarcode.getText());
            this.store.setString(2, this.txtProductName.getText());
            this.store.setFloat(3, Float.parseFloat(this.txtSalePrice.getText()));
            this.fileinputstream = new FileInputStream(this.file);
            this.store.setBinaryStream(4, this.fileinputstream, (int)this.file.length());
            this.store.setString(5, ((String)this.cbbCategory.getSelectionModel().getSelectedItem()).toString());
            this.store.execute();
            Notifications.create().text("You have add product successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            Clear();
            this.loadTable("");
            min = max = 0;
        } catch (SQLException | FileNotFoundException var2) {
            Notifications.create().text("You have failed add product in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            System.err.println(var2);
        }

    }

    public void CompletedCombobox() {
        loadCbbCategory();
        this.cbbprice.setItems(FXCollections.observableArrayList(new String[]{"0-10", "10-50", "50-100", "100-500","500-5000","5000 or more", "All"}));
    }

    public void loadCbbCategory() {
        this.cbbCategory.setItems(this.getAllCategory());
    }

    private ObservableList<String> getAllCategory() {
        ObservableList<String> list = FXCollections.observableArrayList();
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection connectDB = ConnectNow.getConnection();
        String query = "select Category_Name from category";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                String Category_Name = queryResult.getString("Category_Name");
                list.add(Category_Name);
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return list;
    }

    public void loadTable(String txt) {
        ObservableList<Product> list = FXCollections.observableArrayList();
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection connectDB = ConnectNow.getConnection();
        String accountQuery = "select Barcode,ProductName,SalePrice,Category from product" +
                " where (ProductName LIKE '%" + txt + "%' OR Category = '" + txt + "') AND (" + "SalePrice" + " >= " + min;
        if(max != 0)
            accountQuery += " AND " + condition + " < " + max + ")";
        else accountQuery += ")";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(accountQuery);
            while(queryResult.next()) {
                String ProductName = queryResult.getString("ProductName");
                String Barcode = queryResult.getString("Barcode");
                Float SalePrice = queryResult.getFloat("SalePrice");
                String Category = queryResult.getString("Category");
                Integer Quantity = DatabaseHelper.GetQuantityByProductname(ProductName);
                Product product = new Product(Barcode, ProductName, SalePrice, Category, Quantity);
                list.add(product);
            }

            this.Col_Barcode.setCellValueFactory(new PropertyValueFactory("Barcode"));
            this.Col_Name.setCellValueFactory(new PropertyValueFactory("ProductName"));
            this.Col_SalePrice.setCellValueFactory(new PropertyValueFactory("salePrice"));
            this.Col_Category.setCellValueFactory(new PropertyValueFactory("Category"));
            this.Col_Quantity.setCellValueFactory(new PropertyValueFactory("Quantity"));
            this.ProductTableView.setItems(list);
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }

    }

    public void OpenImageBrowse() {
        this.fileChooser = new FileChooser();
        Stage stage = (Stage)this.AnchorPane.getScene().getWindow();
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
    void searchOnAction(ActionEvent event) {
        String[] price = new String[2];
        if(this.cbbprice.getSelectionModel().getSelectedItem() != null)
        {
            if(this.cbbprice.getSelectionModel().getSelectedItem().contains("or more"))
            {
                min = 5000;
                max = 0;
            }
            else if(this.cbbprice.getSelectionModel().getSelectedItem().contains("All"))
            {
                min = max = 0;
            }
            else
            {
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
        try {
            this.update.setString(1, this.txtProductName.getText());
            this.update.setFloat(2, Float.parseFloat(this.txtSalePrice.getText()));
            this.update.setString(3, ((String)this.cbbCategory.getSelectionModel().getSelectedItem()));
            if(file != null)
            {
                this.fileinputstream = new FileInputStream(this.file);
                this.update.setBinaryStream(4, this.fileinputstream, (int)this.file.length());
            }
            else
            {
                try {
                    this.retrive.setString(1, ((Product)this.ProductTableView.getSelectionModel().getSelectedItem()).getBarcode());
                    ResultSet result = this.retrive.executeQuery();
                    if (result.first()) {
                        Blob blob = result.getBlob("ProductImage");
                        this.update.setBinaryStream(4,blob.getBinaryStream());
                    }
                } catch (SQLException var5) {
                    var5.printStackTrace();
                }
            }
            this.update.setString(5, this.txtBarcode.getText());
            this.update.execute();

            Clear();
            this.loadTable("");
            Notifications.create().text("You have update product successfully into our system.").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            min = max = 0;
        } catch (SQLException | FileNotFoundException var2) {
            Notifications.create().text("You have failed update account in to our System. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            System.err.println(var2);
        }
    }

    public void Delete()
    {
        Product selected = ProductTableView.getSelectionModel().getSelectedItem();
        try {
            this.delete.setString(1,selected.getBarcode());
            delete.execute();
            Notifications.create().text("successfully .").title("Well-done!").hideAfter(Duration.seconds(5.0D)).action(new Action[0]).show();
            loadTable("");
            Clear();
        } catch (Exception var15) {
            var15.printStackTrace();
            Notifications.create().text("error!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    private int getQuantityByProductName(String product_name) throws SQLException {
        int imp = 0,bill =0;
        try {
            this.quantityImport.setString(1, product_name);
            this.quantityBill.setString(1, product_name);
            ResultSet rs1 = this.quantityImport.executeQuery();
            ResultSet rs2 = this.quantityBill.executeQuery();

            if (rs1.next()) {imp = rs1.getInt("quantity");}
            if (rs2.next()) {bill = rs2.getInt("quantity");}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (imp - bill);
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
            statistics.setVisible(false);
        }
    }
}