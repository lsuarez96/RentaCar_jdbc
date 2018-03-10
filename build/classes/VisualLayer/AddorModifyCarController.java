/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Auto;
import ModelLayer.Modelo;
import ModelLayer.Situacion;
import ServicesLayer.ServicesLocator;
import com.sun.javafx.collections.ImmutableObservableList;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddorModifyCarController implements Initializable {

    private Auto autoInstance;
    private Stage stage;
    private MainClass main;

    @FXML
    private TextField plateTF;
    @FXML
    private TextField colourTF;
    @FXML
    private TextField kmTF;
    @FXML
    private ComboBox<Modelo> modelCB;
    @FXML
    private ComboBox<Situacion> situationCB;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServicesLocator.getServicesInstance();
        List<Modelo> modelList = ServicesLocator.getModeloServices().retriveAllModels();
        Modelo[] marr = new Modelo[modelList.size()];
        marr = modelList.toArray(marr);
        ObservableList<Modelo> modelCBItems = new ImmutableObservableList<>(marr);
        modelCB.setItems(modelCBItems);
        List<Situacion> situationsList = ServicesLocator.getSituacionServices().retriveAllSituations();
        Situacion[] srr = new Situacion[situationsList.size()];
        situationsList.toArray(srr);
        ObservableList<Situacion> situationCBItems = new ImmutableObservableList<>(srr);
        situationCB.setItems(situationCBItems);
    }

    @SuppressWarnings("ObjectEqualsNull")
    public void onAcceptButtonPressed() {
        boolean exit = false;
        if (autoInstance != null) {
            String situacion = situationCB.getSelectionModel().getSelectedItem().getState().toLowerCase();
            String model = modelCB.getSelectionModel().getSelectedItem().getModel();
            String brand = modelCB.getSelectionModel().getSelectedItem().getBrand();
            String color = colourTF.getText().toLowerCase();
            String plate = plateTF.getText().toLowerCase();
            float km = -1;
            try {
                km = Float.valueOf(kmTF.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "La cantidad de kilometros debe ser un numero");
            }
            if (ServicesLocator.getAutoServices().retriveCar(autoInstance.getIdCar()) != null && !situacion.isEmpty() && !model.isEmpty() && !brand.isEmpty() && !color.isEmpty() && km >= 0) {
                try {
                    Auto auto = new Auto(autoInstance.getIdCar(), plate, situacion, model, brand, color, km);
                    ServicesLocator.getAutoServices().modifyCar(auto);
                    exit = true;
                } catch (InsertionModificationException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    Logger.getLogger(AddorModifyCarController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "La entrada ha sido invalida");
            }
        } else {
            String plate = plateTF.getText().toLowerCase();
            String situacion = situationCB.getSelectionModel().getSelectedItem().getState();
            String model = modelCB.getSelectionModel().getSelectedItem().getModel();
            String brand = modelCB.getSelectionModel().getSelectedItem().getBrand();
            String color = colourTF.getText();
            float km = -1;
            try {
                km = Float.valueOf(kmTF.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "La cantidad de kilometros debe ser un numero");
            }
            if (!plate.isEmpty() && !situacion.isEmpty() && !model.isEmpty() && !brand.isEmpty() && !color.isEmpty() && km >= 0) {

                Auto auto = new Auto(plate, situacion, model, brand, color, km);
                if (ServicesLocator.getAutoServices().retriveCar(plate) == (null)) {
                    try {
                        ServicesLocator.getAutoServices().insertCar(auto);
                    } catch (InsertionModificationException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        Logger.getLogger(AddorModifyCarController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Auto a = ServicesLocator.getAutoServices().retriveCar(plate);
                    exit = true;
                } else {
                    JOptionPane.showMessageDialog(null, "El auto que intenta insertar ya existe, \n compruebe si la chapa es correcta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "La entrada ha sido invalida");
            }
        }
        if (exit) {
            main.loadCarsView();
            stage.hide();
            stage.close();
        }
    }

    /**
     * @param autoInstance the autoInstance to set
     */
    public void setAutoInstance(Auto autoInstance) {
        this.autoInstance = autoInstance;
        if (autoInstance != null) {
            plateTF.setText(autoInstance.getPlate());
            colourTF.setText(autoInstance.getColor());
            try {
                kmTF.setText(String.valueOf(autoInstance.getKm()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Los kilometros recorridos deben ser un numero");
            }
            Modelo m = ServicesLocator.getModeloServices().retriveModel(autoInstance.getModel(), autoInstance.getBrand());
            modelCB.getSelectionModel().select(m);
            Situacion s = ServicesLocator.getSituacionServices().retriveSituacion(autoInstance.getSituation());
            situationCB.getSelectionModel().select(s);
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
