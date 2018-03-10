/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import UtilsLayer.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luisito
 */
public class ServicesLocator {

    private static Connection connection;
    private static AutoServices autoServices;
    private static ChoferServices choferServices;
    private static CategoriasServices categoriasServices;
    private static ContratoServices contratoServices;
    private static FormaPagoServices formaPagoServices;
    private static MarcaServices marcaServices;
    private static ModeloServices modeloServices;
    private static PaisServices paisServices;
    private static SituacionServices situacionServices;
    private static TuristaServices turistaServices;
    private static TarifasServices tarifasServices;
    private static UserServices userServices;
    private static RoleServices rolServices;
    private static LoginServices loginServices;
    private static ServicesLocator servicesLocator;
    private static ReportServices reportServices;

    /**
     * @return the userServices
     */
    public static UserServices getUserServices() {
        if (userServices == null) {
            userServices = new UserServices();
        }
        return userServices;
    }

    /**
     * @return the rolServices
     */
    public static RoleServices getRolServices() {
        if (rolServices == null) {
            rolServices = new RoleServices();
        }
        return rolServices;
    }

    /**
     * @return the loginServices
     */
    public static LoginServices getLoginServices() {
        if (loginServices == null) {
            loginServices = new LoginServices();
        }
        return loginServices;
    }

    /**
     *
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public ServicesLocator() {
        try {
            connection = new Connection();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en el construct sl");
            e.printStackTrace();

        } catch (Exception ex) {
            Logger.getLogger(ServicesLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public static ServicesLocator getServicesInstance() {
        if (servicesLocator == null) {
            servicesLocator = new ServicesLocator();
        }
        return servicesLocator;
    }

    /**
     *
     * @return
     */
    public static java.sql.Connection getConnection() {
        try {
            if (connection.getConnection() != null) {

                return connection.getConnection();
            }
        } catch (Exception e) {
            System.out.println("Null conection class ServicesLocator");
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static AutoServices getAutoServices() {
        if (autoServices == null) {
            autoServices = new AutoServices();
        }
        return autoServices;
    }

    /**
     *
     * @return
     */
    public static ChoferServices getChoferServices() {
        if (choferServices == null) {
            choferServices = new ChoferServices();
        }
        return choferServices;
    }

    /**
     *
     * @return
     */
    public static CategoriasServices getCategoriasServices() {
        if (categoriasServices == null) {
            categoriasServices = new CategoriasServices();
        }
        return categoriasServices;
    }

    /**
     * @return the contratoServices
     */
    public static ContratoServices getContratoServices() {
        if (contratoServices == null) {
            contratoServices = new ContratoServices();
        }
        return contratoServices;
    }

    /**
     * @return the formaPagoServices
     */
    public static FormaPagoServices getFormaPagoServices() {
        if (formaPagoServices == null) {
            formaPagoServices = new FormaPagoServices();
        }
        return formaPagoServices;
    }

    /**
     * @return the marcaServices
     */
    public static MarcaServices getMarcaServices() {
        if (marcaServices == null) {
            marcaServices = new MarcaServices();
        }
        return marcaServices;
    }

    /**
     * @return the modeloServices
     */
    public static ModeloServices getModeloServices() {
        if (modeloServices == null) {
            modeloServices = new ModeloServices();
        }
        return modeloServices;
    }

    /**
     * @return the paisServices
     */
    public static PaisServices getPaisServices() {
        if (paisServices == null) {
            paisServices = new PaisServices();
        }
        return paisServices;
    }

    /**
     * @return the situacionServices
     */
    public static SituacionServices getSituacionServices() {
        if (situacionServices == null) {
            situacionServices = new SituacionServices();
        }
        return situacionServices;
    }

    /**
     * @return the turistaServices
     */
    public static TuristaServices getTuristaServices() {
        if (turistaServices == null) {
            turistaServices = new TuristaServices();
        }
        return turistaServices;
    }

    /**
     * @return the tarifasServices
     */
    public static TarifasServices getTarifasServices() {
        if (tarifasServices == null) {
            tarifasServices = new TarifasServices();
        }
        return tarifasServices;
    }

    /**
     *
     * @return
     */
    public static ReportServices getReportServices() {
        if (reportServices == null) {
            reportServices = new ReportServices();
        }
        return reportServices;
    }

    public static boolean testConnection() {
        try {
            return ServicesLocator.getConnection().getAutoCommit();
        } catch (Exception ex) {
            return false;
        }

    }
}
