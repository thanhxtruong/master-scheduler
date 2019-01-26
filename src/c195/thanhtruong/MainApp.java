/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong;

import c195.thanhtruong.view_controller.UserLoginController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author thanhtruong
 */
public class MainApp extends Application {
    
    private Stage primaryStage;
    private AnchorPane rootLayout;
            
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Master Scheduler - Login");
        
        showLoginScreen();
      
    }
    
    
    public void showLoginScreen(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view_controller/UserLogin.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setTitle("Master Scheduler");
//            Image icon = new Image(UserLoginController.class.getResourceAsStream("authentication icon.png"));
//            System.out.println(icon);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
            primaryStage.setScene(scene);
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
