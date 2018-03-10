/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Modelo;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class DayleController implements Initializable {

    @FXML
    private TableView<Modelo> table;
    @FXML
    private TableColumn<Modelo, String> modeloCol;
    @FXML
    private TableColumn<Modelo, String> marcaCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        modeloCol.setCellValueFactory((TableColumn.CellDataFeatures<Modelo, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getModel()));
        marcaCol.setCellValueFactory((TableColumn.CellDataFeatures<Modelo, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getBrand()));

        ServicesLocator.getServicesInstance();
        table.setItems(FXCollections.observableList(ServicesLocator.getModeloServices().retriveAllModels()));

    }

}
