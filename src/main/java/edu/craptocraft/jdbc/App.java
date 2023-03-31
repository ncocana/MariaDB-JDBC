package edu.craptocraft.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    private static Connection connection;

    public static void main( String[] args ) throws SQLException{
        try {
            openDatabaseConnection();
        } finally {
            closeDatabaseConnection();
        }
    }

    private static void openDatabaseConnection() throws SQLException {
        System.out.println("Connecting to the database....");
        connection = DriverManager.getConnection(
            "jdbc:mariadb://localhost:3306/jdbc_demo",
            "ncocana",
            "password"
        );
        // if (true) throw new RuntimeException("Simulated error!");
        System.out.println("Connection valid: " + connection.isValid(5));
    }

    private static void closeDatabaseConnection() throws SQLException {
        System.out.println("Closing database connection....");
        connection.close();
        System.out.println("Connection valid: " + connection.isValid(5));
    }
}
