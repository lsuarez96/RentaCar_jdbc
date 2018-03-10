/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ModelLayer.FormaPago;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luisito
 */
public class FormaPagoServices {

    public void insertPayForm(FormaPago fp) {
        Connection connection = ServicesLocator.getConnection();
        String function = "{insertar_forma_pago(?)}";
        try {
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, fp.getPayType());
            statement.executeQuery();
            connection.close();
        } catch (SQLException ex) {
            // Logger.getLogger(FormaPagoServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void elimPayForm(String payType) {
        Connection connection = ServicesLocator.getConnection();
        String function = "{eliminar_forma_pago(?)}";
        try {
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, payType);
            statement.executeQuery();
            connection.close();
        } catch (SQLException ex) {
            // Logger.getLogger(FormaPagoServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void elimPayForm(FormaPago fp) {
        Connection connection = ServicesLocator.getConnection();
        String function = "{eliminar_forma_pago(?)}";
        try {
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, fp.getPayType());
            statement.executeQuery();
            connection.close();
        } catch (SQLException ex) {
            // Logger.getLogger(FormaPagoServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<FormaPago> retriveAllPayForms() {
        List<FormaPago> list = new LinkedList<>();
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{?=call buscar_formas_de_pago()}";
                connection.setAutoCommit(false);
                CallableStatement statement = connection.prepareCall(function);
                statement.registerOutParameter(1, Types.OTHER);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    list.add(new FormaPago(rs.getString(1)));

                }
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public FormaPago retrivePayForm(String id_pay) {
        FormaPago f = null;
        try (Connection con = ServicesLocator.getConnection()) {
            con.setAutoCommit(false);
            String sql = "SELECT tipo_pago FROM "
                    + "forma_pago WHERE"
                    + " tipo_pago=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id_pay);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                f = new FormaPago(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormaPagoServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }
}
