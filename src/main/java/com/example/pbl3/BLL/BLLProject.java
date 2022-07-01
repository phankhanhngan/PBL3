package com.example.pbl3.BLL;


import com.example.pbl3.DTO.Product;
import com.jfoenix.controls.JFXDialog;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class BLLProject {

    public static int IDBill = 0;
    public static String gmail;
    public static String phonecashier;
    public static String namecashier;
    public static boolean typecashier;
    public static String username;
    public static String address;

    public static void setAddress(String address) {
        BLLProject.address = address;
    }

    public static void setUsername(String username) {
        BLLProject.username = username;
    }

    public static void setPhoneCashier(String phoneCashier) {
        phonecashier = phoneCashier;
    }

    public static void setNameCashier(String nameCashier) {
        namecashier = nameCashier;
    }

    public static void setIDBill(int idbill) {
        IDBill = idbill;
    }

    public static void setGmail(String Gmail) {
        gmail = Gmail;
    }

    public static void setTypecashier(boolean type) {
        typecashier = type;
    }

    public static void SendMail(String to, String txt, String subject) {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vokhuong1403@gmail.com", "agpiydhzrhehuzgl");
            }
        });
        session.setDebug(true);

        try {
            System.out.println(txt);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("vokhuong1403@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(txt);
            Transport.send(message);
        } catch (MessagingException var7) {
            var7.printStackTrace();
        }
    }

    public static boolean CheckPhone(String phone) {
        if (phone.length() == 10) {
            try {
                int phone_number = Integer.parseInt(phone);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static boolean CheckMail(String mail) {
        if (mail.contains("@gmail.com")) {
            return true;
        }
        return false;
    }

    public static boolean CheckSerial(String serial) {
        List<Product> productList = BLLProducts.getListProduct();
        for (Product product : productList) {
            if (product.getSerial().equals(serial))
                return true;
        }
        return false;
    }

    public static void SetUpPrintJob(JFXDialog dialog, AnchorPane printAnchorPane, StackPane stackPane) {
        dialog.setOverlayClose(true);
        dialog.setContent(printAnchorPane);
        dialog.setDialogContainer(stackPane);
        dialog.getStyleClass().add("jfx-dialog-overlay-pane");
        dialog.setStyle("-fx-background-color: transparent");
        dialog.show();

        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 50, 0, 40, 0);
            printerJob.showPageSetupDialog(printAnchorPane.getScene().getWindow());
            printerJob.showPrintDialog(printAnchorPane.getScene().getWindow());
            boolean success = printerJob.printPage(pageLayout, printAnchorPane);
            if (success) {
                printerJob.endJob();
                dialog.close();
            }
        }
    }
}

