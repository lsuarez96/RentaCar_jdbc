/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Chofer;
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
public class ChoferServices {

    private final String insertionErrorMessage = "El chofer que intenta insertar ya existe!!!!";
    private final String modificationErrorMessage = "El chofer que intenta modificar no existe!!!!";

    @SuppressWarnings("CallToPrintStackTrace")
    public void insertDriver(Chofer c) throws InsertionModificationException {
        validateInsertionModification(c, true);
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call insertar_chofer(?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, c.getId());
                statement.setString(2, c.getName());
                statement.setString(3, c.getLastName());
                statement.setString(4, c.getCategory());
                statement.setString(5, c.getAddress());
                statement.executeQuery();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void modifyDriver(Chofer c) throws InsertionModificationException {
        validateInsertionModification(c, false);
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call modificar_chofer(?,?,?,?,?,?)}";
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, c.getId());
                statement.setString(2, c.getName());
                statement.setString(3, c.getLastName());
                statement.setString(4, c.getCategory());
                statement.setString(5, c.getAddress());
                statement.setInt(6, c.getIdChof());
                statement.executeQuery();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void elimDriver(String id) {
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call eliminar_chofer(?)}";
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, id);
                statement.executeQuery();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Chofer retriveDriver(String id) {
        Chofer c = null;
        ResultSet rs;
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "SELECT "
                        + "numero_id, "
                        + "nombre, "
                        + "apellidos, "
                        + "tipo_categoria, "
                        + "direccion, "
                        + "id_chofer "
                        + "FROM choferes JOIN categorias "
                        + "ON categoria=id_cat "
                        + "WHERE numero_id=?";
                PreparedStatement statement = connection.prepareStatement(function);
//                // PreparedStatement statement=connection.prepareStatement(function);
                //connection.setAutoCommit(false);
//                //statement.registerOutParameter(1, java.sql.Types.OTHER);
                statement.setString(1, id);
//
                rs = (ResultSet) statement.executeQuery();
                while (rs.next()) {
                    c = new Chofer(rs.getInt(6), rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4), rs.getString(5));
                }
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Chofer retriveDriver(int id) {
        Chofer c = null;
        ResultSet rs;
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "SELECT "
                        + "choferes.numero_id, "
                        + "choferes.nombre ,"
                        + "choferes.apellidos ,"
                        + "choferes.categoria ,"
                        + "choferes.direccion "
                        + "choferes.id_chofer"
                        + "FROM choferes "
                        + "WHERE choferes.id_chofer=?";
                PreparedStatement statement = connection.prepareStatement(function);
//                // PreparedStatement statement=connection.prepareStatement(function);
                //connection.setAutoCommit(false);
//                //statement.registerOutParameter(1, java.sql.Types.OTHER);
                statement.setInt(1, id);
//
                rs = (ResultSet) statement.executeQuery();
                while (rs.next()) {
                    c = new Chofer(rs.getInt(6), rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4), rs.getString(5));
                }
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<Chofer> retriveAllDrivers() {
        List<Chofer> list = new LinkedList<>();
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{?=call buscar_choferes()}";
                connection.setAutoCommit(false);
                CallableStatement statement = connection.prepareCall(function);
                statement.registerOutParameter(1, Types.OTHER);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    list.add(new Chofer(rs.getInt(6), rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void validateInsertionModification(Chofer c, boolean insertion) throws InsertionModificationException {
        Chofer ctemp = retriveDriver(c.getId());
        if (insertion && ctemp != null) {
            throw new InsertionModificationException(insertionErrorMessage);
        } else if (!insertion && ctemp == null) {
            throw new InsertionModificationException(modificationErrorMessage);
        }
    }

}
