package com.example.pbl3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

public class HomepageController {
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button signOutButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button homepageButton;
    @FXML
    private Button customerButton;
    @FXML
    private Button productButton;

    OpenUI openUI = new OpenUI();

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)this.closeButton.getScene().getWindow();
        stage.close();
    }

    public void minimizeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)this.minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void signOutButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)this.signOutButton.getScene().getWindow();
        stage.close();
        openUI.Open_UI("LoginUI.fxml","");
    }

    public void accountButtonOnAction(ActionEvent event) {
            Stage stage = (Stage)this.accountButton.getScene().getWindow();
            stage.close();
            openUI.Open_UI("AccountManagementUI.fxml","");
    }

    public void homepageButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)this.homepageButton.getScene().getWindow();
        stage.close();
        openUI.Open_UI("HomePageUI.fxml","");
    }

    public void productButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)productButton.getScene().getWindow();
        stage.close();
        openUI.Open_UI("ProductManagementUI.fxml","");
    }
//    public void customerButtonOnAction(ActionEvent event) {
//        {
//            Stage stage = (Stage)this.customerButton.getScene().getWindow();
//            stage.close();
//            openUI.Open_UI("HomePageUI.fxml","");
//        }
//    }
}
