/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Auto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Luisito
 */
public class AutoServices {

    private final String insertErrorMessage = "El auto que intenta insertar ya existe!!!!";
    private final String modifyErrorMessage = "El auto que intenta modificar no existe!!!!";

    @SuppressWarnings("CallToPrintStackTrace")
    public void insertCar(Auto a) throws InsertionModificationException {
        validateInsertionModification(a, true);
        try {
            try (Connection connection = ServicesLocator.getConnection()) {//pide la conexion
                String function = "{call insertar_auto(?,?,?,?,?,?)}";//declaras la funcion que vas a llamar
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, a.getPlate());
                statement.setString(2, a.getSituation());
                statement.setString(3, a.getModel());
                statement.setString(4, a.getBrand());
                statement.setFloat(5, a.getKm());
                statement.setString(6, a.getColor());
                statement.executeQuery();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyCar(Auto a) throws InsertionModificationException {
        validateInsertionModification(a, false);
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call modificar_auto(?,?,?,?,?,?,?)}";//CallableStatement se usa para invocarl funciones almacenadas en la base de datos
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, a.getPlate());
                statement.setString(2, a.getSituation());
                statement.setString(3, a.getModel());
                statement.setString(4, a.getBrand());
                statement.setFloat(5, a.getKm());
                statement.setString(6, a.getColor());
                statement.setInt(7, a.getIdCar());
                statement.executeQuery();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void elimCar(String plate) {
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call eliminar_auto(?)}";
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, plate);
                statement.executeQuery();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void elimCar(Auto a) {
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{call eliminar_auto(?)}";
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, a.getPlate());
            statement.executeQuery();
            connection.close();
        } catch (SQLException e) {

        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Auto retriveCar(String plate) {
        Auto a = null;
        ResultSet rs;
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{?=call buscar_auto(?)}";
                connection.setAutoCommit(false);
                CallableStatement statement = connection.prepareCall(function);
                // PreparedStatement statement=connection.prepareStatement(function);
                // statement.registerOutParameter(1,java.sql.Types.OTHER);
                statement.registerOutParameter(1, Types.OTHER);
                statement.setString(2, plate);

                statement.execute();
                rs = (ResultSet) statement.getObject(1);

                while (rs.next()) {
                    a = new Auto(rs.getInt(7), rs.getString(1),
                            rs.getString(4),
                            rs.getString(2),
                            rs.getString(3), rs.getString(5), rs.getFloat(6));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public Auto retriveCar(int id) {
        Auto a = null;
        ResultSet rs;
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{?=call buscar_auto(?)}";//?significa en este caso que esl valor que retorna la funcion se va a almacenar ay
                connection.setAutoCommit(false);
                CallableStatement statement = connection.prepareCall(function);
                // PreparedStatement statement=connection.prepareStatement(function);
                // statement.registerOutParameter(1,java.sql.Types.OTHER);
                statement.registerOutParameter(1, Types.OTHER);//esta funcion resive la posicion del"?" y el tipo de dato que va a devolver, es decir que va a registrar el primer "?" como parametro de salida
                statement.setInt(2, id);

                statement.execute();
                rs = (ResultSet) statement.getObject(1);

                while (rs.next()) {
                    a = new Auto(rs.getInt(7), rs.getString(1),
                            rs.getString(4),
                            rs.getString(2),
                            rs.getString(3), rs.getString(5), rs.getFloat(6));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<Auto> retriveAllCars() {
        List<Auto> temp = new ArrayList<>();
        Auto a;
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{?=call buscar_autos()}";
                connection.setAutoCommit(false);
                CallableStatement statement = connection.prepareCall(function);
                statement.registerOutParameter(1, java.sql.Types.OTHER);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    a = new Auto(rs.getInt(7), rs.getString(1),
                            rs.getString(4),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(5),
                            rs.getFloat(6));
                    temp.add(a);

                }
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public void validateInsertionModification(Auto a, boolean insertion) throws InsertionModificationException {
        List<Auto> listaAutos = retriveAllCars();
        Iterator<Auto> ait = listaAutos.iterator();
        if (insertion) {
            while (ait.hasNext()) {
                Auto atemp = ait.next();
                if (atemp.getPlate().equalsIgnoreCase(a.getPlate())) {
                    throw new InsertionModificationException(insertErrorMessage);
                }
            }
        } else {
            boolean exist = false;
            while (ait.hasNext() && !exist) {
                Auto atemp = ait.next();
                if (atemp.getPlate().equalsIgnoreCase(a.getPlate())) {
                    exist = true;
                }
            }
            if (!exist) {
                throw new InsertionModificationException(modifyErrorMessage);
            }
        }
    }
}
