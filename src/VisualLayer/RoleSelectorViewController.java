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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class RoleSelectorViewController implements Initializable {

    private Stage stage;
    private MainClass main;

    @FXML
    private Label admIMG;
    @FXML
    private Label mngIMG;
    @FXML
    private Label depIMG;
    @FXML
    private ImageView adm;
    @FXML
    private ImageView mng;
    @FXML
    private ImageView dep;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        admIMG.setTooltip(new Tooltip("El administrador es el encargado de gestionar\n los usuarios"));
        //admIMG.getTooltip().setX(admIMG.getLayoutX() + 5);
        // admIMG.getTooltip().setY(admIMG.getLayoutY() - admIMG.getHeight());
        mngIMG.setTooltip(new Tooltip("El gerente tiene permisos para gestionar y visualizar todos los datos"));
        // mngIMG.getTooltip().setX(mngIMG.getLayoutX() + 5);
        //mngIMG.getTooltip().setX(mngIMG.getLayoutY() - mngIMG.getHeight());
        depIMG.setTooltip(new Tooltip("Similar al gerente pero con algunas restricciones \n en cuanto a la manipulacion de la informacion"));
        //depIMG.getTooltip().setX(depIMG.getLayoutX() + 5);
        //depIMG.getTooltip().setX(depIMG.getLayoutY() - depIMG.getHeight());
        ServicesLocator.getServicesInstance();
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

    public void onAdminPressed() {
        main.setLoggedRole(LoggedRole.ADMIN);
        main.loadUserLoginView();
        stage.hide();
    }

    public void onManagerPressed() {
        main.setLoggedRole(LoggedRole.MANAG);
        main.loadUserLoginView();
        stage.hide();
    }

    public void onDependPressed() {
        main.setLoggedRole(LoggedRole.DEPEND);
        main.loadUserLoginView();
        stage.hide();
    }

    public void onMouseOver(Event e) {
        Label l = (Label) e.getSource();
        l.setUnderline(true);
        Lighting lig = new Lighting();
        lig.getLight().colorProperty().set(Color.FIREBRICK);
        if (l.getText().equalsIgnoreCase("administrador")) {
            adm.setEffect(lig);
            admIMG.getTooltip().show(stage, 4, 5);
//            admIMG.getTooltip().setX(admIMG.getLayoutX() + 5);
//            admIMG.getTooltip().setY(admIMG.getLayoutY() - admIMG.getHeight());
        } else if (l.getText().equalsIgnoreCase("gerente")) {
            mng.setEffect(lig);
            mngIMG.getTooltip().show(stage, 4, 5);
//            mngIMG.getTooltip().setX(mngIMG.getLayoutX() + 5);
//            mngIMG.getTooltip().setX(mngIMG.getLayoutY() - mngIMG.getHeight());
        } else if (l.getText().equalsIgnoreCase("dependiente")) {
            dep.setEffect(lig);
            depIMG.getTooltip().show(stage, 4, 5);
//            depIMG.getTooltip().setX(depIMG.getLayoutX() + 5);
//            depIMG.getTooltip().setX(depIMG.getLayoutY() - depIMG.getHeight());
        }
    }

    public void onMouseExit(Event e) {
        Label l = (Label) e.getSource();
        l.setUnderline(false);
        l.getTooltip().hide();
        if (l.getText().equalsIgnoreCase("administrador")) {
            adm.effectProperty().setValue(null);
        } else if (l.getText().equalsIgnoreCase("gerente")) {
            mng.effectProperty().setValue(null);
        } else if (l.getText().equalsIgnoreCase("dependiente")) {
            dep.effectProperty().setValue(null);
        }
    }
}
