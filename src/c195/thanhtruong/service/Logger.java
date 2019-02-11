/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.service;

import java.util.logging.Level;

/**
 *
 * @author TTruong
 */
public class Logger {
    private final static Logger LOGGER =  
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
  
    // Get the Logger from the log manager which corresponds  
    // to the given name <Logger.GLOBAL_LOGGER_NAME here> 
    // static so that it is linked to the class and not to 
    // a particular log instance because Log Manage is universal 
    public void makeSomeLog() 
    { 
        // add some code of your choice here 
        // Moving to the logging part now 
        LOGGER.log(Level.INFO, "My first Log Message"); 
  
        // A log of INFO level with the message "My First Log Message" 
    } 
} 
  
public class GfG { 
    public static void main(String[] args) 
    { 
        DemoLogger obj = new DemoLogger(); 
        obj.makeSomeLog(); 
  
        // Generating some log messages through the  
        // function defined above 
        LogManager lgmngr = LogManager.getLogManager(); 
  
        // lgmngr now contains a reference to the log manager. 
        Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME); 
  
        // Getting the global application level logger  
        // from the Java Log Manager 
        log.log(Level.INFO, "This is a log message"); 
  
        // Create a log message to be displayed 
        // The message has a level of Info 
    } 
}
