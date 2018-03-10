/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Chofer;
import ModelLayer.LoggedRole;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class DriversViewController implements Initializable {

    @FXML
    private TableView<Chofer> table;
    @FXML
    private TableColumn<Chofer, String> idCol;
    @FXML
    private TableColumn<Chofer, String> nameCol;
    @FXML
    private TableColumn<Chofer, String> lastNameCol;
    @FXML
    private TableColumn<Chofer, String> categoryCol;
    @FXML
    private TableColumn<Chofer, String> addressCol;

    private Stage stage;
    private MainClass main;
    private Chofer itemInstance;

    @FXML
    private Button add;
    @FXML
    private Button mod;
    @FXML
    private Button del;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory((TableColumn.CellDataFeatures<Chofer, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getId()));
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<Chofer, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getName()));
        lastNameCol.setCellValueFactory((TableColumn.CellDataFeatures<Chofer, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getLastName()));
        categoryCol.setCellValueFactory((TableColumn.CellDataFeatures<Chofer, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getCategory()));
        addressCol.setCellValueFactory((TableColumn.CellDataFeatures<Chofer, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getAddress()));
        ServicesLocator.getServicesInstance();
        table.setItems(FXCollections.observableList(ServicesLocator.getChoferServices().retriveAllDrivers()));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onItemSelected(newValue));
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
//        try {
//            Task task = new Task<Void>() {
//                @Override
//                @SuppressWarnings("SleepWhileInLoop")
//                public void run() {
//                    table.setItems(FXCollections.observableList(ServicesLocator.getChoferServices().retriveAllDrivers()));
//                }
//
//                @Override
//                protected Void call() throws Exception {
//                    return null;
//                }
//            };
//
//            ScheduledService<Void> se = new ScheduledService() {
//                @Override
//                protected Task<Void> createTask() {
//                    return task;
//                }
//            };
//            se.setPeriod(Duration.INDEFINITE);
//            se.setDelay(Duration.ONE);
//            se.setRestartOnFailure(true);
//            se.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
        if (!main.getLoggedRoles().contains(LoggedRole.MANAG)) {
            add.setDisable(true);
            del.setDisable(true);
            mod.setDisable(true);
        }
    }

    public void onItemSelected(Chofer c) {
        itemInstance = c;
    }

    public void onAddPressed() {
        main.loadAddorModifyDriver(null);
        stage.hide();
        stage.close();
        //table.setItems(FXCollections.observableList(ServicesLocator.getChoferServices().retriveAllDrivers()));
    }

    public void onModifyPressed() {
        if (itemInstance != null) {
            main.loadAddorModifyDriver(itemInstance);
            table.setItems(FXCollections.observableList(ServicesLocator.getChoferServices().retriveAllDrivers()));
        }
    }

    public void onElimPressed() {
        if (itemInstance != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este elemento", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                ServicesLocator.getChoferServices().elimDriver(itemInstance.getId());
                table.setItems(FXCollections.observableList(ServicesLocator.getChoferServices().retriveAllDrivers()));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
        }
    }

}
