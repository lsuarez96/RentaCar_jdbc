/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ModelLayer.Role;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Luisito Suarez
 */
public class RoleServices {

    public List<Role> allRole() throws SQLException {
        List<Role> temp = new LinkedList<>();
        try (Connection connection = ServicesLocator.getConnection()) {
            String function = "{?=call buscar_roles()}";
            connection.setAutoCommit(false);
            CallableStatement st = connection.prepareCall(function);
            st.registerOutParameter(1, Types.OTHER);
            st.execute();
            ResultSet result = (ResultSet) st.getObject(1);
            while (result.next()) {
                temp.add(new Role(result.getString(1)));
            }
            connection.close();
            return temp;
        }
    }

}
