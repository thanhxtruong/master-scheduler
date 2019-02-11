/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.User;
import java.util.ResourceBundle;
import java.util.function.Function;
import javafx.stage.Stage;

/**
 * This is a builder pattern for WindowsDisplay.
 * The use of builder pattern makes it easier to add parameters to WindowsDisplay
 * in the future to customize different types of FXML Loader.
 * @author thanhtruong
 */
public class WindowsBuilder {
    ResourceBundle rb;
    String FXMLPath;
    String title;
    Stage ownerStage;
    Customer customer;
    User user;
    Appointment appointment;
    
    public WindowsBuilder setRb(ResourceBundle rb) {
        this.rb = rb;
        return this;
    }
    
    public WindowsBuilder setFXMLPath(String FXMLPath) {
        this.FXMLPath = FXMLPath;
        return this;
    }
    
    public WindowsBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
    
    public WindowsBuilder setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
        return this;
    }
    
    public WindowsBuilder setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    public WindowsBuilder setAppointment(Appointment appointment) {
        this.appointment = appointment;
        return this;
    }
    
    public WindowsDisplay build() {
        return new WindowsDisplay(rb, FXMLPath, title, ownerStage, customer, user, appointment);
    }
}
