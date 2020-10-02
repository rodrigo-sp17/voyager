/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Contains useful static methods for connecting to a database.
 * 
 * @author rodrigo
 */
public class DatabaseUtil {
    
    private final static String TAG = DatabaseUtil.class.getSimpleName();
    
    // The default URL of where the database is expected
    private final static String DEFAULT_URL = "jdbc:derby:voyager";
        
    /**
     * Returns a Connection to database on database.properties, or DEFAULT if
     * it can't be found
     * 
     * @return Connection instance to the database, or null if failed
     */
    public static Connection getConnection() {
        
        // Attempts to load database properties
        Properties databaseProperties = new Properties();
        try (InputStream in = ClassLoader
                    .getSystemResource("database.properties").openStream()) {
                databaseProperties.load(in);
                System.out.println("Database properties loaded successfully!");
        } catch (IOException e) {
                System.err.println(TAG 
                        + " - Error loading database properties! "  
                        + e.getMessage());
        }                   
        
        Connection conn = null;        
        String url = databaseProperties.getProperty("url", DEFAULT_URL);
        try {
            conn = DriverManager.getConnection(url);            
            //DatabaseMetaData meta = conn.getMetaData();
        } catch (SQLException e) {
            System.err.println(TAG + " - SQLException: " + e.getMessage());   
            System.err.println("SQLState: " + e.getSQLState());
            System.out.println("Url from properties may be invalid. "
                    + "Attempting default...");
            try {
               conn = DriverManager.getConnection(DEFAULT_URL);
               System.out.println("Default url loaded successfully!");
            } catch (SQLException f) {
                System.err.println(TAG + " - SQLException: " + e.getMessage());   
                System.err.println("SQLState: " + e.getSQLState());
                throw new RuntimeException("Could not find database."
                        + " Check properties, local folders and try again.");
            }
        } finally {
            if (conn != null) {
                System.out.println("Database connected succesfully!");                
            } else {
                System.err.println(TAG 
                        + " - Database connection failed!");                
            }            
        }       
        
        return conn;
    }    
}
