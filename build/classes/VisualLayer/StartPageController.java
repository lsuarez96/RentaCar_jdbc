/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.LoggedRole;
import ServicesLayer.ServicesLocator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class StartPageController implements Initializable {

    private Stage stage;
    private MainClass main;
    @FXML
    private MenuItem userMenu;
    @FXML
    private Label userlb;
    @FXML
    private Menu auxtab;
    @FXML
    private Menu us;
    @FXML
    private Menu rep;
    @FXML
    private Label contlb;
    @FXML
    private Label carlb;
    @FXML
    private Label drivlb;
    @FXML
    private Label turlb;
    @FXML
    private Circle cont;
    @FXML
    private Circle tur;
    @FXML
    private Circle car;
    @FXML
    private Circle driv;
    @FXML
    private MenuBar barraMenu;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userlb.setTextFill(Color.DARKBLUE);
    }

    public void onContratoSelect() {
        main.loadContractsView();
    }

    public void onAutoSelect() {
        main.loadCarsView();
    }

    public void onChoferSelect() {
        main.loadDriversView();
    }

    public void onTuristaSelect() {
        main.loadTuristsView();
    }

    public void onModelsSelected() {
        main.loadModelsView();
    }

    public void onBrandsSelected() {
        main.loadBrandsView();
    }

    public void onTariffSelected() {
        main.loadTariffView();
    }

    public void onCategorySelected() {
        main.loadCatView();
    }

    public void onFormaPago() {
        main.loadPayFormView();
    }

    public void onCoutriesSelected() {
        main.loadCountriesView();
    }

    public void loadTuristByCountryReport() {
        ServicesLocator.getServicesInstance();
        ServicesLocator.getReportServices().loadTuristsByCountryReport();
    }

    public void loadCarsReport() {
        ServicesLocator.getServicesInstance();
        ServicesLocator.getReportServices().loadCarsReport();
    }

    public void loadContractsByBrandModelReport() {
        ServicesLocator.getServicesInstance();
        ServicesLocator.getReportServices().loadContratPorMarcaModelo();
    }

    public void loadDriversReports() {
        ServicesLocator.getServicesInstance();
        ServicesLocator.getReportServices().loadListadoChoferes();
    }

    public void loadSituationReport() {
        ServicesLocator.getServicesInstance();
        ServicesLocator.getReportServices().loadListadoSituaciones();
    }

    public void loadContractListReport() {
        ServicesLocator.getServicesInstance();
        ServicesLocator.getReportServices().loadListadoContratos();
    }

    public void loadIncumpReport() {
        ServicesLocator.getServicesInstance();
        ServicesLocator.getReportServices().loadListadoIncumplidores();
    }

    public void loadContractByCountryReport() {
        ServicesLocator.getServicesInstance();
        ServicesLocator.getReportServices().loadContratoPorPais();
    }

    public void loadIncomingsReport() {
        ServicesLocator.getServicesInstance();
        ServicesLocator.getReportServices().loadIncomingIngress();
    }

    public void close() {
        stage.close();
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.onCloseRequestProperty().set(closeAction());
        try {
            userlb.getStylesheets().remove(0, userlb.getStylesheets().size());
            //userlb.getStyleClass().remove(0, userlb.getStyleClass().size());
            userlb.setFont(new Font(18));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userlb.setTextFill(Color.DARKBLUE);
        userlb.setTooltip(new Tooltip());
        userlb.getTooltip().setText("Cerrar Sesión");
    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
        if (!main.getLoggedRoles().contains(LoggedRole.ADMIN)) {
            userMenu.setDisable(true);
            barraMenu.getMenus().remove(userMenu);
        }
        userlb.setTextFill(Color.DARKBLUE);
        if (!main.getLoggedRoles().contains(LoggedRole.DEPEND) && !main.getLoggedRoles().contains(LoggedRole.MANAG)) {
            this.auxtab.setDisable(true);
            this.car.setDisable(true);
            this.car.setVisible(false);
            this.carlb.setDisable(true);
            carlb.setVisible(false);
            this.cont.setDisable(true);
            cont.setVisible(false);
            this.contlb.setDisable(true);
            contlb.setVisible(false);
            this.driv.setDisable(true);
            driv.setVisible(false);
            this.drivlb.setDisable(true);
            drivlb.setVisible(false);
            this.rep.setDisable(true);

            this.tur.setDisable(true);
            tur.setVisible(false);
            this.turlb.setDisable(true);
            turlb.setVisible(false);
            barraMenu.getMenus().remove(auxtab);
            barraMenu.getMenus().remove(rep);
        }
        if (!main.getLoggedRoles().contains(LoggedRole.ADMIN)) {
            barraMenu.getMenus().remove(us);
        }
        userlb.setText(main.getLoggedUsser().getUserName());
        //userlb.setFont(new javafx.scene.text.Font(14));
        userlb.setTextFill(Color.DARKBLUE);
    }

    public EventHandler closeAction() {

        EventHandler eh = (EventHandler) (Event event) -> {
            stage.hide();
            stage.close();
        };
        return eh;
    }

    public void onAboutPressed() {
        main.loadAboutView();
    }

    public void closeSession() {
        main.loadUserLoginView();
        stage.hide();
        stage.close();
    }

    public void manageUsers() {
        main.loadRolUserView();
        stage.hide();
        stage.close();
    }

    public void onMouseOverUser() {
        userlb.setTextFill(Color.RED);
        userlb.setUnderline(true);
        userlb.getTooltip().show(userlb, 100, 15);
        userlb.getTooltip().setX(userlb.getLayoutX() + userlb.getWidth());
        userlb.getTooltip().setY(userlb.getLayoutY());
    }

    public void onMouseExited() {
        userlb.setTextFill(Color.DARKBLUE);
        userlb.setUnderline(false);
        userlb.getTooltip().hide();
    }

    public void somethingStupid() {
        userlb.setTextFill(Color.DARKBLUE);
    }

}
