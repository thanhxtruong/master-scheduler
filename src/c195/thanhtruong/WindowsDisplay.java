/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong;

import c195.thanhtruong.view_controller.UserLoginController;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The WindowsDisplay class is used to load FXML, set BundleResource (as needed),
 * and pass an instance of mainApp and active Stage to the controller for use
 * in other method calls defined in controller.
 * There are two static overloading methods:
 *      1. The method with ResourceBundle in the parameters is used when there is
 *      language translator used (currently used for Login FXML only)
 *      2. The method WITHOUT ResourceBundle works the same way, except there is
 *      no ResourceBundle
 * 
 * @param rb (optional) ResourceBundle for the language_files
 * @param rootLayout The layout container for the FXML
 * @param primaryStage The active stage in mainApp
 * @param title (optional) The title for the stage
 * @param stream The stream to load the logo icon
 * @param mainApp An instance of mainApp to pass to controller
 * 
 * @author thanhtruong
 */
public class WindowsDisplay {
    public static void displayScene(ResourceBundle rb,
                                    Pane rootLayout,
                                    Stage primaryStage,
                                    InputStream stream,
                                    MainApp mainApp) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(rb);
            loader.setLocation(MainApp.class.getResource("view_controller/UserLogin.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            
            primaryStage.setScene(scene);
            // Title is the key-value pair defined in ResourceBundle
            primaryStage.setTitle(rb.getString("title"));
            primaryStage.getIcons().add(new Image(stream));            
            primaryStage.show();
            
            // Give controller access to the main app            
            AbstractController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.setDialogStage(primaryStage);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void displayScene(Pane rootLayout,
                                    Stage primaryStage,
                                    String title,
                                    InputStream stream,
                                    MainApp mainApp) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view_controller/UserLogin.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.getIcons().add(new Image(stream));            
            primaryStage.show();
            
            // Give controller access to the main app            
            AbstractController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.setDialogStage(primaryStage);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
  
}
