/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Turista;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
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
public class TuristsViewController implements Initializable {

    @FXML
    private TableView<Turista> table;
    @FXML
    private TableColumn<Turista, String> passportCol;
    @FXML
    private TableColumn<Turista, String> nameCol;
    @FXML
    private TableColumn<Turista, String> lastNameCol;
    @FXML
    private TableColumn<Turista, Integer> ageCol;
    @FXML
    private TableColumn<Turista, String> sexCol;
    @FXML
    private TableColumn<Turista, String> phoneCol;
    @FXML
    private TableColumn<Turista, String> countryCol;

    private Stage stage;

    private MainClass mainClass;

    private Turista itemInstance;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        passportCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getIdPassport()));
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getName()));
        lastNameCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getLastName()));
        ageCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, Integer> cellData) -> (ObservableValue) new SimpleIntegerProperty(cellData.getValue().getAge()));
        sexCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getSex()));
        phoneCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getPhone()));
        countryCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getCountry()));
        ServicesLocator.getServicesInstance();
        table.setItems(FXCollections.observableList(ServicesLocator.getTuristaServices().retriveAllTurists()));
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
     * @param mainClass the mainClass to set
     */
    public void setMainClass(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    public void onItemSelected(Turista t) {
        itemInstance = t;
    }

    public void onAddPressed() {
        mainClass.loadAddOrModifyTurist(null);
        stage.hide();
        stage.close();
        //table.setItems(FXCollections.observableList(ServicesLocator.getTuristaServices().retriveAllTurists()));
    }

    public void onModifyPressed() {
        mainClass.loadAddOrModifyTurist(itemInstance);
        stage.hide();
        stage.close();
        table.setItems(FXCollections.observableList(ServicesLocator.getTuristaServices().retriveAllTurists()));
    }

    public void onElimPressed() {
        int op = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar este item, \n esta accion es irreversible!!!", "Eliminar turista", JOptionPane.YES_NO_OPTION);
        if (itemInstance != null) {
            if (op == JOptionPane.YES_NO_OPTION) {
                ServicesLocator.getTuristaServices().elimTurist(itemInstance);
                table.setItems(FXCollections.observableList(ServicesLocator.getTuristaServices().retriveAllTurists()));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un item de la tabla");
        }
        // table.setItems(FXCollections.observableList(ServicesLocator.getTuristaServices().retriveAllTurists()));
    }

}
