/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Pais;
import ModelLayer.Turista;
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
public class AddorModifyTuristController implements Initializable {

    private Turista itemInstance;
    private Stage stage;
    private MainClass main;

    @FXML
    private TextField idTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private TextField ageTF;
    @FXML
    private ComboBox<String> sexCB;
    @FXML
    private ComboBox<Pais> countryCB;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> sexItems = new ImmutableObservableList<>(new String[]{"femenino", "masculino"});
        sexCB.setItems(sexItems);
        ServicesLocator.getServicesInstance();
        List<Pais> countriesList = ServicesLocator.getPaisServices().retriveAllCountries();
        Pais[] parr = new Pais[countriesList.size()];
        countriesList.toArray(parr);
        ObservableList<Pais> countryCBItems = new ImmutableObservableList<>(parr);
        countryCB.setItems(countryCBItems);
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(Turista itemInstance) {
        this.itemInstance = itemInstance;
        if (itemInstance != null) {
            idTF.setText(itemInstance.getIdPassport());
            nameTF.setText(itemInstance.getName());
            lastNameTF.setText(itemInstance.getLastName());
            phoneTF.setText(itemInstance.getPhone());
            ageTF.setText(String.valueOf(itemInstance.getAge()));
            sexCB.getSelectionModel().select(itemInstance.getSex());
            Pais p = ServicesLocator.getPaisServices().retriveCountry(itemInstance.getCountry());
            countryCB.getSelectionModel().select(p);
        }
    }

    @SuppressWarnings("ObjectEqualsNull")
    public void onAcceptPressed() {
        boolean exit = false;
        if (itemInstance != null) {
            String id = idTF.getText().toLowerCase();
            String name = nameTF.getText();
            String lastName = lastNameTF.getText();
            String phone = phoneTF.getText();
            String sex = sexCB.getSelectionModel().getSelectedItem().toLowerCase();
            int age = -1;
            try {
                age = Integer.valueOf(ageTF.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "La edad debe ser un entero positivo mayor que cero!!!");
            }
            String country = countryCB.getSelectionModel().getSelectedItem().getCountry().toLowerCase();
            if (!id.isEmpty() && !name.isEmpty() && !lastName.isEmpty() && !phone.isEmpty() && !sex.isEmpty() && age > 0) {
                Turista t = new Turista(itemInstance.getId(), id, name, lastName, phone, sex, country, age);
                try {
                    ServicesLocator.getTuristaServices().modifyTurist(t);
                } catch (InsertionModificationException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    Logger.getLogger(AddorModifyTuristController.class.getName()).log(Level.SEVERE, null, ex);
                }
                exit = true;
            } else {
                JOptionPane.showMessageDialog(null, "Los campos entrados no pueden estar vacios");
            }

        } else {
            String id = idTF.getText().toLowerCase();
            String name = nameTF.getText();
            String lastName = lastNameTF.getText();
            String phone = phoneTF.getText();
            String sex = sexCB.getSelectionModel().getSelectedItem().toLowerCase();
            int age = -1;
            try {
                age = Integer.valueOf(ageTF.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "La edad debe ser un entero positivo mayor que cero!!!");
                ageTF.setText(String.valueOf(itemInstance.getAge()));
            }
            String country = countryCB.getSelectionModel().getSelectedItem().getCountry();
            if (!id.isEmpty() && !name.isEmpty() && !lastName.isEmpty() && !phone.isEmpty() && !sex.isEmpty() && age > 0) {
                Turista t = new Turista(id, name, lastName, age, phone, sex, country);
                if (ServicesLocator.getTuristaServices().retriveTurista(id) == (null)) {
                    try {
                        ServicesLocator.getTuristaServices().insertTurist(t);
                        exit = true;
                    } catch (InsertionModificationException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        Logger.getLogger(AddorModifyTuristController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El turista ya existe");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos entrados no pueden estar vacios");
            }
        }
        if (exit) {
            main.loadTuristsView();
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
