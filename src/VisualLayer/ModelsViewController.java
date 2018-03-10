/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.LoggedRole;
import ModelLayer.Modelo;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.List;
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
public class ModelsViewController implements Initializable {

    @FXML
    private TableView<Modelo> table;
    @FXML
    private TableColumn<Modelo, String> modelsCol;
    @FXML
    private TableColumn<Modelo, String> brandsCol;

    @FXML
    private TableColumn<Modelo, String> tarNCol;
    @FXML
    private TableColumn<Modelo, String> tarECol;

    private Stage stage;
    private MainClass main;
    private Modelo itemInstance;
    @FXML
    private Button add;
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
        modelsCol.setCellValueFactory((TableColumn.CellDataFeatures<Modelo, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getModel()));
        brandsCol.setCellValueFactory((TableColumn.CellDataFeatures<Modelo, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getBrand()));
        tarNCol.setCellValueFactory((TableColumn.CellDataFeatures<Modelo, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getNormalTarif()));
        tarECol.setCellValueFactory((TableColumn.CellDataFeatures<Modelo, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getSpeciallTarif()));
        ServicesLocator.getServicesInstance();
        List<Modelo> tableItems = ServicesLocator.getModeloServices().retriveAllModels();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setItemInstance(newValue));

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

        }
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    private void setItemInstance(Modelo itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onAddPressed() {
        main.loadAddorModifyModel(null);
        stage.hide();
        stage.close();
//        List<Modelo> tableItems = ServicesLocator.getModeloServices().retriveAllModels();
//        table.setItems(FXCollections.observableList(tableItems));
    }

    public void onElimPressed() {
        if (itemInstance != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                ServicesLocator.getModeloServices().elimModel(itemInstance);
                List<Modelo> tableItems = ServicesLocator.getModeloServices().retriveAllModels();
                table.setItems(FXCollections.observableList(tableItems));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la tabla");
        }
    }

    public void onAddBrand() {
        main.loadAddBrand();
        List<Modelo> tableItems = ServicesLocator.getModeloServices().retriveAllModels();
        table.setItems(FXCollections.observableList(tableItems));
    }

    public void onModifyPressed() {
        if (itemInstance != null) {
            main.loadAddorModifyModel(itemInstance);
            stage.hide();
            stage.close();
        }
    }

    public void onAddTarif() {
        main.loadAddModifyTariff();

    }
}
