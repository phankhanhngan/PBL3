package com.example.pbl3.DAL;

import com.example.pbl3.BLL.BLLBills;
import com.example.pbl3.BLL.BLLImports;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.DTO.*;
import com.example.pbl3.DTO.DetailImport;
import com.example.pbl3.OpenUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    public static OpenUI openUI = new OpenUI();
    static PreparedStatement insert = null;
    static PreparedStatement update = null;


    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/pbl3_db", "root", "Pknpknkn270!");
        } catch (Exception var6) {
            var6.printStackTrace();
            var6.getCause();
        }
        return null;
    }

    // Cac ham lien quan den Account
    public static List<Account> GetAllAccount() {
        List<Account> listAccount = new ArrayList<>();
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery("select * from accounts where status = 1");
            while (queryResult.next()) {
                String firstname = queryResult.getString("firstname");
                String lastname = queryResult.getString("lastname");
                String gmail = queryResult.getString("gmail");
                String phone = queryResult.getString("phone_number");
                String username = queryResult.getString("username");
                String password = queryResult.getString("password");
                String address = queryResult.getString("address");
                boolean type_admin = queryResult.getBoolean("type_customer");
                String type;
                if (type_admin) type = "Manager";
                else type = "Cashier";
                listAccount.add(new Account(firstname, lastname, gmail, phone, username, password, address, type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAccount;
    }

    public static String DecodePassword(String password) {
        try {
            ResultSet rs = getConnection().createStatement()
                    .executeQuery("select password from accounts where password = md5('" + password + "')");
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean UpdatePasswordAccount(String password) {
        String updatePassword = "UPDATE accounts SET password = md5('" + password + "') WHERE gmail = '" + BLLProject.gmail + "'";
        try {
            getConnection().createStatement().executeUpdate(updatePassword);
            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            var5.getCause();
            return false;
        }
    }


    public static boolean InsertAccount(Account a) {
        String query = "insert into accounts(firstname, lastname, gmail, phone_number, username,password, address, type_customer, status) value(?,?,?,?,?,md5(?),?,?,1)";
        try {
            insert = getConnection().prepareStatement(query);
            insert.setString(1, a.getFirstName());
            insert.setString(2, a.getLastName());
            insert.setString(3, a.getGmail());
            insert.setString(4, a.getPhone());
            insert.setString(5, a.getUsername());
            insert.setString(6, a.getPassword());
            insert.setString(7, a.getAddress());
            if (a.isTypeOfUser().equals("Manager")) insert.setInt(8, 1);
            else insert.setInt(8, 0);
            insert.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean UpdateAccount(Account a) {
        String query = "update accounts set firstname = ?, lastname = ?, address = ?, phone_number = ?, type_customer = ? where gmail = ?";
        try {
            update = getConnection().prepareStatement(query);
            update.setString(1, a.getFirstName());
            update.setString(2, a.getLastName());
            update.setString(3, a.getAddress());
            update.setString(4, a.getPhone());
            if (a.isTypeOfUser().equals("Manager")) update.setInt(5, 1);
            else update.setInt(5, 0);
            update.setString(6, a.getGmail());
            update.execute();
            if (a.getGmail().equals(BLLProject.gmail)) {
                BLLProject.setAddress(a.getAddress());
                BLLProject.setTypecashier(a.isTypeOfUser().equals("Manager") ? true : false);
                BLLProject.setUsername(a.getUsername());
                BLLProject.setNameCashier(a.getFirstName() + " " + a.getLastName());
                BLLProject.setPhoneCashier(a.getPhone());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean DeleteAccount(String username) {
        String query = "update accounts set status = 0 where username = '" + username + "'";
        try {
            getConnection().createStatement().execute(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cac ham lien quan den Category
    public static List<Category> GetAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery("select * from categories");
            while (queryResult.next()) {
                Category c = new Category(queryResult.getInt("id"), queryResult.getString("category_name"));
                categoryList.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public static boolean InsertCategory(Category c) {
        String query = "insert into categories(category_name) value('" + c.getCate_Name() + "')";
        try {
            insert = getConnection().prepareStatement(query);
            insert.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean UpdateCategory(Category c) {
        String query = "update categories set category_name = '" + c.getCate_Name() + "' where id = " + c.getCate_Id();
        try {
            update = getConnection().prepareStatement(query);
            update.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean DeleteCategory(int id) {
        String query = "delete from categories where id = " + id;
        try {
            getConnection().createStatement().execute(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cac ham lien quan den Customer
    public static List<Customer> getAllCustomer() {
        List<Customer> listCustomer = new ArrayList<>();
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery("select * from customers where status = 1");
            while (queryResult.next()) {
                int ID = queryResult.getInt("ID");
                String firstname = queryResult.getString("firstname");
                String lastname = queryResult.getString("lastname");
                String gmail = queryResult.getString("gmail");
                String address = queryResult.getString("address");
                String phone = queryResult.getString("phone_number");
                String gender = queryResult.getString("gender");
                Date birthday = queryResult.getDate("birthday");
                listCustomer.add(new Customer(ID, firstname, lastname, gmail, phone, gender, birthday, address));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCustomer;
    }

    public static boolean InsertCustomer(Customer c) {
        String query = "insert into customers(firstname,lastname,gmail,address,phone_number,gender,birthday,status) values(?,?,?,?,?,?,?,?)";
        try {
            insert = getConnection().prepareStatement(query);
            insert.setString(1, c.getFirstname());
            insert.setString(2, c.getLastname());
            insert.setString(3, c.getGmail());
            insert.setString(4, c.getAddress());
            insert.setString(5, c.getPhone());
            insert.setString(6, c.getGender());
            insert.setDate(7, c.getBirthday());
            insert.setInt(8, 1);
            insert.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean UpdateCustomer(Customer c) {
        String query = "update customers set firstname = ? , lastname = ?, gmail = ?, address = ?, phone_number = ?, gender = ?, birthday = ? where id = ?";
        try {
            update = getConnection().prepareStatement(query);
            update.setString(1, c.getFirstname());
            update.setString(2, c.getLastname());
            update.setString(3, c.getGmail());
            update.setString(4, c.getAddress());
            update.setString(5, c.getPhone());
            update.setString(6, c.getGender());
            update.setDate(7, c.getBirthday());
            update.setInt(8, c.getID());
            update.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean DeleteCustomer(int ID) {
        String query = "update customers set status = 0 where id = " + ID;
        try {
            getConnection().createStatement().execute(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cac ham lien quan den Supplier
    public static List<Supplier> GetAllSupplier() {
        List<Supplier> supplierList = new ArrayList<>();
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery("select * from suppliers where status = 1");
            while (queryResult.next()) {
                int id = queryResult.getInt("id");
                String name = queryResult.getString("name");
                String address = queryResult.getString("address");
                String phone = queryResult.getString("phone");
                Boolean status = queryResult.getBoolean("status");
                supplierList.add(new Supplier(id, name, address, phone));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supplierList;
    }

    public static boolean InsertSupplier(Supplier s) {
        String query = "insert into suppliers(name,address,phone,status) value(?,?,?,1)";
        try {
            insert = getConnection().prepareStatement(query);
            insert.setString(1, s.getSup_Name());
            insert.setString(2, s.getSup_Address());
            insert.setString(3, s.getSup_Phone());
            insert.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean UpdateSupplier(Supplier s) {
        String query = "update suppliers set name = ?, address = ?, phone = ? where Id = ?";
        try {
            update = getConnection().prepareStatement(query);
            update.setString(1, s.getSup_Name());
            update.setString(2, s.getSup_Address());
            update.setString(3, s.getSup_Phone());
            update.setInt(4, s.getSup_Id());
            update.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean DeleteSupplier(int ID) {
        String query = "update suppliers set status = 0 where Id = " + ID;
        try {
            getConnection().createStatement().execute(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cac ham lien quan den Product
    public static List<Product> GetAllProduct() {
        List<Product> productList = new ArrayList<>();
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery("select * from products where status = 1");
            while (queryResult.next()) {
                String serial = queryResult.getString("serial");
                String name = queryResult.getString("name");
                Double salePrice = queryResult.getDouble("sale_price");
                Blob blob = queryResult.getBlob("image");
                InputStream image = blob.getBinaryStream();
                String category = queryResult.getString("category_name");
                int quantity = BLLImports.GetQuantityProductImport(name) - BLLBills.GetQuantityProductBought(name);
                productList.add(new Product(serial, name, salePrice, image, category, quantity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public static boolean InsertProduct(Product p) {
        String query = "insert into products(serial,name,sale_price,image,category_name,status) value(?,?,?,?,?,1)";
        try {
            insert = getConnection().prepareStatement(query);
            insert.setString(1, p.getSerial());
            insert.setString(2, p.getProductName());
            insert.setDouble(3, p.getSalePrice());
            insert.setBinaryStream(4, p.getImage());
            insert.setString(5, p.getCategory());
            insert.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean UpdateProduct(Product p) {
        if (p.getImage() != null) {
            String query = "update products set name = ?, sale_price = ?, image = ?, category_name = ? where serial = ?";
            try {
                update = getConnection().prepareStatement(query);
                update.setString(1, p.getProductName());
                update.setDouble(2, p.getSalePrice());
                update.setBinaryStream(3, p.getImage());
                update.setString(4, p.getCategory());
                update.setString(5, p.getSerial());
                update.execute();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            String query = "update products set name = ?, sale_price = ?, category_name = ? where serial = ?";
            try {
                update = getConnection().prepareStatement(query);
                update.setString(1, p.getProductName());
                update.setDouble(2, p.getSalePrice());
                update.setString(3, p.getCategory());
                update.setString(4, p.getSerial());
                update.execute();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static boolean DeleteProduct(String barcode) {
        String query = "update products set status = 0 where serial = '" + barcode + "'";
        try {
            getConnection().createStatement().execute(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cac ham lien quan den Bill
    public static List<Bill> GetAllBill() {
        List<Bill> billList = new ArrayList<>();
        String selectBill = "select * from bills";
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery(selectBill);
            while (queryResult.next()) {
                int Bill_ID = queryResult.getInt("id_bill");
                String CusName = queryResult.getString("customer_name");
                String CashierName = queryResult.getString("cashier_name");
                Double total = queryResult.getDouble("total");
                Date date = queryResult.getDate("date");
                String cashier_gmail = queryResult.getString("cashier_gmail");
                int IDCustomer = queryResult.getInt("id_customer");
                String pay = queryResult.getString("pay");
                boolean status = queryResult.getBoolean("status");
                Bill bill = new Bill(Bill_ID, IDCustomer, CusName, cashier_gmail, CashierName, date, total, pay, status);
                billList.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billList;
    }

    public static boolean InsertBill(Bill b) {
        String query = "insert into bills(id_customer,customer_name,date,cashier_gmail,cashier_name,total,pay,status) value(?,?,?,?,?,?,?,1)";
        try {
            insert = getConnection().prepareStatement(query);
            insert.setInt(1, b.getID_Customer());
            insert.setString(2, b.getCustomerName());
            insert.setDate(3, b.getDate());
            insert.setString(4, b.getCashier_gmail());
            insert.setString(5, b.getCashierName());
            insert.setDouble(6, b.getTotal_Money());
            insert.setString(7, b.getPay());
            insert.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean DeleteBill(int IDBill) {
        String query = "update bills set status = 0 where ID_Bill = '" + IDBill + "'";
        try {
            getConnection().createStatement().execute(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Bill GetBillByID(int id) {
        List<Bill> billList = BLLBills.getListBill();
        for (int i = 0; i < billList.size(); i++)
            if (billList.get(i).getID_Bill() == id)
                return billList.get(i);
        return null;
    }

    //Cac ham lien quan den DetailBill
    public static List<DetailBillDB> GetAllDetailBill() {
        List<DetailBillDB> detailBills = new ArrayList<>();
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery("select * from detailbills");
            while (queryResult.next()) {
                int iddetailbill = queryResult.getInt("id_detail");
                int idbill = queryResult.getInt("id_bill");
                int quantity = queryResult.getInt("quantity");
                String product = queryResult.getString("product_name");
                String product_serial = queryResult.getString("product_serial");
                double unit_price = queryResult.getDouble("unit_price");
                detailBills.add(new DetailBillDB(iddetailbill, idbill, quantity, product, product_serial, unit_price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detailBills;
    }


    public static boolean InsertDetailBill(DetailBillDB db) {
        String query = "insert into detailbills(id_bill,quantity,product_name,product_serial,unit_price) value(?,?,?,?,?)";
        try {
            insert = getConnection().prepareStatement(query);
            insert.setInt(1, db.getIdBill());
            insert.setInt(2, db.getQuantity());
            insert.setString(3, db.getProductName());
            insert.setString(4, db.getProduct_serial());
            insert.setDouble(5, db.getUnit_price());
            insert.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cac ham lien quan den Import
    public static List<Import> GetAllImport() {
        List<Import> importList = new ArrayList<>();
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery("select * from imports");
            while (queryResult.next()) {
                int id = queryResult.getInt("id_import");
                String supplier = queryResult.getString("supplier_name");
                int supplier_id = queryResult.getInt("supplier_id");
                Date date = queryResult.getDate("date");
                String cashier = queryResult.getString("cashier_name");
                String cashier_gmail = queryResult.getString("cashier_gmail");
                Double total = queryResult.getDouble("total");
                importList.add(new Import(id, supplier_id, supplier, date, total, cashier, cashier_gmail));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return importList;
    }

    public static boolean InsertImport(Import i) {
        String query = "insert into imports(supplier_id,supplier_name,date,cashier_gmail,cashier_name,total) value(?,?,?,?,?,?)";
        try {
            insert = getConnection().prepareStatement(query);
            insert.setInt(1, i.getSupplier_id());
            insert.setString(2, i.getSupplier_name());
            insert.setDate(3, i.getImport_date());
            insert.setString(4, i.getCashier_gmail());
            insert.setString(5, i.getCashier());
            insert.setDouble(6, i.getTotal());
            insert.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean DeleteImport(int id) {
        String query = "delete from imports where import_id = '" + id + "'";
        try {
            getConnection().createStatement().execute(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cac ham lien quan den ImportDetail
    public static List<DetailImport> GetAllDetailImport() {
        List<DetailImport> detailImports = new ArrayList<>();
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery("select * from detailimports");
            while (queryResult.next()) {
                int id = queryResult.getInt("id_import");
                String name = queryResult.getString("product_name");
                String product_id = queryResult.getString("product_serial");
                int quantity = queryResult.getInt("quantity");
                Double unitprice = queryResult.getDouble("unit_price");
                Double amount = queryResult.getDouble("amount");
                detailImports.add(new DetailImport(id, name, product_id, quantity, unitprice, amount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detailImports;
    }

    public static boolean InsertDetailImport(DetailImport di) {
        String query = "insert into detailimports(id_import,quantity,product_serial,product_name,unit_price,amount) value(?,?,?,?,?,?)";
        try {
            insert = getConnection().prepareStatement(query);
            insert.setInt(1, di.getImportID());
            insert.setInt(2, di.getQuantity());
            insert.setString(3, di.getProduct_id());
            insert.setString(4, di.getProduct());
            insert.setDouble(5, di.getUnit_price());
            insert.setDouble(6, di.getAmount());
            insert.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Cac ham thong ke
    public static List<String> ListCategory(String txtStart, String txtEnd) {
        List<String> listCategory = new ArrayList<>();
        String query2 = "select sum(detailbills.quantity*products.sale_price) as Sale,sum(detailbills.quantity) as Quantity, products.category_name from detailbills inner join products on detailbills.product_name = products.name inner join bills on detailbills.id_bill = bills.id_bill where(Date>= '" + txtStart + "'and Date <='" + txtEnd + "') group by products.category_name;";
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery(query2);
            while (queryResult.next()) {
                listCategory.add(queryResult.getString("category_name"));
            }
        } catch (SQLException var14) {
            var14.printStackTrace();
        }
        return listCategory;
    }

    public static List<Double> ListSales(String txtStart, String txtEnd) {
        String query2 = "select sum(detailbills.quantity*products.sale_price) as Sale,sum(detailbills.quantity) as Quantity, products.category_name from detailbills inner join products on detailbills.product_name = products.name inner join bills on detailbills.id_bill = bills.id_bill where(Date>= '" + txtStart + "'and Date <='" + txtEnd + "') group by products.category_name;";
        List<Double> listSales = new ArrayList<>();
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery(query2);
            while (queryResult.next()) {
                listSales.add(queryResult.getDouble("Sale"));
            }
        } catch (SQLException var14) {
            var14.printStackTrace();
        }
        return listSales;
    }

    public static List<String> ListDate(String txtStart, String txtEnd, boolean Type) {
        String query = "";
        if (Type == false) {
            query = "select sum(bills.total) as Sale,count(bills.id_bill) as quantityBill, concat(Month(date),'/',Year(date)) as NewDate from bills where (date>= '" + txtStart + "'and date <='" + txtEnd + "') group by Month(date)";
        } else {
            query = "select sum(bills.total) as Sale, count(bills.id_bill) as quantityBill,concat(Day(date),'/',Month(date)) as NewDate from bills where (date>= '" + txtStart + "'and date <='" + txtEnd + "') group by date order by Date";
        }

        List<String> ListDate = new ArrayList<>();
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery(query);
            while (queryResult.next()) {
                ListDate.add(queryResult.getString("NewDate"));
            }
        } catch (SQLException var14) {
            var14.printStackTrace();
        }
        return ListDate;
    }

    public static List<Double> ListSalesBarchart(String txtStart, String txtEnd, boolean Type) {
        ObservableList<Double> ListSale = FXCollections.observableArrayList();
        String query = "";
        if (!Type) {
            query = "select sum(bills.total) as Sale,count(bills.id_bill) as quantityBill, concat(Month(date),'/',Year(date)) as NewDate from bills where (date>= '" + txtStart + "'and date <='" + txtEnd + "') group by Month(date)";
        } else {
            query = "select sum(bills.total) as Sale, count(bills.id_bill) as quantityBill,concat(Day(date),'/',Month(date)) as NewDate from bills where (date>= '" + txtStart + "'and date <='" + txtEnd + "') group by date order by Date";
        }

        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery(query);
            while (queryResult.next()) {
                ListSale.add(queryResult.getDouble("Sale"));
            }
        } catch (SQLException var14) {
            var14.printStackTrace();
        }
        return ListSale;
    }

    public static int QuantityBill(String txtStart, String txtEnd, boolean Type) {
        int QuantityBill = 0;
        String query;
        if (!Type) {
            query = "select sum(bills.total) as Sale,count(bills.id_bill) as quantityBill, concat(Month(date),'/',Year(date)) as NewDate from bills where (date>= '" + txtStart + "'and date <='" + txtEnd + "') group by Month(date)";
        } else {
            query = "select sum(bills.total) as Sale, count(bills.id_bill) as quantityBill,concat(Day(date),'/',Month(date)) as NewDate from bills where (date>= '" + txtStart + "'and date <='" + txtEnd + "') group by date order by Date";
        }
        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery(query);
            while (queryResult.next()) {
                QuantityBill += queryResult.getInt("quantityBill");
            }
        } catch (SQLException var14) {
            var14.printStackTrace();
        }
        return QuantityBill;
    }

}
