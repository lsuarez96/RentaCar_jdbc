/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Pais;
import ServicesLayer.ServicesLocator;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddModifyCountryController implements Initializable {

    private Stage stage;
    private MainClass main;

    @FXML
    private TextField countryTF;

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
     */
    public void onAcceptPressed() {
        String country = countryTF.getText().toLowerCase();
        Pais p = ServicesLocator.getPaisServices().retriveCountry(country);
        if (!country.isEmpty() && p == null) {
            try {
                ServicesLocator.getPaisServices().insertCountry(new Pais(country));
            } catch (InsertionModificationException ex) {
                JOptionPane.showMessageDialog(null, ex);
                Logger.getLogger(AddModifyCountryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            main.loadCountriesView();
            stage.hide();
            stage.close();
        } else if (p != null) {
            JOptionPane.showMessageDialog(null, "El pais ya existe");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
        }
    }

    public void keyPressedForLetters(KeyEvent e) {
        String c = e.getCharacter();
        if (Character.isDigit(c.charAt(0))) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Solo se admiten letras");
        }
    }

    public void keyPressedForDigits(KeyEvent e) {
        String c = e.getCharacter();
        if (Character.isLetter(c.charAt(0))) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Solo se admiten numeros");
        }
    }

}
