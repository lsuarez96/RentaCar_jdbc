/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Categorias;
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
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class CategoryViewController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Categorias itemInstance;
    @FXML
    private TableView<Categorias> table;
    @FXML
    private TableColumn<Categorias, String> catCol;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catCol.setCellValueFactory((TableColumn.CellDataFeatures<Categorias, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getCategory()));
        ServicesLocator.getServicesInstance();
        List<Categorias> tableItems = ServicesLocator.getCategoriasServices().retriveAllCategories();
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
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(Categorias itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onAddPressed() {
        main.loadAddCat();
        List<Categorias> tableItems = ServicesLocator.getCategoriasServices().retriveAllCategories();
        table.setItems(FXCollections.observableList(tableItems));
    }

    public void onElimPressed() {
        if (itemInstance != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                ServicesLocator.getCategoriasServices().elimCategory(itemInstance.getCategory());
                List<Categorias> tableItems = ServicesLocator.getCategoriasServices().retriveAllCategories();
                table.setItems(FXCollections.observableList(tableItems));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la tabla");
        }
    }

}
