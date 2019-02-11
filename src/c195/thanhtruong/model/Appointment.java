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

/**
 * The typeList, hours, and minutes ObservableList are static final with
 * static getters, which are used to populate comboboxes in FXML
 * @author thanhtruong
 */
public class Appointment {
    private IntegerProperty appointmentId = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty location = new SimpleStringProperty();
    private ObjectProperty<Timestamp> startDateTime = new SimpleObjectProperty();
    private ObjectProperty<Timestamp> endDateTime = new SimpleObjectProperty();
    private StringProperty type = new SimpleStringProperty();
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty custName = new SimpleStringProperty();
    
    private ObjectProperty<LocalDate> date = new SimpleObjectProperty();
    private ObjectProperty<LocalTime> startTime = new SimpleObjectProperty();
    private ObjectProperty<LocalTime> endTime = new SimpleObjectProperty();    

    public Appointment(String title, String description, String location,
            String type, Timestamp startDateTime, Timestamp endDateTime, String userName, String custName) {
        this.title.set(title);
        this.description.set(description);
        this.location.set(location);
        this.type.set(type);
        this.startDateTime.set(startDateTime);
        this.endDateTime.set(endDateTime);
        this.userName.set(userName);
        this.custName.set(custName);
    }
    
    public Appointment(int appointmentId, String title, String description, String location,
            String type, Timestamp startDateTime, Timestamp endDateTime, String userName, String custName) {
        this(title, description, location, type, startDateTime, endDateTime, userName, custName);
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

    public String getCustName() {
        return custName.get();
    }
    
    public IntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty locationProperty() {
        return location;
    }

    public ObjectProperty<Timestamp> startDateTimeProperty() {
        return startDateTime;
    }

    public ObjectProperty<Timestamp> endDateTimeProperty() {
        return endDateTime;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public StringProperty userNameProperty() {
        return userName;
    }
    
    public StringProperty custNameProperty() {
        return custName;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        return startTime;
    }

    public ObjectProperty<LocalTime> endTimeProperty() {
        return endTime;
    }       
        
}
