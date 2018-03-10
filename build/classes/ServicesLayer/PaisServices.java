/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Pais;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Luisito
 */
public class PaisServices {

    private final String insertionErrorMessage = "El pais que intenta insertar ya existe";

    @SuppressWarnings("CallToPrintStackTrace")
    public void insertCountry(Pais p) throws InsertionModificationException {
        validateInsertion(p);
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{call insertar_pais(?)}";
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, p.getCountry());
            statement.executeQuery();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void eliminCountry(Pais p) {
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{call eliminar_pais(?)}";
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, p.getCountry());
            statement.executeQuery();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<Pais> retriveAllCountries() {
        List<Pais> list = new LinkedList<>();
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{?=call buscar_paises()}";
            connection.setAutoCommit(false);
            CallableStatement stat = connection.prepareCall(function);
            stat.registerOutParameter(1, Types.OTHER);
            stat.execute();
            ResultSet rs = (ResultSet) stat.getObject(1);
            while (rs.next()) {
                list.add(new Pais(rs.getString(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Pais retriveCountry(String id) {
        Pais p = null;
        try (Connection con = ServicesLocator.getConnection()) {
            String sqlCons = "{?=call buscar_pais(?)}";
            con.setAutoCommit(false);
            CallableStatement prepStat = con.prepareCall(sqlCons);
            prepStat.registerOutParameter(1, Types.OTHER);
            prepStat.setString(2, id);
            prepStat.execute();
            ResultSet rs = (ResultSet) prepStat.getObject(1);
//            String func = "{?=call buscar_pais(?)}";
//            CallableStatement stat = con.prepareCall(func);
//            stat.registerOutParameter(1, Types.VARCHAR);
//            stat.setString(2, id);
//            stat.execute();
//            ResultSet rs = (ResultSet) stat.getObject(1);
            while (rs.next()) {
                p = new Pais(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    private void validateInsertion(Pais p) throws InsertionModificationException {
        Pais ptemp = retriveCountry(p.getCountry());
        if (ptemp != null) {
            throw new InsertionModificationException(insertionErrorMessage);
        }
    }
}
