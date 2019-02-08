/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.Customer;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
public class WindowsDisplay extends ControllerFactory{
    ResourceBundle rb;
    String FXMLPath;
    String title;
    Stage ownerStage;
    Customer customer;
    Appointment appointment;
    private static final InputStream logoStream = WindowsDisplay.class.getClassLoader().getResourceAsStream("resources/images/logo.png");

    public WindowsDisplay(ResourceBundle rb, String FXMLPath, String title,
                        Stage ownerStage, Customer customer, Appointment appointment) {
        this.rb = rb;
        this.FXMLPath = FXMLPath;
        this.title = title;
        this.ownerStage = ownerStage;
        this.customer = customer;
        this.appointment = appointment;
    }
        
    public void displayScene() {
        Parent root = null;
        Stage stage;
        try {
            FXMLLoader loader = new FXMLLoader();
            if (rb != null)
                loader.setResources(rb);
            loader.setLocation(WindowsDisplay.class.getResource(this.FXMLPath));
            
            root = loader.load();
            
            Scene scene = new Scene(root);
            if (ownerStage != null) {
                stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(ownerStage);
            } else {
                stage = MainApp.getPrimaryStage();
            }            
            
            stage.setScene(scene);
            // Title is the key-value pair defined in ResourceBundle
            if (rb != null) {                
                stage.setTitle(rb.getString("title"));
            } else {
                stage.setTitle(this.title);
            }
            
            stage.getIcons().add(new Image(logoStream));
            
            AbstractController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setExitConfirmation();
            if (customer != null)
                if (appointment != null)
                    controller.displayData(customer, appointment);
                else
                    controller.displayData(customer, null);
            else
                controller.displayData(null, null);
            
            stage.show();
            
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
