/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Turista;
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
public class TuristaServices {

    private final String insertionErrorMessage = "El turista que intenta insertar ya existe";
    private final String modificationErrorMessage = "El turista que intenta modificar no existe";

    @SuppressWarnings("CallToPrintStackTrace")
    public void insertTurist(Turista t) throws InsertionModificationException {
        validateInsertionModification(t, true);
        try (Connection con = ServicesLocator.getConnection()) {
            String function = "{call insertar_turista(?,?,?,?,?,?,?)}";
            CallableStatement stat = con.prepareCall(function);
            stat.setString(1, t.getIdPassport());
            stat.setString(2, t.getName());
            stat.setString(3, t.getLastName());
            stat.setInt(4, t.getAge());
            stat.setString(5, t.getSex());
            stat.setString(6, t.getPhone());
            stat.setString(7, t.getCountry());
            stat.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void modifyTurist(Turista t) throws InsertionModificationException {
        validateInsertionModification(t, false);
        try (Connection con = ServicesLocator.getConnection()) {
            String function = "{call modificar_turista(?,?,?,?,?,?,?,?)}";
            CallableStatement stat = con.prepareCall(function);
            stat.setString(1, t.getIdPassport());
            stat.setString(2, t.getName());
            stat.setString(3, t.getLastName());
            stat.setInt(4, t.getAge());
            stat.setString(5, t.getSex());
            stat.setString(6, t.getPhone());
            stat.setString(7, t.getCountry());
            stat.setInt(8, t.getId());
            stat.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void elimTurist(Turista t) {
        try (Connection con = ServicesLocator.getConnection()) {
            String function = "{call eliminar_turista(?)}";
            CallableStatement stat = con.prepareCall(function);
            stat.setString(1, t.getIdPassport());
            stat.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void elimTurist(String id) {
        try (Connection con = ServicesLocator.getConnection()) {
            String function = "{call eliminar_turista(?)}";
            CallableStatement stat = con.prepareCall(function);
            stat.setString(1, id);
            stat.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Turista retriveTurista(String id_pass) {
        try (Connection con = ServicesLocator.getConnection()) {
            String function = "{?=call buscar_turista(?)}";
            con.setAutoCommit(false);
            CallableStatement stat = con.prepareCall(function);
            stat.registerOutParameter(1, Types.OTHER);
            stat.setString(2, id_pass);
            stat.execute();
            ResultSet rs = (ResultSet) stat.getObject(1);
            while (rs.next()) {
                return new Turista(rs.getInt(8), rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(4));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<Turista> retriveAllTurists() {
        List<Turista> list = new LinkedList<>();
        try (Connection con = ServicesLocator.getConnection()) {
            String fun = "{?=call buscar_turistas()}";
            con.setAutoCommit(false);
            CallableStatement st = con.prepareCall(fun);
            st.registerOutParameter(1, Types.OTHER);
            st.execute();
            ResultSet rs = (ResultSet) st.getObject(1);
            while (rs.next()) {
                list.add(new Turista(rs.getInt(8), rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(4)));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void validateInsertionModification(Turista t, boolean insertion) throws InsertionModificationException {
        Turista ttemp = retriveTurista(t.getIdPassport());
        if (insertion && ttemp != null) {
            throw new InsertionModificationException(insertionErrorMessage);
        } else if (!insertion && ttemp == null) {
            throw new InsertionModificationException(insertionErrorMessage);
        }

    }

}
