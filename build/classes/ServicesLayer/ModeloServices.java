/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Modelo;
import java.sql.CallableStatement;
import java.sql.Connection;
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
public class ModeloServices {

    private final String insertionErrorMessage = "El modelo que intenta insertar ya existe";
    private final String modificationErrorMessage = "El modelo que intenta modificar no existe";

    @SuppressWarnings("CallToPrintStackTrace")
    public void insertModel(Modelo m) throws InsertionModificationException {
        validateInsertionModification(m, true);
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{call insertar_modelo(?,?,?,?)}";
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, m.getModel());
            statement.setString(2, m.getBrand());
            statement.setString(3, m.getNormalTarif());
            statement.setString(4, m.getSpeciallTarif());
            statement.executeQuery();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void elimModel(Modelo m) {
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{call eliminar_modelo(?,?)}";
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, m.getModel());
            statement.setString(2, m.getBrand());
            statement.executeQuery();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Modelo> retriveAllModels() {
        List<Modelo> list = new LinkedList<>();
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{?=call buscar_modelos()}";
            connection.setAutoCommit(false);
            CallableStatement statement = connection.prepareCall(function);
            statement.registerOutParameter(1, Types.OTHER);
            statement.execute();
            ResultSet rs = (ResultSet) statement.getObject(1);
            while (rs.next()) {
                list.add(new Modelo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }

        } catch (Exception e) {

        }
        return list;
    }

    public Modelo retriveModel(String model, String brand) {
        Modelo m = null;
        try (Connection con = ServicesLocator.getConnection()) {
//            String sqlConsult="SELECT modelos.nombre_modelo, "
//                    + "marcas.nombre_marca "
//                    + "FROM modelos "
//                    + "JOIN marcas "
//                    + "ON modelos.modelo_id_marca=marcas.id_marca "
//                    + "WHERE modelos.nombre_modelo=? and "
//                    + "marcas.nombre_marca=?";
//            PreparedStatement prepStatement=con.prepareStatement(sqlConsult);
            con.setAutoCommit(false);
            String f = "{?=call buscar_modelo(?,?)}";
            CallableStatement prepStatement = con.prepareCall(f);
            prepStatement.registerOutParameter(1, Types.OTHER);
            prepStatement.setString(2, model);
            prepStatement.setString(3, brand);
            prepStatement.execute();
            ResultSet rs = (ResultSet) prepStatement.getObject(1);
            while (rs.next()) {
                m = new Modelo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
            con.close();
        } catch (Exception e) {

        }
        return m;
    }

    public void modifyModel(Modelo m) throws InsertionModificationException {
        validateInsertionModification(m, false);
        try (Connection con = ServicesLocator.getConnection()) {
            String sql = "{call modificar_modelo(?,?,?,?)}";
            CallableStatement cs = con.prepareCall(sql);
            cs.setString(1, m.getModel());
            cs.setString(2, m.getBrand());
            cs.setString(3, m.getNormalTarif());
            cs.setString(4, m.getSpeciallTarif());
            cs.executeQuery();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void validateInsertionModification(Modelo m, boolean insertion) throws InsertionModificationException {
        Modelo mtemp = retriveModel(m.getModel(), m.getBrand());
        if (insertion && mtemp != null) {
            throw new InsertionModificationException(insertionErrorMessage);
        } else if (!insertion && mtemp == null) {
            throw new InsertionModificationException(modificationErrorMessage);
        }
    }
}
