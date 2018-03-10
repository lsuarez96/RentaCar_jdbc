/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class AboutController implements Initializable {

    @FXML
    private Label appName;
    @FXML
    private Label developer1Name;
    @FXML
    private Label developer2Name;
    @FXML
    private Label tutorName;
    @FXML
    private Label developers;
    @FXML
    private Label tutor;

    private MainClass main;
    private Stage stage;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;

    }

    public EventHandler onShowing() {
        return (EventHandler) new EventHandler() {
            @Override
            public void handle(Event event) {
                Task task = new Task<Void>() {
                    @Override
                    public void run() {
                        try {
                            appName.setVisible(true);
                            Thread.sleep(300);
                            developers.setVisible(true);
                            Thread.sleep(100);
                            developer1Name.setVisible(true);
                            Thread.sleep(300);
                            developer2Name.setVisible(true);
                            Thread.sleep(300);
                            tutor.setVisible(true);
                            Thread.sleep(100);
                            tutorName.setVisible(true);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AboutController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    @Override
                    protected Void call() {
                        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        return null;
                    }
                };

                new Thread(task).start();
            }
        };
    }
}
