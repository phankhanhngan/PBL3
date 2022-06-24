package com.example.pbl3.View;

import animatefx.animation.FadeInUp;
import com.example.pbl3.BLL.BLLProject;
import com.example.pbl3.OpenUI;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomepageController implements Initializable {


    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text TextDevelopBy;

    @FXML
    private Text TextOrie;

    @FXML
    private MenuItem account;

    @FXML
    private MenuItem statistics;

    @FXML
    private MenuItem bill;

    @FXML
    private Button buttonStarted;

    @FXML
    private MenuItem category;

    @FXML
    private MenuItem customer;

    @FXML
    private MenuItem dashboard;

    @FXML
    private ImageView facebook;

    @FXML
    private ImageView google;

    @FXML
    private ImageView imageLogo;

    @FXML
    private ImageView imageMain;

    @FXML
    private Label labelWelcome;

    @FXML
    private Label labelWelcome1;

    @FXML
    private Label lb1;

    @FXML
    private Label lb2;

    @FXML
    private Label lb3;

    @FXML
    private Line line;

    @FXML
    private MenuItem logout;

    @FXML
    private MenuItem myaccount;

    @FXML
    private MenuItem order;

    @FXML
    private MenuItem product;

    @FXML
    private MenuItem receipt;

    @FXML
    private MenuItem supplier;

    @FXML
    private Text txt1;

    @FXML
    private Text txt2;

    @FXML
    private Text txt3;

    @FXML
    private Text txt4;

    @FXML
    private Text txt5;

    @FXML
    private Text txt6;

    @FXML
    private Text txt7;

    @FXML
    private Text txt8;

    @FXML
    private Text txt9;

    @FXML
    private ImageView youtube;
    private final String FACEBOOK = "https://www.facebook.com/LaynezCode-106644811127683";

    private final String GMAIL = "https://www.google.com/";

    private final String YOUTUBE = "https://www.youtube.com/c/LaynezCode/";

    OpenUI openUI = new OpenUI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decentralization();
        setView();
        setURL();
        setTransition();
    }
    private void setView()
    {
        labelWelcome1.setVisible(false);
        lb1.setVisible(false);
        lb2.setVisible(false);
        lb3.setVisible(false);
        txt1.setVisible(false);
        txt2.setVisible(false);
        txt3.setVisible(false);
        txt4.setVisible(false);
        txt5.setVisible(false);
        txt6.setVisible(false);
        txt7.setVisible(false);
        txt8.setVisible(false);
        txt9.setVisible(false);
        imageLogo.setVisible(false);
        labelWelcome.setVisible(false);
        buttonStarted.setVisible(false);
        TextDevelopBy.setVisible(false);
        TextOrie.setVisible(false);
        line.setVisible(false);
        facebook.setVisible(false);
        google.setVisible(false);
        youtube.setVisible(false);
    }
    private void setTransition()
    {
        //transition(imageMain,0.5);
        transition(labelWelcome,0.5);
        transition(labelWelcome1,0.75);
        transition(lb1,1);
        transition(lb2,1);
        transition(lb3,1);
        transition(txt1,1.5);
        transition(imageLogo,1.5);
        transition(txt2,2);
        transition(txt3,2.5);
        transition(txt4,2.5);
        transition(txt5,3);
        transition(txt6,3);
        transition(txt7,3.5);
        transition(txt8,4);
        transition(txt9,4);
        transition(TextDevelopBy,4.5);
        transition(TextOrie,4.75);
        transition(line,5);
        transition(facebook,5.5);
        transition(google,5.75);
        transition(youtube,6);

    }
    private void transition(Node node, double duration) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), node);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), node);
        fadeTransition.setFromValue(2);
        fadeTransition.setToValue(0.5);

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(duration));
        pauseTransition.setOnFinished(ev -> {
            PauseTransition pauseTransition2 = new PauseTransition();
            pauseTransition2.setDuration(Duration.seconds(0.1));
            pauseTransition2.setOnFinished(ev2 -> {
                node.setVisible(true);
            });

            pauseTransition2.play();
            fadeInUp(node);
            fadeTransition.play();
        });

        pauseTransition.play();

        node.setOnMouseEntered(ev -> {
            fadeTransition.setToValue(1);
            fadeTransition.playFromStart();

            scaleTransition.setRate(1.0);
            scaleTransition.play();
        });

        node.setOnMouseExited(ev -> {
            fadeTransition.setDuration(Duration.millis(100));
            fadeTransition.setToValue(0.5);
            fadeTransition.playFromStart();

            scaleTransition.setRate(-1.0);
            scaleTransition.play();
        });
    }
    private void fadeInUp(Node node) {
        new FadeInUp(node).play();
    }
    private void setURL() {
        url(FACEBOOK, facebook);
        url(GMAIL, google);
        url(YOUTUBE, youtube);
    }
    private void url(String url, Node node) {
        node.setOnMouseClicked(ev -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
        if(BLLProject.typecashier == false)
        {
            account.setVisible(false);
            statistics.setVisible(false);
        }
    }

}
