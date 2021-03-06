/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong;

import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.User;
import c195.thanhtruong.service.ApptAlertService;
import c195.thanhtruong.view_controller.WindowsBuilder;
import c195.thanhtruong.view_controller.WindowsDisplay;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * MainApp used to launch the UserLogin FXML, which starts the application.
 * Getters are used to pass Stage and mainApp instances to other controllers for 
 * loading FXML from controller (indirectly) instead of from mainApp.
 * The logged-in user is also set in MainApp and can be retrieved from here.
 * @author thanhtruong
 */
public class MainApp extends Application {
    
    static Stage primaryStage;
    private static ResourceBundle rb;
    private static final User currentUser = new User();
    private final AppointmentDB apptDB = AppointmentDB.getInstance();

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
                        
    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;
        Locale myLocale = Locale.getDefault();
        // Use for testing language
//        Locale.setDefault(new Locale("fr", "FR"));
//        rb = ResourceBundle.getBundle("language_files/rb");
        rb = ResourceBundle.getBundle("language_files/rb", myLocale);
                
        showLoginScreen();
    }    
    
    public void showLoginScreen(){
        // Use of Builder Pattern
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setRb(rb)
                .setFXMLPath("UserLogin.fxml")
                .build();
        windowDisplay.displayScene();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }
    
}
