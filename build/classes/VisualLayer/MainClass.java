/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Auto;
import ModelLayer.Chofer;
import ModelLayer.Contrato;
import ModelLayer.LoggedRole;
import ModelLayer.Modelo;
import ModelLayer.Turista;
import ModelLayer.User;
import ServicesLayer.ServicesLocator;
import UtilsLayer.Configuration;
import UtilsLayer.ConfigurationFileException;
import UtilsLayer.ConnectionFailureException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 *
 * @author Luisito
 */
@SuppressWarnings("UseSpecificCatch")
public class MainClass extends Application {

    private LoggedRole loggedRole;
    private User loggedUsser;
    private List<LoggedRole> loggedRoles;

    @Override
    public void start(Stage primaryStage) {
        boolean con = true;
        try {
            Configuration.getConfigurationInstance();
            ServicesLocator.getServicesInstance();
            if (!ServicesLocator.testConnection()) {
                JOptionPane.showMessageDialog(null, "Connection configuration failure");
                con = false;
            }
        } catch (ConnectionFailureException | ConfigurationFileException ex) {
            con = false;
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if (!con) {
            loadConfiguration();
        } else {
            loadUserLoginView();
        }
    }

    public void loadCarsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CarsView.fxml"));
            Stage stage = new Stage();
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Gestor de autos...");
            CarsViewController controller = (CarsViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadTuristsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TuristsView.fxml"));
            Stage stage = new Stage();
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setTitle("Gestor de turistas...");
            stage.setScene(scene);
            TuristsViewController controller = (TuristsViewController) loader.getController();
            controller.setStage(stage);
            controller.setMainClass(this);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadDriversView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DriversView.fxml"));
            Stage stage = new Stage();
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setTitle("Gestor de choferes...");
            stage.setScene(scene);
            DriversViewController controller = (DriversViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadContractsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ContractsView.fxml"));
            Stage stage = new Stage();
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Gestor de contratos...");
            ContractsViewController controller = (ContractsViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);

            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void loadAddorModifyCar(Auto a) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddorModifyCar.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            if (a == null) {
                stage.setTitle("Insertar auto...");
            } else {
                stage.setTitle("Modificar auto");
            }
            AddorModifyCarController controller = (AddorModifyCarController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setAutoInstance(a);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadAddOrModifyTurist(Turista t) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddorModifyTurist.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            if (t == null) {
                stage.setTitle("Insertar Turista...");
            } else {
                stage.setTitle("Modificar Turista...");
            }
            AddorModifyTuristController controller = (AddorModifyTuristController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setItemInstance(t);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadAddorModifyDriver(Chofer c) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddorModifyDriver.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            if (c == null) {
                stage.setTitle("Insertar Chofer...");
            } else {
                stage.setTitle("Modificar Chofer...");
            }
            AddorModifyDriverController controller = (AddorModifyDriverController) loader.getController();
            controller.setItemInstance(c);
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadAddorModifyModel(Modelo m) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddorModifyModel.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            if (m == null) {
                stage.setTitle("Insertar Modelo...");
            } else {
                stage.setTitle("Modificar Modelo...");
            }
            AddorModifyModelController controller = (AddorModifyModelController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setModelInstance(m);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAddBrand() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBrand.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setTitle("Insertar Marca...");
            stage.setScene(scene);
            AddBrandController controller = (AddBrandController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadModelsView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModelsView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Gestionar Modelos...");
            ModelsViewController controller = (ModelsViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadBrandsView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BrandsView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Gestionar Marcas...");
            BrandsViewController controller = (BrandsViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadAddModifyTariff() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyTarif.fxml"));
        Stage stage = new Stage();
        try {
            //loader.load(getClass().getResource("AddModifyContract.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setTitle("Agregar Tarifa...");
            stage.setScene(scene);
            AddModifyTarifController controller = (AddModifyTarifController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);

            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadTariffView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TariffView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setTitle("Gestionar Tarifas...");
            stage.setScene(scene);
            TariffViewController controller = (TariffViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAddCat() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCategory.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Insertar Categoria...");
            AddCategoryController controller = (AddCategoryController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            // Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadCatView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CategoryView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Gestionar Categorias...");
            CategoryViewController controller = (CategoryViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);

            stage.show();
        } catch (IOException ex) {
            // Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadPayFormView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PayFormView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Formas de Pago...");
            PayFormViewController controller = (PayFormViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadStartPage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartPage.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setTitle("Pagina de inicio...");
            stage.setScene(scene);
            StartPageController controller = (StartPageController) loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("static-access")
    public void loadAddModifyContract(Contrato c) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyContract.fxml"));
        Stage stage = new Stage();
        try {
            //loader.load(getClass().getResource("AddModifyContract.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddModifyContractController controller = (AddModifyContractController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setItemInstance(c);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void loadAddCountry() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyCountry.fxml"));
        Stage stage = new Stage();
        try {
            //loader.load(getClass().getResource("AddModifyContract.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddModifyCountryController controller = (AddModifyCountryController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadCountriesView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CountryView.fxml"));
        Stage stage = new Stage();
        try {
            //loader.load(getClass().getResource("AddModifyContract.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Gestionar Paises...");
            CountryViewController controller = (CountryViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the loggedRole
     */
    public LoggedRole getLoggedRole() {
        return loggedRole;
    }

    /**
     * @param loggedRole the loggedRole to set
     */
    public void setLoggedRole(LoggedRole loggedRole) {
        this.loggedRole = loggedRole;
    }

    public void loadAddLogin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddLogin.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddLoginController controller = loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAddLogin(User u) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddLogin.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddLoginController controller = loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            controller.setUserInstance(u);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAddUser(User u) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AddUserController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setItemInstance(u);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    public void loadUserView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            UsersViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAdminView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AdminViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
    public void loadUserLoginView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            LoginViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadConfiguration() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfigureConectionView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            //stage.setAlwaysOnTop(true);
            ConfigureConectionViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAboutView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            //stage.setAlwaysOnTop(true);
            AboutController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadRolUserView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RolUserView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            //stage.setAlwaysOnTop(true);
            RolUserViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadChangePassword(User u) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Password.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            //stage.setAlwaysOnTop(true);
            PasswordController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setUserInstance(u);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the loggedRoles
     */
    public List<LoggedRole> getLoggedRoles() {
        return loggedRoles;
    }

    /**
     * @param loggedRoles the loggedRoles to set
     */
    public void setLoggedRoles(List<LoggedRole> loggedRoles) {
        this.loggedRoles = loggedRoles;
    }

    public void setLoggedUsser(User u) {
        this.loggedUsser = u;
    }

    /**
     * @return the loggedUsser
     */
    public User getLoggedUsser() {
        return loggedUsser;
    }

}
