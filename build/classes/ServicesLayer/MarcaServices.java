/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Marca;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Luisito
 */
public class MarcaServices {

    private final String insertionErrorMessage = "La marca que intenta insertar ya existe";

    @SuppressWarnings("CallToPrintStackTrace")
    public void insertBrand(Marca m) throws InsertionModificationException {
        validateInsertion(m);
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{call insertar_marca(?)}";
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, m.getBrand());
            statement.executeQuery();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void elimBrand(Marca m) {
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{call eliminar_marca(?)}";
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, m.getBrand());
            statement.executeQuery();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Marca> retriveAllBrands() {
        List<Marca> list = new LinkedList<>();
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{?=call buscar_marcas()}";
            connection.setAutoCommit(false);
            CallableStatement statement = connection.prepareCall(function);
            statement.registerOutParameter(1, Types.OTHER);
            statement.execute();
            ResultSet rs = (ResultSet) statement.getObject(1);
            while (rs.next()) {
                list.add(new Marca(rs.getString(1)));

            }
            connection.close();
        } catch (Exception e) {

        }
        return list;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Marca retriveBrand(String id_brand) {
        Marca m = null;
        try (Connection con = ServicesLocator.getConnection()) {
            // con.setAutoCommit(false);
            String sql = "SELECT nombre_marca "
                    + "FROM marcas "
                    + "WHERE nombre_marca=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id_brand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m = new Marca(rs.getString(1));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    private void validateInsertion(Marca m) throws InsertionModificationException {
        if (retriveBrand(m.getBrand()) != null) {
            throw new InsertionModificationException(insertionErrorMessage);
        }
    }
}
