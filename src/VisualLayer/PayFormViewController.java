/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.FormaPago;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class PayFormViewController implements Initializable {

    private Stage stage;
    private MainClass main;

    @FXML
    private TableView<FormaPago> table;
    @FXML
    private TableColumn<FormaPago, String> fpCol;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fpCol.setCellValueFactory((TableColumn.CellDataFeatures<FormaPago, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getPayType()));
        ServicesLocator.getServicesInstance();
        List<FormaPago> tableItems = ServicesLocator.getFormaPagoServices().retriveAllPayForms();
        table.setItems(FXCollections.observableList(tableItems));
    }

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @return the main
     */
    public MainClass getMain() {
        return main;
    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
    }

}
