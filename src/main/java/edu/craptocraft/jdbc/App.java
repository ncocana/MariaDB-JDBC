package edu.craptocraft.jdbc;

import java.sql.SQLException;

import edu.craptocraft.jdbc.Database.Database;

public class App {

    public static void main( String[] args ) throws SQLException{
        try {
            Database.initDatabaseConnectionPool();
            Database.deleteData("%");
            Database.createData("Java", 10);
            Database.createData("JavaScript", 9);
            Database.createData("C++", 8);
            Database.readData();
            Database.updateData("C++", 7);
            Database.readData();
            Database.deleteData("C++");
            Database.readData();
        } finally {
            Database.closeDatabaseConnectionPool();
        }
    }

}
