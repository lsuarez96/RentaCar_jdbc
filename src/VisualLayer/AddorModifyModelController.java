/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Marca;
import ModelLayer.Modelo;
import ModelLayer.Tarifas;
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
public class AddorModifyModelController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Modelo modelInstance;
    @FXML
    private TextField modelTF;
    @FXML
    private ComboBox<Marca> brandCB;
    @FXML
    private ComboBox<Tarifas> tarCB;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServicesLocator.getServicesInstance();
        List<Marca> brandsList = ServicesLocator.getMarcaServices().retriveAllBrands();
        Marca[] auxArr = new Marca[brandsList.size()];
        brandsList.toArray(auxArr);
        ObservableList<Marca> brandCbItems = new ImmutableObservableList<>(auxArr);
        brandCB.setItems(brandCbItems);
        List<Tarifas> tarList = ServicesLocator.getTarifasServices().retriveAllTariffs();
        Tarifas[] tarItems = new Tarifas[tarList.size()];
        tarList.toArray(tarItems);
        tarCB.setItems(new ImmutableObservableList<>(tarItems));
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

    public void onAcceptPressed() {
        boolean exit = false;
        if (modelInstance == null) {
            String model = modelTF.getText().toLowerCase();
            String brand = brandCB.getSelectionModel().getSelectedItem().getBrand().toLowerCase();
            String tarn = tarCB.getSelectionModel().getSelectedItem().getNormalPrice();
            String tare = tarCB.getSelectionModel().getSelectedItem().getEspecialPrice();
            if (!(model.isEmpty() && brand.isEmpty() && tarn.isEmpty() && tare.isEmpty())) {

                Modelo m = new Modelo(model, brand, tarn, tare);
                if (ServicesLocator.getModeloServices().retriveModel(model, brand) == (null)) {
                    try {
                        ServicesLocator.getModeloServices().insertModel(m);
                    } catch (InsertionModificationException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        Logger.getLogger(AddorModifyModelController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    exit = true;

                } else {
                    JOptionPane.showMessageDialog(null, "El modelo ya existe");
                    exit = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
                exit = false;
            }
            if (exit) {
                main.loadModelsView();
                stage.hide();
                stage.close();

            }
        } else {
            String model = modelTF.getText().toLowerCase();
            String brand = brandCB.getSelectionModel().getSelectedItem().getBrand().toLowerCase();
            String tarn = tarCB.getSelectionModel().getSelectedItem().getNormalPrice();
            String tare = tarCB.getSelectionModel().getSelectedItem().getEspecialPrice();
            if (!(model.isEmpty() && brand.isEmpty())) {

                Modelo m = new Modelo(model, brand, tarn, tare);
                if (ServicesLocator.getModeloServices().retriveModel(model, brand) != (null)) {
                    try {
                        ServicesLocator.getModeloServices().modifyModel(m);
                    } catch (InsertionModificationException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        Logger.getLogger(AddorModifyModelController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    exit = true;

                } else {
                    JOptionPane.showMessageDialog(null, "El modelo no existe");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
                exit = false;
            }
            if (exit) {
                main.loadModelsView();
                stage.hide();
                stage.close();
            }
        }

    }

    /**
     * @param modelInstance the modelInstance to set
     */
    public void setModelInstance(Modelo modelInstance) {
        this.modelInstance = modelInstance;
        if (modelInstance != null) {

            modelTF.setText(modelInstance.getModel());
            brandCB.getSelectionModel().select(ServicesLocator.getMarcaServices().retriveBrand(modelInstance.getBrand()));
            modelTF.setDisable(true);
            brandCB.setDisable(true);
            tarCB.getSelectionModel().select(ServicesLocator.getTarifasServices().retriveTariff(modelInstance.getNormalTarif(), modelInstance.getSpeciallTarif()));
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
