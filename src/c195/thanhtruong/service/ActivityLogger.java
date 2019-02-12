/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.service;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author thanhtruong
 */
public class ActivityLogger {
    private final static Logger log = Logger.getLogger("log.txt"); 
    
    public static void logActivities(String msg) {
        try {
            //The following four lines write the log text to a file. Otherwise it will print only to the console. 
            FileHandler fh = new FileHandler("log.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);
            
            log.setLevel(Level.INFO);
            log.log(Level.INFO, msg);
            
        } catch (IOException ex) {
            Logger.getLogger(ActivityLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ActivityLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 

