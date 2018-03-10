/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.User;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
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
public class AddUserController implements Initializable {

    @FXML
    private TextField userTF;
    @FXML
    private PasswordField passF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField lnameTF;

    private Stage stage;
    private MainClass main;

    private User itemInstance;

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
        if (itemInstance == null) {
            String user = userTF.getText();
            String passw = passF.getText();
            String name = nameTF.getText();
            String lname = lnameTF.getText();
            User u = new User(name, lname, user, passw);
            User temp = ServicesLocator.getUserServices().readUser(user, passw);
            if (temp == null && !(passw.isEmpty() && user.isEmpty() && name.isEmpty() && lname.isEmpty())) {
                try {
                    ServicesLocator.getUserServices().insertUser(u);
                } catch (InsertionModificationException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                main.loadAddLogin(ServicesLocator.getUserServices().readUser(user));
                // main.loadUserView();
                stage.hide();
                stage.close();
            } else if (temp != null) {
                JOptionPane.showMessageDialog(null, "El usuario ya existe");
            } else {
                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            }
        } else {

            String user = userTF.getText();
            String passw = passF.getText();
            String name = nameTF.getText();
            String lname = lnameTF.getText();
            User u = new User(itemInstance.getId(), name, lname, user, passw);
            User existInstance = ServicesLocator.getUserServices().readUser(itemInstance.getUserName());
            if (existInstance != null && !(user.isEmpty() && passw.isEmpty() && name.isEmpty() && lname.isEmpty())) {
                try {

                    ServicesLocator.getUserServices().modifyUser(u);

                    main.loadRolUserView();
                    stage.hide();
                    stage.close();
                } catch (InsertionModificationException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.onCloseRequestProperty().set(closeAction());
    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(User itemInstance) {
        this.itemInstance = itemInstance;
        if (itemInstance != null) {
            nameTF.setText(itemInstance.getName());
            lnameTF.setText(itemInstance.getLastName());
            userTF.setText(itemInstance.getUserName());
            passF.setText(itemInstance.getPass());
            passF.setEditable(false);
            passF.setDisable(true);

        }
    }

    public EventHandler closeAction() {
        return (EventHandler) (Event event) -> {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            stage.hide();
            main.loadRolUserView();
            stage.close();
        };
    }
}
