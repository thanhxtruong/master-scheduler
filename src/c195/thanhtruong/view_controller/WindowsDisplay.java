/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.MainApp;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.E;
import javafx.stage.Modality;
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
 * The displayModalWindow() static method is used to display a Window on top of
 * the current active Window.
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
                                    String FXMLPath) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(rb);	    
            loader.setLocation(WindowsDisplay.class.getResource(FXMLPath));
            InputStream logoStream = WindowsDisplay.class.getClassLoader().getResourceAsStream("resources/images/logo.png");
            root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = MainApp.getPrimaryStage();
            
            stage.setScene(scene);
            // Title is the key-value pair defined in ResourceBundle
            stage.setTitle(rb.getString("title"));
            stage.getIcons().add(new Image(logoStream));            
            stage.show();
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void displayScene(String FXMLPath, String title) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WindowsDisplay.class.getResource(FXMLPath));
            InputStream logoStream = WindowsDisplay.class.getClassLoader().getResourceAsStream("resources/images/logo.png");
            root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = MainApp.getPrimaryStage();            
            
            stage.setScene(scene);
            stage.setTitle(title);
            stage.getIcons().add(new Image(logoStream));
            stage.show();
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void displayModalWindow(String FXMLPath, String title, Stage ownerStage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WindowsDisplay.class.getResource(FXMLPath));
            InputStream logoStream = WindowsDisplay.class.getClassLoader().getResourceAsStream("resources/images/logo.png");
            root = loader.load();
            
            // Create a new Stage
            Stage newStage = new Stage();
            System.out.println("ownerStage is " + ownerStage);
            System.out.println("newStage from WindowsDisplay is " + newStage);
            newStage.setTitle(title);
            newStage.getIcons().add(new Image(logoStream));
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(ownerStage);            
            
            Scene scene = new Scene(root);
            newStage.setScene(scene);                        
            
            AbstractController controller = loader.getController();
            controller.setDialogStage(newStage);
            controller.setExitConfirmation();
            
            newStage.showAndWait();
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
            
//    public static void filterList(ObservableList unsortedList) {
//        
//        Predicate p = new Predicate() {
//            @Override
//            public boolean test(Object t) {
//                return true;
//            }
//            
//        };
//        
//        FilteredList filteredList = new FilteredList<>(unsortedList, p);
//        
//        ChangeListener listener = new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable,
//                    Object oldValue,
//                    Object newValue) {
//                Predicate p2 = new Predicate() {
//                    @Override
//                    public boolean test(Object t) {
//                        if(newValue.toString().isEmpty() || newValue.toString().equals(null)) {
//                            return true;
//                        }
//                        
//                        String lowerCaseFilter = newValue.toString().toLowerCase();
//                        
//                        
//                    }
//                    
//                }
//            }
//            
//        }
//    }
  
}
