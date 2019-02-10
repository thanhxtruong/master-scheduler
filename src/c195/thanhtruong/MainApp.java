/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong;

import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.User;
import c195.thanhtruong.view_controller.WindowsBuilder;
import c195.thanhtruong.view_controller.WindowsDisplay;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Getters are used to pass Stage and mainApp instances to other controllers for 
 * loading FXML from controller (indirectly) instead of from mainApp.
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
//        showScreen();
      
    }    
    
    public void showLoginScreen(){
        // Use of Builder Pattern
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setRb(rb)
                .setFXMLPath("UserLogin.fxml")
                .build();
        windowDisplay.displayScene();
    }

//    public void showScreen(){
//        // Use of Builder Pattern
//        WindowsDisplay windowDisplay = new WindowsBuilder()
//                .setRb(rb)
//                .setFXMLPath("CalendarByCust.fxml")
//                .build();
//        windowDisplay.displayScene();
//    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
