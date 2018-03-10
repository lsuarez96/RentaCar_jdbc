/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Login;
import ModelLayer.Role;
import ModelLayer.User;
import ServicesLayer.ServicesLocator;
import com.sun.javafx.collections.ImmutableObservableList;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class AddLoginController implements Initializable {

    private Stage stage;
    private MainClass main;
    private User userInstance;

    @FXML
    private ComboBox<User> userCB;

    @FXML
    private CheckBox adm;
    @FXML
    private CheckBox mng;
    @FXML
    private CheckBox dep;
    private final List<Role> roleList = new LinkedList<>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<User> tmList = ServicesLocator.getUserServices().allUser();
        User[] itemsUsr = new User[tmList.size()];
        tmList.toArray(itemsUsr);
        userCB.setItems(new ImmutableObservableList<>(itemsUsr));
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.onCloseRequestProperty().set(onClose());
    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
    }

    @SuppressWarnings("null")
    public void onAcceptPressed() {
        User u = userCB.getSelectionModel().getSelectedItem();
        if (adm.isSelected()) {
            roleList.add(new Role(adm.getText()));
        }
        if (mng.isSelected()) {
            roleList.add(new Role(mng.getText()));

        }
        if (dep.isSelected()) {
            roleList.add(new Role(dep.getText()));
        }
        // Role r = rolCB.getSelectionModel().getSelectedItem();
        if (!loginExist(roleList, u)) {
            if (u != null && !roleList.isEmpty()) {
                ServicesLocator.getLoginServices().addLogin(roleList, u.getUserName());
                main.loadRolUserView();
                stage.hide();
                stage.close();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El login ya existe");
        }
    }

    public void onAdminPress() {
        if (!adm.isSelected()) {
            adm.setSelected(true);
            roleList.add(new Role(adm.getText()));
        } else {
            adm.setSelected(false);
            roleList.remove(new Role(adm.getText()));
        }
    }

    public void onMngPressed() {
        if (!mng.isSelected()) {
            mng.setSelected(true);
            roleList.add(new Role(mng.getText()));
        } else {
            mng.setSelected(false);
            roleList.remove(new Role(mng.getText()));
        }
    }

    public void onDepPressed() {
        if (!dep.isSelected()) {
            dep.setSelected(true);
            roleList.add(new Role(dep.getText()));
        } else {
            dep.setSelected(false);
            roleList.remove(new Role(dep.getText()));
        }
    }

    public boolean loginExist(List<Role> rl, User u) {
        boolean exist = false;
        List<Login> lgList = ServicesLocator.getLoginServices().retriveAllRoleUsers();
        for (Login l : lgList) {
            for (Role r : rl) {
                if (l.getRol() == (r.getRole()) && l.getUsuario() == (u.getUserName())) {
                    return exist;
                }
            }
        }
        return exist;
    }

    /**
     * @return the userInstance
     */
    public User getUserInstance() {
        return userInstance;
    }

    /**
     * @param userInstance the userInstance to set
     */
    public void setUserInstance(User userInstance) {
        this.userInstance = userInstance;
        if (userInstance != null) {
            userCB.getSelectionModel().select(userInstance);
            userCB.setEditable(false);
            userCB.setDisable(true);
        }
    }

    public EventHandler onClose() {
        return (EventHandler) (Event event) -> {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            stage.hide();
            main.loadRolUserView();
            stage.close();
        };
    }

}
