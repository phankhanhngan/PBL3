package com.example.pbl3;

import java.io.File;
import java.io.IOException;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.sun.mail.imap.protocol.ID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.LocalDateStringConverter;
import org.controlsfx.control.Notifications;
import com.itextpdf.layout.Document;



import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    private Label addressCustomerTextField;
    @FXML
    private JFXButton butAdd;
    @FXML
    private JFXButton button;
    @FXML
    private JFXButton button1;
    @FXML
    private Label cashierTextField;
    @FXML
    private ComboBox<String> cbbCustomer;
    @FXML
    private ComboBox<String> cbbPay;
    @FXML
    private ComboBox<String> cbbProduct;
    @FXML
    private ComboBox<Integer> cbbQuantity;
    @FXML
    private Label customerTextField;
    @FXML
    private Label dateTextField;
    @FXML
    private Label totalMoneyTextField;
    @FXML
    private Label customerLabel;
    @FXML
    private Label payLabel;
    private PreparedStatement quantityImport = null;
    private PreparedStatement quantityBill = null;
    ObservableList<DetailBill> list = FXCollections.observableArrayList();
    DatabaseConnection ConnectNow = new DatabaseConnection();
    Connection connectDB = ConnectNow.getConnection();
    OpenUI openUI = new OpenUI();

    double Total = 0;
    String query = "";
    int ID_Bill = 0;

    public ResultSet getdetailbill()
    {
        ResultSet queryResult = null;
        String Query = "select concat(customer.firstname,' ',customer.lastname) as customername,phone,Pay,Date,customer.address, concat(account.firstname,' ', account.lastname) as cashiername,Total from bill inner join customer on ID_Customer = ID inner join account on Cashier_Phone = phone_number where ID_Bill = " + openUI.IDBill;
        try {
            Statement statement = connectDB.createStatement();
            queryResult = statement.executeQuery(Query);
        }
        catch (SQLException var14)
        {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return queryResult;
    }


    public void addCBBCustomer() {

        if(openUI.IDBill == 0)
        {
            customerLabel.setVisible(false);
            cbbCustomer.setVisible(true);
            cbbQuantity.setVisible(true);
            butAdd.setVisible(true);
            String Query = "select firstname, lastname, phone from customer";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(Query);
                ObservableList<String> List = FXCollections.observableArrayList();
                while(queryResult.next())
                {
                    String item = queryResult.getString("phone") + " - " + queryResult.getString("firstname") + " " +  queryResult.getString("lastname");
                    List.add(item);
                }
                cbbCustomer.setItems(List);
            }
            catch (SQLException var14)
            {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
                var14.printStackTrace();
            }
        }
        else{
            cbbCustomer.setVisible(false);
            customerLabel.setVisible(true);
            ResultSet queryResult = getdetailbill();
            String item = "";
            String address = "";
            try{
                if(queryResult.next())
                {
                    item = queryResult.getString("customername") + "    SDT:   " + queryResult.getString("phone");
                    address = queryResult.getString("address");
                }

            } catch (Exception e)
            {
                e.printStackTrace();
                e.getCause();
            }
            customerLabel.setText(item);
            addressCustomerTextField.setText(address);
            cbbQuantity.setVisible(false);
            butAdd.setVisible(false);
        }
    }

    public void addCBBProduct()
    {
        if(openUI.IDBill == 0)
        {
            cbbProduct.setVisible(true);
            String Query = "select Barcode, ProductName from product";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(Query);
                ObservableList<String> List = FXCollections.observableArrayList();
                while (queryResult.next()) {
                    String item = queryResult.getString("ProductName");
                    List.add(item);
                }
                cbbProduct.setItems(List);
            } catch (SQLException var14) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String) null, var14);
                var14.printStackTrace();
            }
        }
        else
        {
            cbbProduct.setVisible(false);
        }
    }

    public void addCBBPay()
    {
        if(openUI.IDBill == 0)
        {
            payLabel.setVisible(false);
            cbbPay.setVisible(true);
            ObservableList<String> List = FXCollections.observableArrayList();
            List.add("Transfer money");
            List.add("Cash");
            cbbPay.setItems(List);
        }
        else
        {
            payLabel.setVisible(true);
            cbbPay.setVisible(false);
            ResultSet queryResult = getdetailbill();
            String item = "";
            try{
                if(queryResult.next()) item = queryResult.getString("Pay");
            } catch (Exception e)
            {
                e.printStackTrace();
                e.getCause();
            }
            payLabel.setText(item);
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
    public int getQuantityInTableByProductName(String productname)
    {
        int sl=0;
        try {
             sl = DatabaseHelper.GetQuantityByProductname(productname);
        }
        catch(Exception ngan)
        {
        }
        if(!DetailBillTableView.getItems().isEmpty())
        {
            for(DetailBill detailBill : this.DetailBillTableView.getItems())
            {
                if(detailBill.getProduct() == productname)
                {
                    sl = sl - detailBill.getQuantity();
                }
            }
        }
        return sl;
    }
    public void addCBBQuantity()
    {
        if(openUI.IDBill == 0)
        {
            cbbQuantity.setVisible(true);
            if(cbbProduct.getSelectionModel().getSelectedItem() != null)
            {
                        ObservableList<Integer> List = FXCollections.observableArrayList();
                        for(int i=1; i<=getQuantityInTableByProductName( cbbProduct.getSelectionModel().getSelectedItem()); i++)
                        {
                            List.add(i);
                        }
                        cbbQuantity.setItems(List);
            }
        }
        else
        {
            cbbQuantity.setVisible(false);
        }
    }

    public String getAddress(String phonecustomer)
    {
        String address = "";
        String query = "select address from customer where phone = '" + phonecustomer + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                address = queryResult.getString("address");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return address;
    }

    public void setDate()
    {
        if(openUI.IDBill == 0)
        {
            LocalDate date = LocalDate.now();
            String s = "Day " + date.getDayOfMonth() + " Month " + date.getMonthValue() + " Year " + date.getYear();
            dateTextField.setText(s);
        }
        else
        {
            ResultSet queryResult = getdetailbill();
            Date item = null;
            try{
                if(queryResult.next()) item = queryResult.getDate("Date");
                String s = "Day " + item.getDate() + " Month " + (item.getMonth()+1) + " Year " + (item.getYear()+1900);
                dateTextField.setText(s);
            } catch (Exception e)
            {
                e.printStackTrace();
                e.getCause();
            }
        }

    }

    public void setCustomer()
    {
        if(!cbbCustomer.getSelectionModel().isEmpty())
        {
            String[] s = cbbCustomer.getSelectionModel().getSelectedItem().split(" ",3);
            addressCustomerTextField.setText(getAddress(s[0]));
            customerTextField.setText(s[2]);
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection link = connection.getConnection();
        try {
            String queryQuantityImport = "select sum(quantity) as quantity from detailimport where product = ?  group by product";
            this.quantityImport = link.prepareStatement(queryQuantityImport);
            String queryQuantityBill = "select sum(Quantity) as quantity from detailbill left join product " +
                    "on detailbill.Product = product.ProductName where product.ProductName = ? group by product.ProductName";
            this.quantityBill = link.prepareStatement(queryQuantityBill);
        } catch (SQLException var7) {
            var7.printStackTrace();
        }
        addCBBCustomer();
        addCBBProduct();
        addCBBPay();
        setDate();
        new AutoCompleteBox(cbbCustomer);
        if(openUI.IDBill == 0)
        {
            cashierTextField.setText(openUI.namecashier);
            button.setText("Create New Bill");
            button1.setText("Cancel");
        }
        else
        {
            button.setText("Cancel");
            button1.setText("Print");
            ResultSet queryResult = getdetailbill();
            String cashiername = "", customername = "";
            double total = 0;
            try {
                if(queryResult.next()) {
                    cashiername = queryResult.getString("cashiername");
                    customername = queryResult.getString("customername");
                    total = queryResult.getDouble("Total");
                }
                cashierTextField.setText(cashiername);
                customerTextField.setText(customername);
                totalMoneyTextField.setText(total + "");
                loaddetailbill();
            } catch (Exception e)
            {
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    public boolean Check()
    {
        if(cbbCustomer.getSelectionModel().getSelectedItem() == null) return false;
        if(cbbPay.getSelectionModel().getSelectedItem() == null) return false;
        return true;
    }

    public double getPrice(String Product)
    {
        double price = 0;
        String query = "select SalePrice from product where ProductName = '" + Product + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                price = queryResult.getDouble("SalePrice");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return price;
    }


    public int getIDCustomer(String phone)
    {
        int IDCustomer = 0;
        query = "select ID from customer where phone = '" + phone + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                IDCustomer = queryResult.getInt("ID");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return IDCustomer;
    }

    public void addBill(int IDCustomer, String cashier_phone, Double Total, String pay)
    {
        query = "insert into bill(ID_Customer, Date, Cashier_Phone, Total, Pay) value(" + IDCustomer + ",'" + java.sql.Date.valueOf(LocalDate.now()) + "','" + cashier_phone + "'," + Total + ",'" + pay + "')";
        try {
            PreparedStatement add = null;
            add = connectDB.prepareStatement(query);
            add.execute();
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
    }

    public int getIDBill()
    {
        int IDBill = 0;
        query = "select MAX(ID_Bill) from bill";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                IDBill = queryResult.getInt("MAX(ID_Bill)");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        return IDBill;
    }

    public void addDetailBill(int IDBill, int Quantity, String Product)
    {
        query = "insert into detailbill(ID_Bill, Quantity, Product) value(" + ID_Bill + "," + Quantity + ",'" + Product + "')";
        try {
            PreparedStatement add = null;
            add = connectDB.prepareStatement(query);
            add.execute();
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }

//        int soluong = 0;
//        query = "select quantity from import where product_name = '" + Product + "'";
//        try {
//            Statement statement = connectDB.createStatement();
//            ResultSet queryResult = statement.executeQuery(query);
//            while(queryResult.next()) {
//                soluong = queryResult.getInt("quantity");
//            }
//        } catch (SQLException var14) {
//            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
//            var14.printStackTrace();
//        }
//        query = "update import set quantity = " + (soluong - Quantity) + " where product_name = '" + Product + "'";
//        try {
//            PreparedStatement add = null;
//            add = connectDB.prepareStatement(query);
//            add.execute();
//        } catch (SQLException var14) {
//            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
//            var14.printStackTrace();
//        }
    }

    @FXML
    void addOnAcTion(ActionEvent event) {
        if(Check())
        {
            DetailBill db = new DetailBill(cbbProduct.getSelectionModel().getSelectedItem(),cbbQuantity.getSelectionModel().getSelectedItem());
            list.add(db);
            double total = Double.parseDouble(totalMoneyTextField.getText());
            total += db.getIntoMoney();
            totalMoneyTextField.setText(total + "");

            this.Col_STT.setCellValueFactory(new PropertyValueFactory("STT"));
            this.Col_Product.setCellValueFactory(new PropertyValueFactory("Product"));
            this.Col_Quantity.setCellValueFactory(new PropertyValueFactory("Quantity"));
            this.Col_UnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
            this.Col_IntoMoney.setCellValueFactory(new PropertyValueFactory("intoMoney"));
            this.DetailBillTableView.setItems(list);
            ClearSelection();
        }
        else
        {
            Notifications.create().text("Error. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
        }
    }

    @FXML
    void buttonOnAction(ActionEvent event) {
        if(openUI.IDBill == 0)
        {
            if(Check())
            {
                cbbProduct.getSelectionModel().clearSelection();
                cbbQuantity.getSelectionModel().clearSelection();
                if(list.size() == 0)
                {
                    Notifications.create().text("Please add product!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
                    return;
                }
                String[] cus = cbbCustomer.getSelectionModel().getSelectedItem().split(" ");
                String phone_cashier = openUI.phonecashier;
                list.forEach(detailbill -> {
                    Total += getPrice(detailbill.getProduct())*detailbill.getQuantity();
                });
                addBill(getIDCustomer(cus[0]),phone_cashier, Total, cbbPay.getSelectionModel().getSelectedItem().toString());
                ID_Bill = getIDBill();
                list.forEach(detailbill -> {
                    addDetailBill(ID_Bill,detailbill.getQuantity(),detailbill.getProduct());
                });
                list.clear();
                DetailBillTableView.setItems(list);
                Notifications.create().text("You have create new bill successfully into our system!").title("Well-done!").hideAfter(Duration.seconds(5.0D)).show();
            }
            else
            {
                Notifications.create().text("Error. Try again!").title("Oh Snap!").hideAfter(Duration.seconds(5.0D)).show();
            }
        }
        else
        {
            Stage stage = (Stage) this.button.getScene().getWindow();
            stage.close();
            openUI.setIDBill(0);
            openUI.Open_UI("ViewBillUI.fxml");
        }
    }

    @FXML
    void button1OnAction(ActionEvent event) {
        DetailBill.setSTT();
        if(openUI.IDBill == 0)
        {
            Stage stage = (Stage) this.button1.getScene().getWindow();
            stage.close();
            openUI.Open_UI("ViewBillUI.fxml");
        }
        else
        {
            try {
                CreateBillPDF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void deleteRow(KeyEvent event) {
        if(openUI.IDBill == 0)
        {
            if(DetailBillTableView.getSelectionModel().getSelectedItem() != null)
            {
                if(event.getCode() == KeyCode.BACK_SPACE)
                {
                    list.remove(DetailBillTableView.getSelectionModel().getSelectedItem());
                    DetailBillTableView.setItems(list);
                }
            }
        }
    }

    public void loaddetailbill()
    {
        list.clear();
        DetailBill.setSTT();
        query = "select * from detailbill where ID_Bill = " + openUI.IDBill;
        try{
            PreparedStatement pst = connectDB.prepareStatement(query);
            ResultSet queryResult = pst.executeQuery();
            while (queryResult.next())
            {
                list.add(new DetailBill(queryResult.getString("Product"), queryResult.getInt("Quantity")));
            }
            this.Col_STT.setCellValueFactory(new PropertyValueFactory("STT"));
            this.Col_Product.setCellValueFactory(new PropertyValueFactory("Product"));
            this.Col_Quantity.setCellValueFactory(new PropertyValueFactory("Quantity"));
            this.Col_UnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
            this.Col_IntoMoney.setCellValueFactory(new PropertyValueFactory("intoMoney"));
            DetailBillTableView.setItems(list);
        }catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void CreateBillPDF() throws IOException{

        String path = "D:\\PBL3-ORIE\\PBL3\\src\\main\\storing\\Bill.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        float columnWidth[] = {140f,400f};
        Table informationTable = new Table(columnWidth);
        informationTable.addCell(new Cell().add(new Paragraph("Name customer :\nStore :\nAddress :\nPay :").setBold()).setBorder(Border.NO_BORDER));
        informationTable.addCell(new Cell().add(new Paragraph(customerLabel.getText() + "\nORIE\n" + addressCustomerTextField.getText() + "\n" + payLabel.getText()).setItalic()).setBorder(Border.NO_BORDER));

        float productWidth[] = {50f,130f,130f,130f,130f};
        Table productTable = new Table(productWidth);
        productTable.addCell(new Cell().add(new Paragraph("STT").setTextAlignment(TextAlignment.CENTER).setBold()));
        productTable.addCell(new Cell().add(new Paragraph("Product").setTextAlignment(TextAlignment.CENTER).setBold()));
        productTable.addCell(new Cell().add(new Paragraph("Quantity").setTextAlignment(TextAlignment.CENTER).setBold()));
        productTable.addCell(new Cell().add(new Paragraph("Unit Price").setTextAlignment(TextAlignment.CENTER).setBold()));
        productTable.addCell(new Cell().add(new Paragraph("Into Money").setTextAlignment(TextAlignment.CENTER).setBold()));
        list.forEach(detailBill -> {
            productTable.addCell(new Cell().add(new Paragraph(detailBill.getSTT() + "").setTextAlignment(TextAlignment.CENTER)));
            productTable.addCell(new Cell().add(new Paragraph(detailBill.getProduct()).setTextAlignment(TextAlignment.CENTER)));
            productTable.addCell(new Cell().add(new Paragraph(detailBill.getQuantity() + "").setTextAlignment(TextAlignment.CENTER)));
            productTable.addCell(new Cell().add(new Paragraph(detailBill.getUnitPrice() + "").setTextAlignment(TextAlignment.CENTER)));
            productTable.addCell(new Cell().add(new Paragraph(detailBill.getIntoMoney() + "").setTextAlignment(TextAlignment.CENTER)));
        });
        productTable.addCell(new Cell(0,5).add(new Paragraph("Total Money\t\t" + totalMoneyTextField.getText()).setTextAlignment(TextAlignment.CENTER)));

        float signWidth[] = {280f,280f};
        Table signTable = new Table(signWidth);
        signTable.addCell(new Cell().add(new Paragraph("Customer\n\n\n" + customerTextField.getText()).setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(13)).setBorder(Border.NO_BORDER));
        signTable.addCell(new Cell().add(new Paragraph("Cashier\n\n\n" + cashierTextField.getText()).setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(13)).setBorder(Border.NO_BORDER));

        document.add(new Paragraph(dateTextField.getText()).setTextAlignment(TextAlignment.RIGHT).setItalic());
        document.add(new Paragraph("BILL").setTextAlignment(TextAlignment.CENTER).setFontSize(16).setBold());
        document.add(new Paragraph("\n"));
        document.add(informationTable);
        document.add(new Paragraph("\n"));
        document.add(productTable);
        document.add(new Paragraph("\n"));
        document.add(signTable);

        document.close();
        System.out.println("pdf created");
        try {

            if ((new File("D:\\GitHub_Version3\\PBL3\\src\\main\\resources\\BillPDF\\Bill.pdf")).exists()) {

                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler D:\\GitHub_Version3\\PBL3\\src\\main\\resources\\BillPDF\\Bill.pdf");
                p.waitFor();

            } else {

                System.out.println("File is not exists");

            }

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void ClearSelection()
    {
        cbbProduct.getSelectionModel().clearSelection();
        cbbQuantity.getSelectionModel().clearSelection();
    }
}
