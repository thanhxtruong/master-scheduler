/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong;

import c195.thanhtruong.view_controller.WindowsDisplay;
import c195.thanhtruong.view_controller.UserLoginController;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Getters are used to pass Stage and mainApp instances to other controllers for 
 * loading FXML from controller (indirectly) instead of from mainApp.
 * @author thanhtruong
 */
public class MainApp extends Application {
    
    static Stage primaryStage;
    private Pane rootLayout;
    private static ResourceBundle rb;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public Pane getRootLayout() {
        return rootLayout;
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
        WindowsDisplay.displayScene(rb, "UserLogin.fxml");
    }    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
