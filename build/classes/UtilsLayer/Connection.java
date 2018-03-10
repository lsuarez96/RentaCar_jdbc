package UtilsLayer;

import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    private String user = "postgres";
    private String pass = "6991";
    @SuppressWarnings("FieldMayBeFinal")
    private String url;
    //private static java.sql.Connection connection;

    public Connection()
            throws ClassNotFoundException, SQLException, FileNotFoundException, Exception {
        user = Configuration.getConfigurationInstance().getUser();
        pass = Configuration.getConfigurationInstance().getPassword();
        Class.forName("org.postgresql.Driver");
        url = "jdbc:postgresql://" + Configuration.getConfigurationInstance().getDataBaseAddress();

    }

    public java.sql.Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

}
