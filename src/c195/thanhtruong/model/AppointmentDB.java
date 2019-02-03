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
import java.util.Map;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author thanhtruong
 */
public class AppointmentDB {
    
    private static ObservableList<Appointment> apptListByCust;

    public AppointmentDB() {
        this.apptListByCust = FXCollections.observableArrayList();
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
            System.err.println(sqlStatement);
            
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
            System.err.println("selectedCustomer from AppointmentDB: " + selectedCust.getCustomerName());
            // Connect to the DB
            DBConnection.makeConnection();
            
            String sqlStatement = "INSERT INTO appointment\n" +
                "(customerId, title, description, location, contact, url, start, end, "
                + "createDate, createdBy, lastUpdate, lastUpdateBy, type, userId)\n" +
                "VALUES (" + selectedCust.getCustomerID() + ", '" + newAppt.getTitle() +
                "', '" + newAppt.getDescription() + "', '" + newAppt.getLocation() +
                "', 'not used', 'not used', '" + newAppt.getStartTime() + "', '" + newAppt.getEndTime() +
                "', '" + Timestamp.valueOf(now(ZoneId.of("UTC")).toLocalDateTime()) + "', '" + 
                MainApp.getCurrentUser().getUserName() +
                "', '" + Timestamp.valueOf(now(ZoneId.of("UTC")).toLocalDateTime()) + "', '" + 
                MainApp.getCurrentUser().getUserName() +
                "', '" + newAppt.getType() + "', " + MainApp.getCurrentUser().getUserId() +")";
            
            System.err.println(sqlStatement);
            
            Query.makeQuery(sqlStatement);
            
            //Update apptListByCust;
            downloadAppt(selectedCust);
            
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
//    public static void main(String[] args) {
//        ObservableList<Appointment> apptListByCust = FXCollections.observableArrayList();
//        Map<Customer, ObservableList<Appointment>> apptMapByCust = new TreeMap<>();
//        
//        System.err.println(now(ZoneId.of("UTC")));
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
//                apptListByCust.add(tempAppt);
//            }
//            DBConnection.closeConnection();
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex.getMessage());
//        }
//        
//        for (Appointment appt:apptListByCust) {
//            System.out.println(appt.getStartTime());
//        }        
//          
//    }
    
    
}
