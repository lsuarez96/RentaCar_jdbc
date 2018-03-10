/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ServicesLayer.ServicesLocator;
import UtilsLayer.Configuration;
import UtilsLayer.ConfigurationFileException;
import UtilsLayer.ConnectionFailureException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class ConfigureConectionViewController implements Initializable {

    @FXML
    private TextField userTF;

    @FXML
    private PasswordField passTF;
    @FXML
    private TextField addressTF;

    private Stage stage;
    private MainClass main;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userTF.setText("postgres");
        addressTF.setText("localhost:5432/rentaCar");
        passTF.setText("0000");
    }

    public void onAcceptPressed() {
        boolean successfull = true;
        String dataBaseAddress = addressTF.getText();
        String user = userTF.getText();
        String password = this.passTF.getText();
        Configuration conf = new Configuration(dataBaseAddress, user, password);
        Configuration.writeConfiguration(conf);

        try {
            Configuration.getConfigurationInstance();
        } catch (ConnectionFailureException | ConfigurationFileException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            successfull = false;
        }
        if (successfull) {
            ServicesLocator.getServicesInstance();
            main.loadUserLoginView();
            stage.hide();
            stage.close();
        }
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

}
