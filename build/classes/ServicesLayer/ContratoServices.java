/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.Contrato;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Luisito
 */
public class ContratoServices {

    private final String insertionErrorMessage = "El contrato que intenta insertar ya existe!!!!";
    private final String modificationErrorMessage = "El contrato que intenta modificar no existe";

    @SuppressWarnings("CallToPrintStackTrace")
    public void insertContract(Contrato c, int id_usuario) throws InsertionModificationException {
        validateInsertionModification(c, true);
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call insertar_contrato(?,?,?,?,?,?,?)}";

                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, c.getPlate());
                statement.setString(2, c.getIdTurist());
                statement.setDate(3, c.getStartDate());
                statement.setDate(4, c.getEndDate());
                statement.setString(5, c.getIdDriver());
                statement.setString(6, c.getPayment());
                statement.setInt(7, id_usuario);
                statement.executeQuery();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void modifyContract(Contrato c, int id_usuario) throws InsertionModificationException {
        validateInsertionModification(c, false);
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call modificar_contrato(?,?,?,?,?,?,?)}";
                //connection.setAutoCommit(false);
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, c.getPlate());
                statement.setString(2, c.getIdTurist());
                statement.setDate(3, c.getStartDate());
                statement.setDate(4, c.getEndDate());
                statement.setString(5, c.getIdDriver());
                statement.setString(6, c.getPayment());
                statement.setInt(7, id_usuario);
                statement.execute();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void closeContract(Contrato c) {
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call cerrar_contrato(?,?,?,?)}";
                // connection.setAutoCommit(false);
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, c.getPlate());
                statement.setString(2, c.getIdTurist());
                statement.setDate(3, c.getStartDate());
                statement.setDate(4, c.getDeliveryDate());
                statement.execute();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void elimContract(String plate, String idTurist, Date startDate) {
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{call eliminar_contrato(?,?,?)}";
                CallableStatement statement = connection.prepareCall(function);
                statement.setString(1, plate);
                statement.setString(2, idTurist);
                statement.setDate(3, startDate);
                statement.executeQuery();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Contrato retriveContract(String plate, String idTurist, Date startDate) {
        Contrato c = null;
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                String function = "{?=call buscar_contrato(?,?,?)}";
                connection.setAutoCommit(false);
                CallableStatement statement = connection.prepareCall(function);
                statement.registerOutParameter(1, Types.OTHER);
                statement.setString(2, plate);
                statement.setString(3, idTurist);
                statement.setDate(4, startDate);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    c = new Contrato(rs.getString(1),
                            rs.getString(2),
                            rs.getDate(3),
                            rs.getDate(4),
                            rs.getString(5),
                            rs.getDate(6),
                            rs.getString(7),
                            rs.getString(8));

                }
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<Contrato> retriveAllContracts() {
        List<Contrato> list = new LinkedList<>();
        try {
            try (Connection connection = ServicesLocator.getConnection()) {
                connection.setAutoCommit(false);
                String function = "{?=call buscar_contratos()}";
                CallableStatement statement = connection.prepareCall(function);
                statement.registerOutParameter(1, Types.OTHER);
                statement.execute();
                ResultSet rs = (ResultSet) statement.getObject(1);
                while (rs.next()) {
                    list.add(new Contrato(rs.getString(1),
                            rs.getString(2),
                            rs.getDate(3),
                            rs.getDate(4),
                            rs.getString(5),
                            rs.getDate(6),
                            rs.getString(7),
                            rs.getString(8)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void validateInsertionModification(Contrato c, boolean insertion) throws InsertionModificationException {
        Contrato ctemp = retriveContract(c.getPlate(), c.getIdTurist(), c.getStartDate());
        System.out.println(ctemp);
        if (insertion && ctemp != null) {
            throw new InsertionModificationException(insertionErrorMessage);
        } else if (!insertion && ctemp == null) {
            //  throw new InsertionModificationException(modificationErrorMessage);
        }
    }

}
