/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Luisito
 */
public class ReportServices {

    public void loadTuristsByCountryReport() {
        String path = "src/Reports2/listado_turistas.jasper";

        Connection connection = ServicesLocator.getConnection();

        try {
            connection.setAutoCommit(false);
            JasperPrint jPrinter = JasperFillManager.fillReport(path, null, connection);
            JasperViewer jViewer = new JasperViewer(jPrinter, false);
            JasperExportManager.exportReportToPdf(jPrinter);
            jViewer.setVisible(true);
            connection.close();
        } catch (JRException ex) {
            System.err.println("Excepcion cargando el reporte de turistas por pais");
            System.out.println(ex.getCause());
        } catch (SQLException ex) {
            Logger.getLogger(ReportServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadCarsReport() {
        String path = "src/Reports2/listado_autos.jasper";
        Connection connection = ServicesLocator.getConnection();
        try {
            JasperPrint jPrinter = JasperFillManager.fillReport(path, null, connection);
            JasperViewer jViewer = new JasperViewer(jPrinter, false);
            jViewer.setVisible(true);
        } catch (JRException ex) {
            System.err.println("Excepcion cargando el reporte de Listado de autos");
            System.out.println(ex.getCause());
        }
    }

    public void loadListadoContratos() {
        String path = "src/Reports2/listado_contratos.jasper";
        Connection connection = ServicesLocator.getConnection();
        try {
            JasperPrint jPrinter = JasperFillManager.fillReport(path, null, connection);
            JasperViewer jViewer = new JasperViewer(jPrinter, false);
            jViewer.setVisible(true);
        } catch (JRException ex) {
            System.err.println("Excepcion cargando el reporte de turistas por pais");
            System.out.println(ex.getCause());
        }
    }

    public void loadListadoChoferes() {
        String path = "src/Reports2/listado_choferes.jasper";
        Connection connection = ServicesLocator.getConnection();
        try {
            JasperPrint jPrinter = JasperFillManager.fillReport(path, null, connection);
            JasperViewer jViewer = new JasperViewer(jPrinter, false);
            jViewer.setVisible(true);
        } catch (JRException ex) {
            System.err.println("Excepcion cargando el reporte de turistas por pais");
            System.out.println(ex.getCause());
        }
    }

    public void loadListadoSituaciones() {
        String path = "src/Reports2/listado_situaciones.jasper";
        Connection connection = ServicesLocator.getConnection();
        try {
            JasperPrint jPrinter = JasperFillManager.fillReport(path, null, connection);
            JasperViewer jViewer = new JasperViewer(jPrinter, false);
            jViewer.setVisible(true);
        } catch (JRException ex) {
            System.err.println("Excepcion cargando el reporte de turistas por pais");
            System.out.println(ex.getCause());
        }
    }

    public void loadListadoIncumplidores() {
        String path = "src/Reports2/turistas_incumplidores.jasper";
        Connection connection = ServicesLocator.getConnection();
        try {
            JasperPrint jPrinter = JasperFillManager.fillReport(path, null, connection);
            JasperViewer jViewer = new JasperViewer(jPrinter, false);
            jViewer.setVisible(true);
        } catch (JRException ex) {
            System.err.println("Excepcion cargando el reporte de turistas por pais");
            System.out.println(ex.getCause());
        }
    }

    public void loadContratPorMarcaModelo() {
        String path = "src/Reports2/contratos_marca_modelo.jasper";
        Connection connection = ServicesLocator.getConnection();
        try {
            JasperPrint jPrinter = JasperFillManager.fillReport(path, null, connection);
            JasperViewer jViewer = new JasperViewer(jPrinter, false);
            jViewer.setVisible(true);
        } catch (JRException ex) {
            System.err.println("Excepcion cargando el reporte de turistas por pais");
            System.out.println(ex.getCause());
        }
    }

    public void loadContratoPorPais() {
        String path = "src/Reports2/contratos_por_pais.jasper";
        Connection connection = ServicesLocator.getConnection();
        try {
            JasperPrint jPrinter = JasperFillManager.fillReport(path, null, connection);
            JasperViewer jViewer = new JasperViewer(jPrinter, false);
            jViewer.setVisible(true);
        } catch (JRException ex) {
            System.err.println("Excepcion cargando el reporte de turistas por pais");
            System.out.println(ex.getCause());
        }
    }

    public void loadIncomingIngress() {
        String path = "src/Reports2/ingresos_anuales2.jasper";
        Connection connection = ServicesLocator.getConnection();
        try {
            JasperPrint jPrinter = JasperFillManager.fillReport(path, null, connection);
            JasperViewer jViewer = new JasperViewer(jPrinter, false);
            jViewer.setVisible(true);
        } catch (JRException ex) {
            System.err.println("Excepcion cargando el reporte de turistas por pais");
            System.out.println(ex.getCause());
        }
    }

}
