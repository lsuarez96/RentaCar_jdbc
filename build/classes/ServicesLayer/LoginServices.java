/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ModelLayer.Login;
import ModelLayer.Role;
import ModelLayer.User;
import UtilsLayer.Cipher;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luisito Suarez
 */
public class LoginServices {

    public boolean login(String usr, String pass) {
        boolean res = false;
        Connection con = ServicesLocator.getConnection();
        String function = "{?=call login_function(?,?)}";
        try {
            con.setAutoCommit(false);
            CallableStatement st = con.prepareCall(function);
            st.registerOutParameter(1, Types.BOOLEAN);

            st.setString(2, usr);
            st.setString(3, Cipher.SHA256(pass));
            st.execute();
            res = st.getBoolean(1);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public void insertarRol_Uuario(String usr, String rl) {
        Connection con = ServicesLocator.getConnection();
        String function = "{call insertar_rol_usuario(?,?)}";
        try {
            CallableStatement st = con.prepareCall(function);

            st.setString(1, rl);
            st.setString(2, usr);
            st.executeQuery();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertarRol_Uuario(User usr, Role rl) {
        Connection con = ServicesLocator.getConnection();
        String function = "{call insertar_rol_usuario(?,?)}";
        try {
            CallableStatement st = con.prepareCall(function);

            st.setString(1, rl.getRole());
            st.setString(2, usr.getUserName());
            st.executeQuery();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void eliminarRol_Uuario(String usr, String rl) {
        Connection con = ServicesLocator.getConnection();
        String function = "{call eliminar_rol_usuario(?,?)}";
        try {
            CallableStatement st = con.prepareCall(function);

            st.setString(1, rl);
            st.setString(2, usr);
            st.executeQuery();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarRol_Uuario(User usr, Role rl) {
        Connection con = ServicesLocator.getConnection();
        String function = "{eliminar_rol_usuario(?,?)}";
        try {
            CallableStatement st = con.prepareCall(function);

            st.setString(1, rl.getRole());
            st.setString(2, usr.getUserName());
            st.executeQuery();

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Login> retriveAllRoleUsers() {
        List<Login> list = new LinkedList<>();
        try (Connection con = ServicesLocator.getConnection()) {
            String sql = "{?=call buscar_roles_usuarios() }";
            con.setAutoCommit(false);
            CallableStatement st = con.prepareCall(sql);
            st.registerOutParameter(1, Types.OTHER);
            st.execute();
            ResultSet rs = (ResultSet) st.getObject(1);
            while (rs.next()) {
                list.add(new Login(rs.getString(2), rs.getString(1)));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public Login retriveLogin(String usr, String rl) {
        try (Connection connection = ServicesLocator.getConnection()) {
            String sqlFunction = "{?=call buscar_rol_usuario(?,?)}";
            connection.setAutoCommit(false);
            CallableStatement st = connection.prepareCall(sqlFunction);
            st.registerOutParameter(1, Types.OTHER);
            st.setString(2, rl);
            st.setString(3, usr);
            st.execute();
            ResultSet rs = (ResultSet) st.getObject(1);
            while (rs.next()) {
                return new Login(rs.getString(2), rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Role> retriveAssociatedRoles(String u) {
        List<Role> list = new ArrayList<>();
        try (Connection con = ServicesLocator.getConnection()) {
            String sql = "{?=call buscar_roles_asosiados(?)}";
            con.setAutoCommit(false);
            CallableStatement ps = con.prepareCall(sql);
            ps.registerOutParameter(1, Types.OTHER);
            ps.setString(2, u);
            ps.execute();
            ResultSet rs = (ResultSet) ps.getObject(1);
            while (rs.next()) {
                list.add(new Role(rs.getInt(1), rs.getString(2)));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void addLogin(List<Role> roleList, String user) {
        roleList.stream().forEach((r) -> {
            insertarRol_Uuario(user, r.getRole());
        });
    }
}
