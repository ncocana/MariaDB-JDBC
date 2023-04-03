package edu.craptocraft.jdbc.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

public class Database {

    private static HikariDataSource dataSource;
    
    public static void initDatabaseConnectionPool() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mariadb://localhost:3306/jdbc_demo");
        dataSource.setUsername("ncocana");
        dataSource.setPassword("password");
    }

    public static void closeDatabaseConnectionPool() {
        dataSource.close();
    }

    public static void createData(String name, int rating) throws SQLException {
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

    public static void readData() throws SQLException {
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

    public static void updateData(String name, int newRating) throws SQLException {
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

    public static void deleteData(String name) throws SQLException {
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
