/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.LoggedRole;
import ModelLayer.Tarifas;
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
public class TariffViewController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Tarifas itemInstance;

    @FXML
    private TableView<Tarifas> table;

    @FXML
    private TableColumn<Tarifas, String> normalCol;
    @FXML
    private TableColumn<Tarifas, String> speciallCol;

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
    public void initialize(URL url, ResourceBundle rb) {

        normalCol.setCellValueFactory((TableColumn.CellDataFeatures<Tarifas, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getNormalPrice()));
        speciallCol.setCellValueFactory((TableColumn.CellDataFeatures<Tarifas, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getEspecialPrice()));
        ServicesLocator.getServicesInstance();
        List<Tarifas> tableItems = ServicesLocator.getTarifasServices().retriveAllTariffs();
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
            mod.setDisable(true);
        }
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(Tarifas itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onElimPressed() {
        if (itemInstance != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                ServicesLocator.getTarifasServices().elimTariff(itemInstance);
                List<Tarifas> tableItems = ServicesLocator.getTarifasServices().retriveAllTariffs();
                table.setItems(FXCollections.observableList(tableItems));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la tabla");
        }
    }

    public void onAddPress() {
        main.loadAddModifyTariff();
        stage.hide();
        stage.close();
//        List<Tarifas> tableItems = ServicesLocator.getTarifasServices().retriveAllTariffs();
//        table.setItems(FXCollections.observableList(tableItems));
    }

}
