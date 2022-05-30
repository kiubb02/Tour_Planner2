package com.example.tour_planner.utils.db;

import com.example.tour_planner.utils.logger.LoggerFactory;
import com.example.tour_planner.utils.logger.LoggerWrapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class databaseImpl implements database{ // Singleton Pattern

    // Command:
    // (Cmd) docker run --name swe2db -e POSTGRES_USER=swe2user -e POSTGRES_PASSWORD=swe2pw -p 5432:5432 postgres

    // ---------- LAZY INITIALIZATION ----------//
    /////////////////// THE ONLY INSTANCE TO USE ///////////////////
    public static databaseImpl instance = null;

    private static String URL;
    private static String username;
    private static String password;

    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    // PRIVATE Default-Constructor
    private databaseImpl() {
        try {
            Properties dbProps = new Properties();
            dbProps.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("database.properties"));

            URL = dbProps.getProperty("url");
            username = dbProps.getProperty("username");
            password = dbProps.getProperty("password");
        } catch(IOException e){
            logger.warn(e.toString());
        }
    }

    public static databaseImpl getInstance()
    {
        if(instance == null) instance = new databaseImpl();
        return instance;
    }

    public Connection getConnection() {
        //build a connection with the database
        try {
            return DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            logger.warn(e.toString());
        }
        return null;
    }
}
