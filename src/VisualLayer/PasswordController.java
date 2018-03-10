/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.User;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class PasswordController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Stage stage;
    private MainClass main;
    private User userInstance;

    @FXML
    private Label userlb;
    @FXML
    private PasswordField pf;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
    }

    /**
     * @param userInstance the userInstance to set
     */
    public void setUserInstance(User userInstance) {
        this.userInstance = userInstance;
        if (userInstance != null) {
            userlb.setText(userInstance.getUserName());

        }
    }

    public void onAccept() {
        userInstance.setPass(pf.getText());
        ServicesLocator.getUserServices().changePassword(userInstance);
        stage.hide();
        stage.close();
    }

}
