package com.nauta.voyager.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains methods handling database connection and persistence frameworks
 * initialization
 * 
 * @author rodrigo
 */
public class DatabaseUtil {        
    private final static Logger log = LogManager.getLogger();
    
    // Flag that holds if a instance was already created
    private static boolean exists = false;
    
    // The only instance of DatabaseUtil
    private static DatabaseUtil instance;
        
    private static final String PERSISTENCE_UNIT_NAME =
            "com.voyager.jpa";
        
    // The default URL of where the database is expected
    private final static String DEFAULT_URL = "jdbc:derby:voyager";
    
    // For Hibernate frameworl
    private final EntityManagerFactory emf;
        
    
    private DatabaseUtil() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);               
    }
    
    // Initiates the database connection routine
    public static DatabaseUtil init() {
        if (!exists) {
            exists = true;
            instance = new DatabaseUtil();
        }
        return instance;
    }
    
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
                log.info("Database properties loaded successfully!");
        } catch (IOException e) {
                log.error("Error loading database properties!");
                log.error(e);                        
        }                   
        
        Connection conn = null;        
        String url = databaseProperties.getProperty("url", DEFAULT_URL);
        try {
            conn = DriverManager.getConnection(url);            
            //DatabaseMetaData meta = conn.getMetaData();
        } catch (SQLException e) {
            log.warn(e);               
            log.warn("Url from properties may be invalid. "
                    + "Attempting default...");
            try {
               conn = DriverManager.getConnection(DEFAULT_URL);
               log.info("Default url loaded successfully!");
            } catch (SQLException f) {                
                log.error(f);
                throw new RuntimeException("Could not find database."
                        + " Check properties, local folders and try again.");
            }
        } finally {
            if (conn != null) {
                log.info("Database connected succesfully!");                
            } else {
                log.error("Database connection failed!");                
            }            
        }       
        
        return conn;
    }    
    
    public EntityManagerFactory getSessionFactory() {
        return emf;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
