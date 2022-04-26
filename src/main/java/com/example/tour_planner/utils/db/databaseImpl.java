package com.example.tour_planner.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseImpl implements database{

    // Command:
    // (Cmd) docker run --name swe2db -e POSTGRES_USER=swe2user -e POSTGRES_PASSWORD=swe2pw -p 5432:5432 postgres

    static String URL = "jdbc:postgresql://localhost:5432/swe2user";
    static String username = "swe2user";
    static String password = "swe2pw";

    static Connection getConnection() {
        //build a connection with the database
        try {
            return DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
