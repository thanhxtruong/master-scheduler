/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong;

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
 *
 * @author thanhtruong
 */
public class MainApp extends Application {
    
    private Stage primaryStage;
    private Pane rootLayout;
    private static ResourceBundle rb;
    InputStream logoStream = getClass().getResourceAsStream("logo.png");
                
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
        WindowsDisplay.displayScene(rb, rootLayout, primaryStage, logoStream, this);
    }
    
    public void showHome(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(rb);
            loader.setLocation(MainApp.class.getResource("view_controller/UserLogin.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            
            primaryStage.setScene(scene);
            primaryStage.setTitle(rb.getString("title"));
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));            
            primaryStage.show();
            
            // Give controller access to the main app
            UserLoginController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(primaryStage);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
