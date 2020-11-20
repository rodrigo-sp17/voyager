package com.nauta.voyager.util;

import com.nauta.voyager.util.exception.PropertyNotFoundException;
import com.nauta.voyager.util.StateNotifier;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Loads the application context, which holds necessary dependencies in an easy
 * manner
 * 
 */
public class VoyagerContext extends StateNotifier {
    
    private static final Logger log = LogManager.getLogger();
    
    private Map<String, Object> contextObjects;
    
    private Properties localProperties;    
    
    private static VoyagerContext activeContext;

    public VoyagerContext() {
        contextObjects = new HashMap<>();
        activeContext = this;
        localProperties = initLocalProperties();
        loadRaftProperties();
    }
    
    private Properties initLocalProperties() {
        // TODO
        Properties defaults = new Properties();
        
        try (InputStream in = ClassLoader
                .getSystemResource("default.properties").openStream()) {
            defaults.load(in);            
            log.info("Default properties loaded successfully!");
        } catch (IOException f) {
            log.error("Error loading local properties");                      
            log.error(f);
        }
        
        Properties result = new Properties(defaults);
        try (InputStream in = new FileInputStream("local.properties")) {                
            result.load(in);
            log.info("Local Properties loaded successfully!");            
        } catch (FileNotFoundException e) {
            log.info("Could not find local properties. "
                    + "Using defaults...");
        } catch (IOException g) {
            log.error("Error loading local properties"); 
            log.error(g);
            throw new RuntimeException("IOException while loading properties."
                    + " Closing application!");
        }        
        return result;
    }
    
    private void loadRaftProperties() {
        Properties result = new Properties();
        
        try (InputStream in = new FileInputStream("raft.properties")) {    
            result.load(in);
            log.info("Raft Properties loaded successfully!");        
        } catch (IOException g) {
            log.error("Error loading local properties"); 
            log.error(g);            
        }        
        contextObjects.put("raftProperties", result);
    }
    
    private void saveRaftProperties() {
        Properties raftProperties = (Properties) 
                contextObjects.get("raftProperties");
        try (OutputStream out = new FileOutputStream("raft.properties")) {
            log.info("Saving raft properties...");
            raftProperties.store(out, "DO NOT MODIFY");
            log.info("Raft properties saved!");
        } catch (IOException e) {
            log.error("IO errror saving Raft properties"); 
            log.error(e);
        }
    }
    
    private void saveProperties() {
        try (OutputStream out = new FileOutputStream("local.properties")) {
            log.info("Saving local properties...");
            localProperties.store(out, "DO NOT MODIFY");
            log.info("Properties saved!");
        } catch (IOException e) {
            log.error("IO errror saving properties"); 
            log.error(e);
        }
    }
    
    // Cleanup method
    public void endContext() {
        saveProperties();
        saveRaftProperties();
    }   
    
    public void addObject(String key, Object obj) {
        contextObjects.put(key, obj);
    }
    
    public Object getParam(String key) {
        return contextObjects.get(key);       
    }
    
    public String getLocalProperty(String key) {
        String result = localProperties.getProperty(key);
        if (result == null) {
            throw new PropertyNotFoundException(
                    "Could not find properties in context");
        }
        return result;
    }   
    
    public static VoyagerContext getContext() {
        return activeContext;
    }
}
