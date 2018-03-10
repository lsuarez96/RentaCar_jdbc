/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.LoggedRole;
import ModelLayer.Marca;
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
public class BrandsViewController implements Initializable {

    private Stage stage;
    private MainClass main;

    @FXML
    private TableView<Marca> table;
    @FXML
    private TableColumn<Marca, String> brandCol;
    @FXML
    private Button add;
    @FXML
    private Button del;
    private Marca itemInstance;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        brandCol.setCellValueFactory((TableColumn.CellDataFeatures<Marca, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getBrand()));
        ServicesLocator.getServicesInstance();
        List<Marca> tableItems = ServicesLocator.getMarcaServices().retriveAllBrands();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setItemInstance(newValue));

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
        if (!main.getLoggedRoles().contains(LoggedRole.MANAG)) {
            add.setDisable(true);
            del.setDisable(true);
        }
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(Marca itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onAddPressed() {
        main.loadAddBrand();
        stage.hide();
        stage.close();
    }

    public void onElimPressed() {
        if (itemInstance != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                ServicesLocator.getMarcaServices().elimBrand(itemInstance);
                List<Marca> tableItems = ServicesLocator.getMarcaServices().retriveAllBrands();
                table.setItems(FXCollections.observableList(tableItems));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la tabla");
        }
    }

}
