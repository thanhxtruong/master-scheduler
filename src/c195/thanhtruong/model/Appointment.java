/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author thanhtruong
 */
public class Appointment {
    IntegerProperty appointmentId = new SimpleIntegerProperty();
    StringProperty title = new SimpleStringProperty();
    StringProperty description = new SimpleStringProperty();
    StringProperty location = new SimpleStringProperty();
    StringProperty contact = new SimpleStringProperty();
    StringProperty startTime = new SimpleStringProperty();
    StringProperty endTime = new SimpleStringProperty();
    StringProperty type = new SimpleStringProperty();
    StringProperty userName = new SimpleStringProperty();

    public Appointment() {
    }
    
    public void isInputValid(String title, String apptType, String description,
            String location, String appDate, String apptHour, String apptMin) {
        
    }
    
    
}
