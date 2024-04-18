package JDBC;

import util.Util;
import java.sql.*;


public class DBConnect {


    public Connection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName(Util.DRIVER);
        Connection connection;
        try {
            connection = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;


    }

}


