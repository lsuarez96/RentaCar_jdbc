/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Login;
import ModelLayer.User;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class RolUserViewController implements Initializable {

    @FXML
    private TreeView<String> treeView;
    @FXML
    private Label userNlb;
    @FXML
    private Label namelb;
    @FXML
    private Label lnamelb;

    private MainClass main;
    private Stage stage;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fullTableData();
        userNlb.setText("-");
        namelb.setText("-");
        lnamelb.setText("-");
    }

    public void fullTableData() {
        ServicesLocator.getServicesInstance();
        List<Login> loginList = ServicesLocator.getLoginServices().retriveAllRoleUsers();
        List<AuxLoginRepresentation> auxLogList = new ArrayList<>();
        for (Login l1 : loginList) {
            AuxLoginRepresentation temp = new AuxLoginRepresentation();
            temp.setRol(l1.getRol());
            for (Login l2 : loginList) {
                String user = l2.getUsuario();
                if (l2.getRol().equalsIgnoreCase(l1.getRol()) && !temp.getUsersList().contains(user)) {
                    temp.addUser(user);
                }
            }

            auxLogList.add(temp);
        }

        TreeItem<String> root = new TreeItem<>("Roles/Usuarios");
        root.setExpanded(true);
        TreeItem<String> adminItem = new TreeItem<>("Administrador");
        adminItem.setExpanded(true);
        TreeItem<String> mngItem = new TreeItem<>("Gerente");
        mngItem.setExpanded(true);
        TreeItem<String> depItem = new TreeItem<>("Dependiente");
        depItem.setExpanded(true);

        for (AuxLoginRepresentation al : auxLogList) {
            switch (al.getRol()) {
                case "Administrador": {
                    for (String usr : al.getUsersList()) {
                        TreeItem<String> itm = new TreeItem<>(usr);
                        boolean exist = false;
                        for (TreeItem<String> i : adminItem.getChildren()) {
                            if (i.getValue().equals(itm.getValue())) {
                                exist = true;
                            }
                        }
                        if (!exist) {
                            adminItem.getChildren().add(itm);
                        }
                    }
                }
                break;
                case "Gerente": {
                    for (String usr : al.getUsersList()) {
                        TreeItem<String> itm = new TreeItem<>(usr);
                        boolean exist = false;
                        for (TreeItem<String> i : mngItem.getChildren()) {
                            if (i.getValue().equals(itm.getValue())) {
                                exist = true;
                            }
                        }
                        if (!exist) {
                            mngItem.getChildren().add(itm);
                        }

                    }
                }
                break;
                case "Dependiente": {
                    for (String usr : al.getUsersList()) {
                        TreeItem<String> itm = new TreeItem<>(usr);
                        boolean exist = false;
                        for (TreeItem<String> i : depItem.getChildren()) {
                            if (i.getValue().equals(itm.getValue())) {
                                exist = true;
                            }
                        }
                        if (!exist) {
                            depItem.getChildren().add(itm);
                        }
                    }
                }
            }
        }

        root.getChildren().add(adminItem);
        root.getChildren().add(mngItem);
        root.getChildren().add(depItem);
        treeView.setRoot(root);

    }

    public class AuxLoginRepresentation {

        private String rol;
        private final List<String> usersNameList;

        public AuxLoginRepresentation() {
            usersNameList = new ArrayList<>();
        }

        /**
         * @return the rol
         */
        public String getRol() {
            return rol;
        }

        /**
         * @param rol the rol to set
         */
        public void setRol(String rol) {
            this.rol = rol;
        }

        public void addUser(String user) {
            usersNameList.add(user);
        }

        public List<String> getUsersList() {
            return usersNameList;
        }
    }

    public void onItemSelected() {
        try {
            String selected = treeView.getSelectionModel().getSelectedItem().getValue();
            User user = ServicesLocator.getUserServices().readUser(selected);
            if (user != null) {
                userNlb.setText(user.getUserName());
                namelb.setText(user.getName());
                lnamelb.setText(user.getLastName());
            }
        } catch (Exception e) {

        }
    }

    public void elimPrivilege() {
        String selected = treeView.getSelectionModel().getSelectedItem().getValue();
        User user = ServicesLocator.getUserServices().readUser(selected);
        if (user != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta seguro que desea remover este privilegio al usuario: " + user.getUserName(), "Confirmar", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                TreeItem<String> rolItem = treeView.getSelectionModel().getSelectedItem().getParent();
                String rol = rolItem.getValue();
                ServicesLocator.getLoginServices().eliminarRol_Uuario(selected, rol);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un usuario");
        }
        fullTableData();
    }

    public void addLogin() {
        main.loadAddLogin();
        stage.hide();
        stage.close();
    }

    public void elimUsuario() {
        String selected = treeView.getSelectionModel().getSelectedItem().getValue();
        User user = ServicesLocator.getUserServices().readUser(selected);
        if (user != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar al usuario: " + user.getUserName(), "Confirmar", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {

                ServicesLocator.getUserServices().deleteUser(user);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un usuario");
        }
        fullTableData();
    }

    public void modifyUser() {
        String selected = treeView.getSelectionModel().getSelectedItem().getValue();
        User user = ServicesLocator.getUserServices().readUser(selected);
        main.loadAddUser(user);
        stage.hide();
        stage.close();
    }

    public void addUser() {
        main.loadAddUser(null);
        stage.hide();
        stage.close();
    }

    public void manageUsers() {
        // main.loadUserView();
        stage.hide();
        stage.close();
    }

    public void changePassword() {
        String selected = treeView.getSelectionModel().getSelectedItem().getValue();
        User user = ServicesLocator.getUserServices().readUser(selected);
        if (user != null) {
            main.loadChangePassword(user);
        }
    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(onClose());
    }

    public EventHandler onClose() {
        return new EventHandler() {
            @Override
            public void handle(Event event) {
                stage.hide();
                main.loadStartPage();
                stage.close();
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
}
