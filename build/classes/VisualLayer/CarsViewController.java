/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Auto;
import ModelLayer.LoggedRole;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleFloatProperty;
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
public class CarsViewController implements Initializable {

    @FXML
    private TableView<Auto> table;
    @FXML
    private TableColumn<Auto, String> plateCol;
    @FXML
    private TableColumn<Auto, String> situationCol;
    @FXML
    private TableColumn<Auto, String> modelCol;
    @FXML
    private TableColumn<Auto, String> brandCol;
    @FXML
    private TableColumn<Auto, Float> kmCol;
    @FXML
    private TableColumn<Auto, String> colourCol;

    private Stage stage;
    private MainClass main;
    private Auto selectedItem;
    @FXML
    private Button add;
    @FXML
    private Button del;
    @FXML
    private Button mod;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    @SuppressWarnings("All")
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        plateCol.setCellValueFactory((TableColumn.CellDataFeatures<Auto, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getPlate()));
        situationCol.setCellValueFactory((TableColumn.CellDataFeatures<Auto, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getSituation()));
        modelCol.setCellValueFactory((TableColumn.CellDataFeatures<Auto, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getModel()));
        brandCol.setCellValueFactory((TableColumn.CellDataFeatures<Auto, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getBrand()));
        kmCol.setCellValueFactory((TableColumn.CellDataFeatures<Auto, Float> cellData) -> (ObservableValue) new SimpleFloatProperty(cellData.getValue().getKm()));
        colourCol.setCellValueFactory((TableColumn.CellDataFeatures<Auto, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getColor()));
        ServicesLocator.getServicesInstance();
        List<Auto> tableItems = ServicesLocator.getAutoServices().retriveAllCars();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onItemSelected(newValue));

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
        if (!main.getLoggedRoles().contains(LoggedRole.MANAG)) {
            add.setDisable(true);
            del.setDisable(true);
            mod.setDisable(true);
        }
    }

    public void onItemSelected(Auto a) {
        this.selectedItem = a;
    }

    public void onAddButtonPressed() {
        main.loadAddorModifyCar(null);
        stage.hide();
        stage.close();
    }

    public void onModifyButtonPressed() {
        if (selectedItem != null) {
            main.loadAddorModifyCar(selectedItem);
            stage.hide();
            stage.close();
//            List<Auto> tableItems = ServicesLocator.getAutoServices().retriveAllCars();
//            table.setItems(FXCollections.observableList(tableItems));
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar el auto que desea modificar");
        }
    }

    public void onElimButtonPressed() {
        if (selectedItem != null) {
            stage.hide();
            int op = JOptionPane.showConfirmDialog(null, "Esta seguro de que desea eliminar el auto, \n esta accion es irreversible!!!!", "Eliminar auto", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                ServicesLocator.getAutoServices().elimCar(selectedItem);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar el auto que desea eliminar");
        }
        List<Auto> tableItems = ServicesLocator.getAutoServices().retriveAllCars();
        table.setItems(FXCollections.observableList(tableItems));
    }

    public void onMenuItem1Pressed() {
        main.loadAddorModifyModel(null);

    }

    public void onMenuItem2Pressed() {
        main.loadAddBrand();
    }

}
