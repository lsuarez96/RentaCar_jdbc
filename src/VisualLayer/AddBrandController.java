/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Marca;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddBrandController implements Initializable {

    @FXML
    private TextField brandTF;

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
    }

    public void onAcceptPressed() {
        String b = brandTF.getText().toLowerCase();
        Marca temp = ServicesLocator.getMarcaServices().retriveBrand(b);
        if (!b.isEmpty() && temp == null) {
            Marca m = new Marca(b);
            try {
                ServicesLocator.getMarcaServices().insertBrand(m);
            } catch (InsertionModificationException ex) {
                JOptionPane.showMessageDialog(null, ex);
                Logger.getLogger(AddBrandController.class.getName()).log(Level.SEVERE, null, ex);
            }
            main.loadBrandsView();
            stage.hide();
            stage.close();
        } else if (temp != null) {
            JOptionPane.showMessageDialog(null, "La marca ya existe");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
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
