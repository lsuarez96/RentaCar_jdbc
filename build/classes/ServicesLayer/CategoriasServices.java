/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Categorias;
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
public class CategoriasServices {

    private final String insertionErrorMessage = "La categoria que intenta insertar ya existe!!!!";

    @SuppressWarnings("CallToPrintStackTrace")
    public void insertCategory(Categorias c) throws InsertionModificationException {
        validateInsertion(c);
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call insertar_categoria(?)}";
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, c.getCategory());
                statement.executeQuery();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void elimCategory(String catCod) {
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call eliminar_categoria(?)}";
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, catCod);
                statement.executeQuery();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<Categorias> retriveAllCategories() {
        List<Categorias> list = new LinkedList<>();
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{?=call buscar_categorias()}";
                CallableStatement statement = connection.prepareCall(function);
                connection.setAutoCommit(false);
                statement.registerOutParameter(1, Types.OTHER);

                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    list.add(new Categorias(rs.getString(1)));
                }
//                statement.executeQuery();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Categorias retriveCategory(String cat) {
        try (Connection con = ServicesLocator.getConnection()) {
            String sql = "SELECT tipo_categoria "
                    + "FROM categorias "
                    + "WHERE tipo_categoria=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Categorias(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriasServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void validateInsertion(Categorias c) throws InsertionModificationException {
        Categorias catTemp = retriveCategory(c.getCategory());
        if (catTemp != null) {
            throw new InsertionModificationException(insertionErrorMessage);
        }
    }
}
