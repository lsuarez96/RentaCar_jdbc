/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Categorias;
import ModelLayer.Chofer;
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
public class AddorModifyDriverController implements Initializable {

    private Chofer itemInstance;
    private Stage stage;
    private MainClass main;
    @FXML
    private TextField idTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField addressTF;
    @FXML
    private ComboBox<Categorias> categoryCB;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Categorias> catList = ServicesLocator.getCategoriasServices().retriveAllCategories();
        Categorias[] crr = new Categorias[catList.size()];
        for (int i = 0; i < crr.length; i++) {
            crr[i] = catList.get(i);
        }
        ObservableList<Categorias> catItems = new ImmutableObservableList<>(crr);
        categoryCB.setItems(catItems);
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(Chofer itemInstance) {
        this.itemInstance = itemInstance;
        if (itemInstance != null) {
            idTF.setText(itemInstance.getId());
            nameTF.setText(itemInstance.getName());
            lastNameTF.setText(itemInstance.getLastName());
            addressTF.setText(itemInstance.getAddress());
            categoryCB.getSelectionModel().select(new Categorias(itemInstance.getCategory()));
        }
    }

    public void onAcceptPressed() {
        boolean exit = false;
        if (itemInstance != null) {
            String id = idTF.getText().toLowerCase();
            String name = nameTF.getText();
            String lastName = lastNameTF.getText();
            String address = addressTF.getText();
            String category = categoryCB.getSelectionModel().getSelectedItem().getCategory();
            if (!(id.isEmpty() && name.isEmpty() && lastName.isEmpty() && address.isEmpty() && category.isEmpty()) && id.length() == 11) {
                try {
                    Long.valueOf(id);
                    Chofer c = new Chofer(itemInstance.getIdChof(), id, name, lastName, category, address);
                    try {
                        ServicesLocator.getChoferServices().modifyDriver(c);
                    } catch (InsertionModificationException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        Logger.getLogger(AddorModifyDriverController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    exit = true;
                } catch (NumberFormatException e) {
                    exit = false;

                }
            } else if (id.length() != 11) {
                JOptionPane.showMessageDialog(null, "El carne del chofer tener 11 caracteres numericos");
            } else {
                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            }
        } else {
            String id = idTF.getText();
            String name = nameTF.getText();
            String lastName = lastNameTF.getText();
            String address = addressTF.getText();
            String category = categoryCB.getSelectionModel().getSelectedItem().getCategory();
            if (!(id.isEmpty() && name.isEmpty() && lastName.isEmpty() && address.isEmpty() && category.isEmpty())) {
                Chofer c = new Chofer(id, name, lastName, category, address);
                if (ServicesLocator.getChoferServices().retriveDriver(id) == (null)) {
                    try {
                        ServicesLocator.getChoferServices().insertDriver(c);
                    } catch (InsertionModificationException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        Logger.getLogger(AddorModifyDriverController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    exit = true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            }
        }
        if (exit) {
            main.loadDriversView();
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
