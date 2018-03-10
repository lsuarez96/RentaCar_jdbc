package UtilsLayer;

import java.sql.Connection;
import java.util.Map;
import javax.swing.UnsupportedLookAndFeelException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class JasperManager {

    /*
     Map parameters = null;

     if(parameterName!=null && parameterValue!=null){
     parameters = new HashMap();
     parameters.put(parameterName, parameterValue);
     }
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void showReport(Map parameters, String reportFile, Connection connection) {

        JasperPrint print;
        try {
            print = JasperFillManager.fillReport(reportFile, parameters, connection);
            {
                try {
                    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
