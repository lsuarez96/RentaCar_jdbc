/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Contrato;
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
public class ContractsViewController implements Initializable {

    @FXML
    private TableView<Contrato> table;
    @FXML
    private TableColumn<Contrato, String> idTurCol;
    @FXML
    private TableColumn<Contrato, String> plateCarCol;
    @FXML
    private TableColumn<Contrato, String> startDateCol;
    @FXML
    private TableColumn<Contrato, String> endDateCol;
    @FXML
    private TableColumn<Contrato, String> idDriverCol;
    @FXML
    private TableColumn<Contrato, String> deliveryDateCol;
    @FXML
    private TableColumn<Contrato, String> payFormCol;
    @FXML
    private TableColumn<Contrato, String> importCol;

    @FXML
    private Button del;

    private Stage stage;
    private MainClass main;
    private Contrato c;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idTurCol.setCellValueFactory((TableColumn.CellDataFeatures<Contrato, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getIdTurist()));
        plateCarCol.setCellValueFactory((TableColumn.CellDataFeatures<Contrato, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getPlate()));
        startDateCol.setCellValueFactory((TableColumn.CellDataFeatures<Contrato, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getStartDateAsString()));
        endDateCol.setCellValueFactory((TableColumn.CellDataFeatures<Contrato, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getEndDateAsString()));
        idDriverCol.setCellValueFactory((TableColumn.CellDataFeatures<Contrato, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getIdDriver()));
        deliveryDateCol.setCellValueFactory((TableColumn.CellDataFeatures<Contrato, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getDeliveryDateAsString()));
        payFormCol.setCellValueFactory((TableColumn.CellDataFeatures<Contrato, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getPayment()));
        importCol.setCellValueFactory((TableColumn.CellDataFeatures<Contrato, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getTotalImport()));
        ServicesLocator.getServicesInstance();
        table.setItems(FXCollections.observableList(ServicesLocator.getContratoServices().retriveAllContracts()));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setC(newValue));

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
            del.setDisable(true);
        }
    }

    /**
     * @param c the c to set
     */
    public void setC(Contrato c) {
        this.c = c;
    }

    public void onAddPressed() {
        main.loadAddModifyContract(null);
        stage.hide();
        stage.close();
        //table.setItems(FXCollections.observableList(ServicesLocator.getContratoServices().retriveAllContracts()));
    }

    public void onModifyPressed() {
        if (c != null) {
            main.loadAddModifyContract(c);
            //table.setItems(FXCollections.observableList(ServicesLocator.getContratoServices().retriveAllContracts()));
            stage.hide();
            stage.close();
        }
    }

    public void onElimPressed() {
        if (c != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta seguro de que desea eliminar el auto, \n esta accion es irreversible!!!!", "Eliminar auto", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                ServicesLocator.getContratoServices().elimContract(c.getPlate(), c.getIdTurist(), c.getStartDate());
                table.setItems(FXCollections.observableList(ServicesLocator.getContratoServices().retriveAllContracts()));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar el auto que desea eliminar");
        }

    }

}
