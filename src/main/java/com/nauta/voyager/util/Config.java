/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author rodrigo
 */
public class Config {    
    
    private static final String TAG = Config.class.getSimpleName();
    
    private final Properties defaultProps;
    
    public Config() {
        defaultProps = new Properties();
        try (InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("default.properties")) {
            defaultProps.load(in);
        } catch (IOException e) {
            System.err.format("%s - could not get load properties - %s",
                    TAG,
                    e.getMessage());
        }        
    }
    
    public Object getProperty(final Object key) {
        return defaultProps.get(key);        
    }    
}    

