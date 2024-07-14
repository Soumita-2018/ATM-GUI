package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, "scott", "tiger");
    }
}
