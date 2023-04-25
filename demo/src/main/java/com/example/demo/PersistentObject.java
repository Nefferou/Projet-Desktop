package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class PersistentObject {

    public static final String HOST = "sql7.freemysqlhosting.net";
    public static final String PORT = "3306";
    public static final String DATABASE_NAME = "sql7614185";
    public static final String USER = "sql7614185";
    public static final String PASSWORD = "mJeepwVJ4s";
    public static final String DATABASE_URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE_NAME;
    public Connection dbConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        return conn;
    }
    public abstract void persist();

}
