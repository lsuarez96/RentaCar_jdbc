/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.InsertionModificationException;
import ModelLayer.User;
import UtilsLayer.Cipher;
import static UtilsLayer.Cipher.SHA256;
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
 * @author Luisito Suarez
 */
public class UserServices {

    private final String insertionErrorMessage = "El usuario que intenta insertar ya existe";
    private final String modificationErrorMessage = "El usuario que intenta modificar no existe";

    @SuppressWarnings("CallToPrintStackTrace")
    public void insertUser(User user) throws InsertionModificationException {
        validateInsertionModification(user, true);
        String pass = SHA256(user.getPass());
        ServicesLocator.getServicesInstance();
        java.sql.Connection con = ServicesLocator.getConnection();
        String func = "{call insertar_usuario(?,?,?,?)}";
        try {
            CallableStatement st = con.prepareCall(func);
            st.setString(1, user.getUserName());
            st.setString(2, pass);
            st.setString(3, user.getName());
            st.setString(4, user.getLastName());
            st.executeQuery();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteUser(User user) {
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{call eliminar_uuario(?,?)}";
            CallableStatement statement = connection.prepareCall(function);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPass());
            statement.executeQuery();
            connection.close();
        } catch (SQLException e) {
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public User readUser(String user, String pass) {
        // List<User> temp = new LinkedList<User>();
        User u = null;
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{?=call buscar_usuario(?,?)}";
            connection.setAutoCommit(false);
            CallableStatement statement = connection.prepareCall(function);
            statement.registerOutParameter(1, Types.OTHER);
            statement.setString(2, user);
            statement.setString(3, Cipher.SHA256(pass));
            statement.execute();
            ResultSet result = (ResultSet) statement.getObject(1);
            while (result.next()) {
                u = (new User(result.getInt(1), result.getString(4), result.getString(5), result.getString(2), result.getString(3)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public User readUser(String user) {
        // List<User> temp = new LinkedList<User>();
        User u = null;
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "SELECT * "
                    + "FROM usuarios "
                    + "WHERE usuario=?";
            PreparedStatement statement = connection.prepareStatement(function);
            statement.setString(1, user);
            ResultSet result = (ResultSet) statement.executeQuery();
            while (result.next()) {
                u = (new User(result.getInt(1), result.getString(4), result.getString(5), result.getString(2), result.getString(3)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<User> allUser() {
        List<User> temp = new LinkedList<>();
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{?=call buscar_usuarios()}";
            connection.setAutoCommit(false);
            CallableStatement st = connection.prepareCall(function);
            st.registerOutParameter(1, Types.OTHER);
            st.execute();
            ResultSet result = (ResultSet) st.getObject(1);
            while (result.next()) {
                temp.add(new User(result.getInt(1), result.getString(4), result.getString(5), result.getString(2), result.getString(3)));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public void modifyUser(User u) throws InsertionModificationException {
        validateInsertionModification(u, false);
        try (Connection con = ServicesLocator.getConnection()) {
            String sql = "UPDATE usuarios "
                    + "SET usuario=?, "
                    + "nombre=?, "
                    + "apellidos=? "
                    + "WHERE id_user=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u.getUserName());
            ps.setString(2, u.getName());
            ps.setString(3, u.getLastName());
            ps.setInt(4, u.getId());
            ps.execute();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePassword(User u) {
        try (Connection con = ServicesLocator.getConnection()) {
            String sql = "UPDATE usuarios "
                    + "SET usuario=?, "
                    + "password_usuario=?, "
                    + "nombre=?, "
                    + "apellidos=? "
                    + "WHERE id_user=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(2, Cipher.SHA256(u.getPass()));
            ps.setString(1, u.getUserName());
            ps.setString(3, u.getName());
            ps.setString(4, u.getLastName());
            ps.setInt(5, u.getId());
            ps.execute();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User readUser(int id) {
        User u = null;
        try (Connection con = ServicesLocator.getConnection()) {
            String sql = "SELECT "
                    + "usuario,"
                    + " password_usuario, "
                    + "nombre, "
                    + "apellidos "
                    + "FROM usuarios "
                    + "WHERE id_user=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u = new User(rs.getString(3), rs.getString(4), rs.getString(1), rs.getString(2));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    private void validateInsertionModification(User u, boolean insertion) throws InsertionModificationException {
        User utemp = readUser(u.getId());
        if (insertion && utemp != null) {
            throw new InsertionModificationException(insertionErrorMessage);
        } else if (!insertion && utemp == null) {
            throw new InsertionModificationException(modificationErrorMessage);
        }
    }
}
