/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ModelLayer.Tarifas;
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
public class TarifasServices {

    @SuppressWarnings("CallToPrintStackTrace")
    public void insertTariff(Tarifas t) {
        try (Connection con = ServicesLocator.getConnection()) {
            String func = "{call insertar_tarifa(?,?)}";
            CallableStatement stat = con.prepareCall(func);

            stat.setString(1, t.getNormalPrice());
            stat.setString(2, t.getEspecialPrice());
            stat.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void elimTariff(Tarifas t) {
        try (Connection con = ServicesLocator.getConnection()) {
            String func = "{call eliminar_tarifa(?,?)}";
            CallableStatement stat = con.prepareCall(func);
            stat.setString(1, t.getNormalPrice());
            stat.setString(2, t.getEspecialPrice());
            stat.executeQuery();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Tarifas retriveTariff(String tarn, String tare) {
        Tarifas f = null;
        try (Connection con = ServicesLocator.getConnection()) {
            String func = "{?=call buscar_tarifa(?,?)}";
            con.setAutoCommit(false);
            CallableStatement stat = con.prepareCall(func);
            stat.registerOutParameter(1, Types.OTHER);
            stat.setString(2, tarn);
            stat.setString(3, tare);
            stat.execute();
            if (true) {
                ResultSet rs = (ResultSet) stat.getObject(1);
                while (rs.next()) {
                    f = new Tarifas(rs.getString(1), rs.getString(2));
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<Tarifas> retriveAllTariffs() {
        List<Tarifas> list = new LinkedList<>();
        try (Connection con = ServicesLocator.getConnection()) {
            String funct = "{?=call buscar_tarifas()}";
            CallableStatement stat = con.prepareCall(funct);
            con.setAutoCommit(false);
            stat.registerOutParameter(1, Types.OTHER);
            stat.execute();
            ResultSet rs = (ResultSet) stat.getObject(1);
            while (rs.next()) {
                list.add(new Tarifas(rs.getString(1), rs.getString(2)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
