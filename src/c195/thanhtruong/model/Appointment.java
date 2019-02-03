/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    ObjectProperty<Timestamp> startTime = new SimpleObjectProperty();
    ObjectProperty<Timestamp> endTime = new SimpleObjectProperty();
    StringProperty type = new SimpleStringProperty();
    StringProperty userName = new SimpleStringProperty();

    public Appointment(String title, String description, String location,
            String type, Timestamp startTime, Timestamp endTime, String userName) {
        this.title.set(title);
        this.description.set(description);
        this.location.set(location);
        this.type.set(type);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.userName.set(userName);
    }
    
    public Appointment(int appointmentId, String title, String description, String location,
            String type, Timestamp startTime, Timestamp endTime, String userName) {
        this(title, description, location, type, startTime, endTime, userName);
        this.appointmentId.set(appointmentId);        
    }
    
    public void isInputValid(String title, String apptType, String description,
                String location, String appDate, String apptHour, String apptMin) {
        
    }

    public int getAppointmentId() {
        return appointmentId.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getLocation() {
        return location.get();
    }

    public Timestamp getStartTime() {
        return startTime.get();
    }

    public Timestamp getEndTime() {
        return endTime.get();
    }

    public String getType() {
        return type.get();
    }

    public String getUserName() {
        return userName.get();
    }
    
    
}
