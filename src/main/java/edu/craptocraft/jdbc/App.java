package edu.craptocraft.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

public class App {

    private static HikariDataSource dataSource;

    public static void main( String[] args ) throws SQLException{
        try {
            initDatabaseConnectionPool();
            deleteData("%");
            createData("Java", 10);
            createData("JavaScript", 9);
            createData("C++", 8);
            readData();
            updateData("C++", 7);
            readData();
            deleteData("C++");
            readData();
        } finally {
            closeDatabaseConnectionPool();
        }
    }

    private static void initDatabaseConnectionPool() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mariadb://localhost:3306/jdbc_demo");
        dataSource.setUsername("ncocana");
        dataSource.setPassword("password");
    }

    private static void closeDatabaseConnectionPool() {
        dataSource.close();
    }

    private static void createData(String name, int rating) throws SQLException {
        System.out.println("Creating data....");
        int rowsInserted;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO programming_language(name, rating) VALUES (?, ?)")) {
                statement.setString(1, name);
                statement.setInt(2, rating);
                rowsInserted = statement.executeUpdate();
                statement.close();
            }
        }
        System.out.println("Rows inserted: " + rowsInserted);
    }

    private static void readData() throws SQLException {
        System.out.println("Reading data....");
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT name, rating FROM programming_language ORDER BY rating DESC")) {
                ResultSet resultSet = statement.executeQuery();
                boolean empty = true;
                while (resultSet.next()) {
                    String name = resultSet.getString(1);
                    int rating = resultSet.getInt(2);
                    System.out.println("\t" + name + ": " + rating);
                    empty = false;
                }
                if (empty) {
                    System.out.println("\t (no data)");
                }
            }
        }
    }

    private static void updateData(String name, int newRating) throws SQLException {
        System.out.println("Updating data....");
        int rowsUpdated;
        try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("UPDATE programming_language SET rating = ? WHERE name LIKE ?")) {
                statement.setInt(1, newRating);
                statement.setString(2, name);
                rowsUpdated = statement.executeUpdate();
                statement.close();
            }
        }
        System.out.println("Rows updated: " + rowsUpdated);
    }

    private static void deleteData(String name) throws SQLException {
        System.out.println("Deleting data....");
        int rowsDeleted;
        try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("DELETE FROM programming_language WHERE name LIKE ?")) {
                statement.setString(1, name);
                rowsDeleted = statement.executeUpdate();
                System.out.println("Rows deleted: " + rowsDeleted);
                statement.close();
            }
        }
    }
}
