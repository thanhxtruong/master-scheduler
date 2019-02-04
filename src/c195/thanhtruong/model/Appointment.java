/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The typeList, hours, and minutes ObservableList are static final with
 * static getters, which are used to populate comboboxes in FXML
 * @author thanhtruong
 */
public class Appointment {
    IntegerProperty appointmentId = new SimpleIntegerProperty();
    StringProperty title = new SimpleStringProperty();
    StringProperty description = new SimpleStringProperty();
    StringProperty location = new SimpleStringProperty();
    ObjectProperty<Timestamp> startDateTime = new SimpleObjectProperty();
    ObjectProperty<Timestamp> endDateTime = new SimpleObjectProperty();
    StringProperty type = new SimpleStringProperty();
    StringProperty userName = new SimpleStringProperty();
    
    ObjectProperty<LocalDate> date = new SimpleObjectProperty();
    ObjectProperty<LocalTime> startTime = new SimpleObjectProperty();
    ObjectProperty<LocalTime> endTime = new SimpleObjectProperty();    

    public Appointment(String title, String description, String location,
            String type, Timestamp startDateTime, Timestamp endDateTime, String userName) {
        this.title.set(title);
        this.description.set(description);
        this.location.set(location);
        this.type.set(type);
        this.startDateTime.set(startDateTime);
        this.endDateTime.set(endDateTime);
        this.userName.set(userName);        
    }
    
    public Appointment(int appointmentId, String title, String description, String location,
            String type, Timestamp startDateTime, Timestamp endDateTime, String userName) {
        this(title, description, location, type, startDateTime, endDateTime, userName);
        this.appointmentId.set(appointmentId);

        // Construct appointment date, start, and end time for use in Appointment List TableView
        DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String txtStartDT = startDateTime.toString();
        String txtEndDT = endDateTime.toString();                
        this.date.setValue(LocalDate.parse(txtStartDT.substring(0, 10), dFormatter));
        
        DateTimeFormatter tFormatter = DateTimeFormatter.ofPattern("kk:mm:ss.S");        
        this.startTime.setValue(LocalTime.parse(txtStartDT.substring(11), tFormatter));
        this.endTime.setValue(LocalTime.parse(txtEndDT.substring(11), tFormatter));
        
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

    public Timestamp getStartDateTime() {
        return startDateTime.get();
    }

    public Timestamp getEndDateTime() {
        return endDateTime.get();
    }

    public String getType() {
        return type.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public IntegerProperty AppointmentId() {
        return appointmentId;
    }

    public StringProperty Title() {
        return title;
    }

    public StringProperty Description() {
        return description;
    }

    public StringProperty Location() {
        return location;
    }

    public ObjectProperty<Timestamp> StartDateTime() {
        return startDateTime;
    }

    public ObjectProperty<Timestamp> EndDateTime() {
        return endDateTime;
    }

    public StringProperty Type() {
        return type;
    }

    public StringProperty UserName() {
        return userName;
    }

    public ObjectProperty<LocalDate> Date() {
        return date;
    }

    public ObjectProperty<LocalTime> StartTime() {
        return startTime;
    }

    public ObjectProperty<LocalTime> EndTime() {
        return endTime;
    }       
        
}
