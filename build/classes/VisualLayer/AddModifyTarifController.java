/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Tarifas;
import ServicesLayer.ServicesLocator;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
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
public class AddModifyTarifController implements Initializable {

    private Stage stage;
    private MainClass main;

    @FXML
    private TextField normalTF;
    @FXML
    private TextField speciallTF;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
     *
     */
    public void onAcceptPressed() {

        String normal = normalTF.getText();
        String speccial = speciallTF.getText();
        Tarifas t = new Tarifas(normal, speccial);
        Tarifas temp = ServicesLocator.getTarifasServices().retriveTariff(normal, speccial);
        if (!(normal.isEmpty() && speccial.isEmpty()) && temp == null) {
            ServicesLocator.getTarifasServices().insertTariff(t);

            main.loadTariffView();
            stage.hide();
            stage.close();
        } else if (temp != null) {
            JOptionPane.showMessageDialog(null, "La tarifa ya existe");
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
