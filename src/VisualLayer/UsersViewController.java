/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.LoggedRole;
import ModelLayer.User;
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
public class UsersViewController implements Initializable {

    private Stage stage;
    private MainClass main;
    private User itemInstance;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> userCol;
    @FXML
    private TableColumn<User, String> nameCol;
    @FXML
    private TableColumn<User, String> lnameCol;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userCol.setCellValueFactory((TableColumn.CellDataFeatures<User, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getUserName()));
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<User, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getName()));
        lnameCol.setCellValueFactory((TableColumn.CellDataFeatures<User, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getLastName()));
        ServicesLocator.getServicesInstance();
        List<User> tableItems = ServicesLocator.getUserServices().allUser();
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
        if (main.getLoggedRoles().contains(LoggedRole.DEPEND) || main.getLoggedRoles().contains(LoggedRole.MANAG)) {

        }
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(User itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onAddPressed() {
        main.loadAddUser(null);
        stage.hide();
        stage.close();
    }

    public void modifyUser() {
        if (itemInstance != null) {
            main.loadAddUser(itemInstance);
            stage.hide();
            stage.close();
        }
    }

    public void onElimPressed() {
        if (itemInstance != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar el usuario", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                ServicesLocator.getUserServices().deleteUser(itemInstance);
                List<User> tableItems = ServicesLocator.getUserServices().allUser();
                table.setItems(FXCollections.observableList(tableItems));
            }
        }
    }

    public EventHandler closeAction() {

        EventHandler eh = (EventHandler) (Event event) -> {
            main.loadRolUserView();
            stage.hide();
            stage.close();
        };
        return eh;
    }
}
