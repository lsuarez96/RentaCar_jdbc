/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ModelLayer.Situacion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Luisito
 */
public class SituacionServices {

    @SuppressWarnings("CallToPrintStackTrace")
    public void inserSituation(Situacion s) {
        try (Connection con = ServicesLocator.getConnection()) {
            String function = "{call insertar_situacion(?)}";
            CallableStatement stat = con.prepareCall(function);
            stat.setString(1, s.getState());
            stat.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void elimSituation(Situacion s) {
        try (Connection con = ServicesLocator.getConnection()) {
            String function = "{call eliminar_situacion(?)}";
            CallableStatement stat = con.prepareCall(function);
            stat.setString(1, s.getState());
            stat.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<Situacion> retriveAllSituations() {
        List<Situacion> list = new LinkedList<>();
        try (Connection con = ServicesLocator.getConnection()) {
            String function = "{?=call buscar_situaciones()}";
            con.setAutoCommit(false);
            CallableStatement stat = con.prepareCall(function);
            stat.registerOutParameter(1, Types.OTHER);
            stat.execute();
            ResultSet rs = (ResultSet) stat.getObject(1);
            while (rs.next()) {
                list.add(new Situacion(rs.getString(1)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Situacion retriveSituacion(String situacion) {
        Situacion s = null;
        try (Connection con = ServicesLocator.getConnection()) {
            String sqlConsult = "SELECT situaciones.tipo_situacion FROM situaciones "
                    + "WHERE tipo_situacion=?";
            PreparedStatement prepStat = con.prepareStatement(sqlConsult);
            prepStat.setString(1, situacion);
            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
                s = new Situacion(rs.getString(1));
            }
        } catch (Exception e) {

        }
        return s;
    }
}
