/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Login;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class AdminViewController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Login login;
    @FXML
    private TableView<Login> table;
    @FXML
    private TableColumn<Login, String> userCol;
    @FXML
    private TableColumn<Login, String> rolCol;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userCol.setCellValueFactory((TableColumn.CellDataFeatures<Login, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getUsuario()));
        rolCol.setCellValueFactory((TableColumn.CellDataFeatures<Login, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getRol()));
        ServicesLocator.getServicesInstance();
        List<Login> tableItems = ServicesLocator.getLoginServices().retriveAllRoleUsers();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setItemInstance(newValue));
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

    private void setItemInstance(Login newValue) {
        login = newValue;
    }

    public void onElimPressed() {
        if (login != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta seguro de que desea eliminarlo", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                ServicesLocator.getLoginServices().eliminarRol_Uuario(login.getUsuario(), login.getRol());
                JOptionPane.showMessageDialog(null, "It's gone. RIP");
                List<Login> tableItems = ServicesLocator.getLoginServices().retriveAllRoleUsers();
                table.setItems(FXCollections.observableList(tableItems));
            }
        }
    }

    public void onAddPressed() {
        main.loadAddLogin();
        stage.hide();
        stage.close();
    }

    public void onAddUserPressed() {
        main.loadAddUser(null);
        stage.hide();
        stage.close();
    }

    public void manageUsers() {
        // main.loadUserView();
        stage.hide();
        stage.close();
    }

    public EventHandler closeAction() {

        EventHandler eh = (EventHandler) (Event event) -> {
            main.loadStartPage();
            stage.hide();
            stage.close();
        };
        return eh;
    }
}
