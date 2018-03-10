/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Auto;
import ModelLayer.Chofer;
import ModelLayer.Contrato;
import ModelLayer.FormaPago;
import ModelLayer.Turista;
import ServicesLayer.ServicesLocator;
import com.sun.javafx.collections.ImmutableObservableList;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddModifyContractController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Contrato itemInstance;

    @FXML
    private ComboBox<Auto> carCB;
    @FXML
    private ComboBox<Turista> turistCB;
    @FXML
    private ComboBox<Chofer> driverCB;
    @FXML
    private DatePicker startDP;
    @FXML
    private DatePicker endDP;
    @FXML
    private DatePicker deliveryDP;
    @FXML
    private ComboBox<FormaPago> payCB;
    @FXML
    private TextField completTF;
    @FXML
    private ContextMenu completCM;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Auto> listaAutos = getAvailableCars();
        Auto[] carList = new Auto[listaAutos.size()];
        listaAutos.toArray(carList);
        List<Turista> listaTur = ServicesLocator.getTuristaServices().retriveAllTurists();
        Turista[] turList = new Turista[listaTur.size()];
        listaTur.toArray(turList);
        List<Chofer> chofList = ServicesLocator.getChoferServices().retriveAllDrivers();
        Chofer[] drivList = new Chofer[chofList.size()];
        chofList.toArray(drivList);
        List<FormaPago> fpList = ServicesLocator.getFormaPagoServices().retriveAllPayForms();
        FormaPago[] payList = new FormaPago[fpList.size()];
        fpList.toArray(payList);
        ObservableList<Auto> carItems = new ImmutableObservableList<>(carList);
        ObservableList<Turista> turItems = new ImmutableObservableList<>(turList);
        ObservableList<Chofer> drivItems = new ImmutableObservableList<>(drivList);
        ObservableList<FormaPago> payItems = new ImmutableObservableList<>(payList);
        carCB.setItems(carItems);
        turistCB.setItems(turItems);
        driverCB.setItems(drivItems);
        payCB.setItems(payItems);
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
    public void setItemInstance(Contrato itemInstance) {
        this.itemInstance = itemInstance;
        if (itemInstance != null) {
            carCB.getSelectionModel().select(ServicesLocator.getAutoServices().retriveCar(itemInstance.getPlate()));
            turistCB.getSelectionModel().select(ServicesLocator.getTuristaServices().retriveTurista(itemInstance.getIdTurist()));
            carCB.setEditable(false);
            turistCB.setEditable(false);
            driverCB.getSelectionModel().select(ServicesLocator.getChoferServices().retriveDriver(itemInstance.getIdDriver()));
            payCB.getSelectionModel().select(ServicesLocator.getFormaPagoServices().retrivePayForm(itemInstance.getPayment()));
            LocalDate l = LocalDate.parse(itemInstance.getStartDate().toString());
            startDP.setValue(LocalDate.parse(itemInstance.getStartDate().toString()));
            endDP.setValue(LocalDate.parse(itemInstance.getEndDate().toString()));
            try {
                deliveryDP.setValue(LocalDate.parse(itemInstance.getDeliveryDate().toString()));
            } catch (NullPointerException e) {

            }

        }
    }

    public void onAcceptPressed() {
        boolean exit = false;
        if (itemInstance != null) {
            String idTur = itemInstance.getIdTurist();
            String plate = itemInstance.getPlate();
            String idChof;
            try {
                idChof = driverCB.getSelectionModel().getSelectedItem().getId();
            } catch (NullPointerException e) {
                idChof = null;
            }
            String pay = payCB.getSelectionModel().getSelectedItem().getPayType();

            LocalDate startD = startDP.getValue();
            LocalDate endD = endDP.getValue();
            LocalDate deliveryD = deliveryDP.getValue();
            Date sDate = null;
            Date eDate = null;
            Date dDate = null;
            try {
                sDate = Date.valueOf(startD);
                eDate = Date.valueOf(endD);
                dDate = Date.valueOf(deliveryD);
            } catch (NullPointerException e) {

            }
            if (!(idTur.isEmpty() && plate.isEmpty() && pay.isEmpty() && sDate == null && eDate == null)) {
                Contrato c = new Contrato(plate, idTur, sDate, eDate, idChof, pay);
                int id_usuario = main.getLoggedUsser().getId();
                try {
                    ServicesLocator.getContratoServices().modifyContract(c, id_usuario);
                    if (dDate != null) {
                        c.setDeliveryDate(dDate);
                        ServicesLocator.getContratoServices().closeContract(c);
                    }
                    exit = true;
                } catch (InsertionModificationException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    Logger.getLogger(AddModifyContractController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            }
        } else {
            String idTur = turistCB.getSelectionModel().getSelectedItem().getIdPassport();
            String plate = carCB.getSelectionModel().getSelectedItem().getPlate();
            String idChof;
            try {
                idChof = driverCB.getSelectionModel().getSelectedItem().getId();
            } catch (NullPointerException e) {
                idChof = null;
            }
            String pay = payCB.getSelectionModel().getSelectedItem().getPayType();
            LocalDate startD = startDP.getValue();
            LocalDate endD = endDP.getValue();
            // LocalDate deliveryD = deliveryDP.getValue();
            Date sDate = null;
            Date eDate = null;

            try {
                sDate = Date.valueOf(startD);
                eDate = Date.valueOf(endD);
                // dDate = new Date(deliveryD.getYear(), deliveryD.getMonthValue(), deliveryD.getDayOfMonth());
            } catch (NullPointerException e) {

            }
            if (!(idTur.isEmpty() && plate.isEmpty() && pay.isEmpty() && sDate == null && eDate == null)) {
                Contrato c = new Contrato(plate, idTur, sDate, eDate, idChof, pay);
                if (ServicesLocator.getContratoServices().retriveContract(plate, idTur, sDate) == null) {
                    int id_usuario = main.getLoggedUsser().getId();
                    try {
                        ServicesLocator.getContratoServices().insertContract(c, id_usuario);
                        exit = true;
                    } catch (InsertionModificationException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        Logger.getLogger(AddModifyContractController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El contrato ya existe");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            }
        }
        if (exit) {
            main.loadContractsView();
            stage.hide();
            stage.close();
        }
    }

    public void keyPressedForLetters(KeyEvent e) {
        String c = e.getCharacter();
        if (Character.isDigit(c.charAt(0))) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Solo se admiten letras");
        }
    }

    public void keyPressedForDigits(KeyEvent e) {
        String c = e.getCharacter();
        if (Character.isLetter(c.charAt(0))) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Solo se admiten numeros");
        }
    }

    public List<Auto> getAvailableCars() {
        List<Auto> list = ServicesLocator.getAutoServices().retriveAllCars();
        Iterator<Auto> it = list.iterator();
        while (it.hasNext()) {
            if (!"libre".equalsIgnoreCase(it.next().getSituation())) {
                it.remove();
            }
        }
        return list;
    }

    public void onKeyPressedForFilter() {
        List<Auto> listaAutos = getAvailableCars();
        String[] completeResult = completTF.getText().split(" ");
        for (String completeResult1 : completeResult) {
            Iterator<Auto> it = listaAutos.iterator();
            while (it.hasNext()) {
                Auto tempA = it.next();
                String[] a = tempA.toString().split(" ");
                boolean exist = false;
                for (String res2 : a) {
                    if (res2.toLowerCase().matches(completeResult1) || res2.toLowerCase().startsWith(completeResult1)) {
                        exist = true;

                    }
                }
                if (!exist) {
                    it.remove();
                }
            }
        }
        Auto[] carList = new Auto[listaAutos.size()];
        listaAutos.toArray(carList);
        completCM.getItems().clear();
        Iterator<Auto> it = listaAutos.iterator();
        while (it.hasNext()) {
            MenuItem item = new MenuItem(it.next().toString());
            item.onActionProperty().set(onMenuItemSelected());
            completCM.getItems().add(item);
        }
        completCM.show(completTF, Side.BOTTOM, 0, 0);
        Auto[] carList2 = new Auto[getAvailableCars().size()];
        getAvailableCars().toArray(carList2);
        carCB.setItems(new ImmutableObservableList<>(carList2));
    }

    public EventHandler onMenuItemSelected() {
        return (EventHandler) (Event event) -> {
            //To change body of generated methods, choose Tools | Templates.
            completTF.setText(((MenuItem) event.getSource()).getText());
            List<Auto> list = getAvailableCars();
            Auto a = null;
            Iterator<Auto> it = list.iterator();
            boolean found = false;
            while (it.hasNext() && !found) {
                Auto temp = it.next();
                if (temp.toString().toLowerCase().equals(completTF.getText().toLowerCase())) {
                    a = temp;
                    found = true;
                }
            }
            carCB.getSelectionModel().select(a);
        };
    }

}
