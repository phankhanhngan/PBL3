package com.example.pbl3;

import animatefx.animation.FadeInUp;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomepageController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Text TextBrand;

    @FXML
    private Text TextCorporation;

    @FXML
    private Text TextDevelopBy;

    @FXML
    private Text TextOrie;

    @FXML
    private MenuItem account;

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
    private ImageView youtube;
    private final String FACEBOOK = "https://www.facebook.com/LaynezCode-106644811127683";

    private final String GMAIL = "https://www.google.com/";

    private final String YOUTUBE = "https://www.youtube.com/c/LaynezCode/";

    OpenUI openUI = new OpenUI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setView();
        setURL();
        setTransition();
    }
    private void setView()
    {
        //imageMain.setVisible(false);
        imageLogo.setVisible(false);
        labelWelcome.setVisible(false);
        buttonStarted.setVisible(false);
        TextDevelopBy.setVisible(false);
        TextOrie.setVisible(false);
        line.setVisible(false);
        facebook.setVisible(false);
        google.setVisible(false);
        youtube.setVisible(false);
        TextBrand.setVisible(false);
       // TextCorporation.setVisible(false);
    }
    private void setTransition()
    {
        //transition(imageMain,0.5);
        transition(imageLogo,1);
        transition(labelWelcome,1.5);
        transition(buttonStarted,3);
        transition(TextDevelopBy,3.5);
        transition(TextOrie,4);
        transition(line,4.5);
        transition(facebook,5);
        transition(google,5.5);
        transition(youtube,6);
        transition(TextBrand,6.5);
       // transition(TextCorporation,7);



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

}
