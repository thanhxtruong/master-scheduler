/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.service.DBConnection;
import c195.thanhtruong.service.Query;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import static java.time.ZonedDateTime.now;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.util.Callback;


/**
 *
 * @author thanhtruong
 */
public class AppointmentDB {
    
    final ObservableList<Appointment> apptListByCust;

    public AppointmentDB() {
        Callback<Appointment, Observable[]> extractor = new Callback<Appointment, Observable[]>(){
            @Override
            public Observable[] call(Appointment a) {
                return new Observable[] {
                    a.appointmentIdProperty(),
                    a.titleProperty(),
                    a.descriptionProperty(),
                    a.locationProperty(),
                    a.dateProperty(),
                    a.startTimeProperty(),
                    a.endTimeProperty(),
                    a.startDateTimeProperty(),
                    a.endDateTimeProperty(),
                    a.typeProperty(),
                    a.userNameProperty()
                };
            }
            
        };
        this.apptListByCust = FXCollections.observableArrayList(extractor);
        ListChangeListener listener = new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                while (c.next()) {
                    if (c.wasUpdated()) {
                        int start = c.getFrom();
                        int end = c.getTo();
                        System.out.println("start: " + start + ",end: " + end);
                    }
                }
            }
            
        };
        apptListByCust.addListener(listener);        
    }
    
    public ObservableList<Appointment> getApptListByCust() {
        return apptListByCust;
    }
        
    public void downloadAppt(Customer selectedCust) {
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            String sqlStatement = "SELECT customerName, appointmentId, "
                + "appointment.customerId, title, description, location, contact, "
                + "start, end, type, appointment.userId, userName\n"
                + "FROM appointment\n"
                + "INNER JOIN customer\n"
                + "ON appointment.customerId = customer.customerId\n"
                + "INNER JOIN user\n"
                + "ON appointment.userId = user.userId\n"
                + "WHERE customer.customerId = " + selectedCust.getCustomerID();
            
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();            
            
            Timestamp tempStartTS;
            Timestamp tempEndTS;
            while (result.next()) {
                //Start and End time from DB
                tempStartTS = result.getTimestamp("start");
                tempEndTS = result.getTimestamp("end");
                
                ZoneId newzid = ZoneId.systemDefault();
                
                // Covert Start and End time to local date and time
                ZonedDateTime newzdtStart = tempStartTS.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);
                LocalDateTime localStart = newLocalStart.toLocalDateTime();
                Timestamp localStartTS = Timestamp.valueOf(localStart);
                
                ZonedDateTime newzdtEnd = tempEndTS.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(newzid);
                LocalDateTime localEnd = newLocalEnd.toLocalDateTime();
                Timestamp localEndTS = Timestamp.valueOf(localEnd);
                
                Appointment tempAppt = new Appointment(result.getInt("appointmentId"),
                    result.getString("title"), result.getString("description"),
                    result.getString("location"), result.getString("type"),
                    localStartTS, localEndTS,
                    result.getString("userName"));
                apptListByCust.add(tempAppt);
            }
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }            
    }
    
    public void insertAppt(Appointment newAppt, Customer selectedCust) {
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            // Start and End Time have been converted to DB TimeZone in AddAppointmentController
            String sqlStatement = "INSERT INTO appointment\n" +
                "(customerId, title, description, location, contact, url, start, end, "
                + "createDate, createdBy, lastUpdate, lastUpdateBy, type, userId)\n" +
                "VALUES (" + selectedCust.getCustomerID() + ", '" + newAppt.getTitle() +
                "', '" + newAppt.getDescription() + "', '" + newAppt.getLocation() +
                    "', 'not used', 'not used', '" + newAppt.getStartDateTime() + "', '" + newAppt.getEndDateTime() +
                "', '" + Timestamp.valueOf(now(ZoneId.of("UTC")).toLocalDateTime()) + "', '" + 
                MainApp.getCurrentUser().getUserName() +
                "', '" + Timestamp.valueOf(now(ZoneId.of("UTC")).toLocalDateTime()) + "', '" + 
                MainApp.getCurrentUser().getUserName() +
                "', '" + newAppt.getType() + "', " + MainApp.getCurrentUser().getUserId() +")";
                        
            Query.makeQuery(sqlStatement);
            
            //Update apptListByCust;
            downloadAppt(selectedCust);
            
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public void updateAppt(Appointment newAppt, Appointment selectedAppt, Customer selectedCust) {
        String sqlStatement;
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            //Start and End Time have been converted to DB TimeZone in EditAppointmentController
            sqlStatement = "UPDATE appointment\n" +
                            "SET title = '" + newAppt.getTitle() + "',\n" +
                            "description = '" + newAppt.getDescription() + "',\n" +
                            "location = '" + newAppt.getLocation() + "',\n" +
                            "type = '" + newAppt.getType() + "',\n" +
                            "start = '" + newAppt.getStartDateTime() + "',\n" +
                            "end = '" + newAppt.getEndDateTime() + "',\n" +
                            "lastUpdate = '" + Timestamp.valueOf(now(ZoneId.of("UTC")).toLocalDateTime()) + "',\n" +
                            "lastUpdateBy = '" + MainApp.getCurrentUser().getUserName() + "'\n" +
                            "WHERE appointmentId = " + selectedAppt.getAppointmentId();
            System.err.println(sqlStatement);
            Query.makeQuery(sqlStatement);
            
            DBConnection.closeConnection();
            
            downloadAppt(selectedCust);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    
//    public static void main(String[] args) {
//        ObservableList<Appointment> apptList= FXCollections.observableArrayList();
//        
//        try {
//            // Connect to the DB
//            DBConnection.makeConnection();
//            
//            String sqlStatement = "SELECT customerName, appointmentId, "
//                + "appointment.customerId, title, description, location, contact, "
//                + "start, end, type, appointment.userId, userName\n"
//                + "FROM appointment\n"
//                + "INNER JOIN customer\n"
//                + "ON appointment.customerId = customer.customerId\n"
//                + "INNER JOIN user\n"
//                + "ON appointment.userId = user.userId\n"
//                + "WHERE customer.customerId = 3";
//            
//            Query.makeQuery(sqlStatement);
//            ResultSet result = Query.getResult();            
//            
//            Timestamp tempStartTS;
//            Timestamp tempEndTS;
//            while (result.next()) {
//                //Start and End time from DB
//                tempStartTS = result.getTimestamp("start");
//                tempEndTS = result.getTimestamp("end");
//                
//                ZoneId newzid = ZoneId.systemDefault();
//                
//                // Covert Start and End time to local date and time
//                ZonedDateTime newzdtStart = tempStartTS.toLocalDateTime().atZone(ZoneId.of("UTC"));
//                ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);
//                LocalDateTime localStart = newLocalStart.toLocalDateTime();
//                Timestamp localStartTS = Timestamp.valueOf(localStart);
//                
//                ZonedDateTime newzdtEnd = tempEndTS.toLocalDateTime().atZone(ZoneId.of("UTC"));
//                ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(newzid);
//                LocalDateTime localEnd = newLocalEnd.toLocalDateTime();
//                Timestamp localEndTS = Timestamp.valueOf(localEnd);
//                
//                Appointment tempAppt = new Appointment(result.getInt("appointmentId"),
//                    result.getString("title"), result.getString("description"),
//                    result.getString("location"), result.getString("type"),
//                    localStartTS, localEndTS,
//                    result.getString("userName"));
//                apptList.add(tempAppt);
//            }
//            DBConnection.closeConnection();
//            
//            for (Appointment a:apptList) {
//                System.err.println(a.startTimeProperty().get() + ", " + a.endTimeProperty().get());
//            }
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex.getMessage());
//        }
//    }
      
    
}
