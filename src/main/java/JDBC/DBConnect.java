package JDBC;

import java.sql.*;


public class DBConnect {

    public  final static String USER = "postgres"; // это не утилитарные данные
    public final static String PASSWORD = "k2hoesdy";
    public final static String URL = "jdbc:postgresql://localhost:5432/Aston";
    public final static  String DRIVER = "org.postgresql.Driver"; //

    public Connection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName(DRIVER);
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;


    }

}


