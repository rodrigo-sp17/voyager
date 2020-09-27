/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/* TODO List
 * - Implement proper exception handling
 * 
 */

/**
 *
 * @author rodrigo
 */
public class DatabaseUtil {
    
    // TODO - REMOVE HARDCODED ADDRESS!
    private final static String URL = "jdbc:derby:voyager";
        
    
    public static Connection getConnection() {
                           
        Connection conn = null;
        // TODO - insert connection Properties Retrieval        
        try {
            conn = DriverManager.getConnection(URL);
            //DatabaseMetaData meta = conn.getMetaData();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getSQLState());
        }       
        
        if (conn != null) {
            System.out.println("Database connected succesfully!");            
        } else {
            System.err.println("Could NOT get a connection to the database!");            
        }
        
        return conn;
    }    
}
