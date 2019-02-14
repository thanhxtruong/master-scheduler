/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The WindowsDisplay class is used to load FXML, set BundleResource (as needed),
 * and pass an instance of mainApp and active Stage to the controller for use
 * in other method calls defined in controller.\
 * 
 * @param rb (optional) ResourceBundle for the language_files
 * @param FXMLPath The path to the FXML file to be loaded for this scene
 * @param title The title for the stage
 * @param ownerStage (optional) Set onwerStage for Window modality if not null
 * @param customer (optional) Set the selected customer in the @override
 * displayData() method inherited from AbstractController. This parameter is used
 * associate customer information and/or appointment by customer data with the
 * selected customer
 * @param  appointment (optional) same as the "customer" parameter
 * @author thanhtruong
 */
public class WindowsDisplay {
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
            
            stage.setOnShown(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    stage.centerOnScreen();
                }
                
            });
            stage.show();
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
  
}
