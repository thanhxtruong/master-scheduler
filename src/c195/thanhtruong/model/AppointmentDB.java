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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import static java.time.ZonedDateTime.now;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.WEEK_OF_YEAR;
import java.util.Locale;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;
import static java.time.ZonedDateTime.now;


/**
 *
 * @author thanhtruong
 */
public class AppointmentDB {
    
    private static ObservableList<Appointment> apptListByCust;
    private static ObservableList<Appointment> sortedList = FXCollections.observableArrayList();
    private static Map<Integer, Map<Integer, ObservableList<String>>> apptMapByMonth;
    private static Map<Integer, Map<Integer, ObservableList<Appointment>>> apptMapByWeek;
    private static final AppointmentDB instance;
    
    static {
        instance = new AppointmentDB();
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
        ListChangeListener listener = new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                while (c.next()) {
                    if (c.wasUpdated()) {
                        int start = c.getFrom();
                        int end = c.getTo();
                    }
                }
            }
            
        };
        apptListByCust = FXCollections.observableArrayList(extractor);
        
        apptListByCust.addListener(listener);
    }
    private AppointmentDB() {}
    
    public static AppointmentDB getInstance() {
        return instance;
    }
    
    public ObservableList<Appointment> getApptListByCust() {
        return apptListByCust;
    }
        
    public void downloadAppt(Customer selectedCust) {
        try {
            apptListByCust.clear();
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
            
            DBConnection.closeConnection();
            
            //Update apptListByCust;
            downloadAppt(selectedCust);
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
            Query.makeQuery(sqlStatement);
            
            DBConnection.closeConnection();
            
            downloadAppt(selectedCust);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public void deleteAppt(Appointment selectedAppt, Customer selectedCust ) {
        String sqlStatement;
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            sqlStatement = "DELETE FROM appointment\n" +
                            "WHERE appointmentId = " + selectedAppt.getAppointmentId();
            Query.makeQuery(sqlStatement);          
                        
            DBConnection.closeConnection();
            
            downloadAppt(selectedCust);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
    public ObservableList<Appointment> sortApptByMonth() {
        sortedList.clear();
        sortedList.addAll(apptListByCust);
        Collections.sort(sortedList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                int month1 = ((Appointment) o1).dateProperty().get().getMonthValue();
                int month2 = ((Appointment) o2).dateProperty().get().getMonthValue();

                int mComp = month1 - month2;
                if (mComp != 0) {
                    return mComp;
                } else {
                    int date1 = ((Appointment) o1).dateProperty().get().getDayOfMonth();
                    int date2 = ((Appointment) o2).dateProperty().get().getDayOfMonth();

                    return date1 - date2;
                }
            }            
        });        
        return sortedList;
    }
    
    public ObservableList<Appointment> sortApptByWeek() {
        sortedList.clear();
        sortedList.addAll(apptListByCust);
        Collections.sort(sortedList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                int week1 = getWeekOfYear(((Appointment) o1).dateProperty().get());
                int week2 = getWeekOfYear(((Appointment) o2).dateProperty().get());

                int wComp = week1 - week2;
                if (wComp != 0) {
                    return wComp;
                } else {
                    int date1 = ((Appointment) o1).dateProperty().get().getDayOfMonth();
                    int date2 = ((Appointment) o2).dateProperty().get().getDayOfMonth();
                    return date1 - date2;
                }
            }            
        });        
        return sortedList;
    }
    
    public int getWeekOfYear(LocalDate lcDate) {
        Calendar calDate = Calendar.getInstance();
        calDate.set(lcDate.getYear(), lcDate.getMonthValue()-1, lcDate.getDayOfMonth());
        return calDate.get(WEEK_OF_YEAR);
    }
    
    public Map<Integer, Map<Integer, ObservableList<String>>> getApptMapByMonth(ObservableList<Appointment> sortedList) {        
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
        
        apptMapByMonth = new TreeMap<>();
        Integer mKey;
        Integer dKey;
        for (Appointment appt:sortedList) {
            mKey = appt.dateProperty().get().getMonthValue();
            dKey = appt.dateProperty().get().getDayOfMonth();
            if (!apptMapByMonth.containsKey(mKey)) {
                apptMapByMonth.put(mKey, new TreeMap<>());
            }
            if (!apptMapByMonth.get(mKey).containsKey(dKey)) {
                apptMapByMonth.get(mKey).put(dKey, FXCollections.observableArrayList());
            }
            apptMapByMonth.get(mKey).get(dKey).add(appt.getStartDateTime().toLocalDateTime().format(tf) + " " + appt.getType());
        }
        return apptMapByMonth;
    }
    
    public Map<Integer, Map<Integer, ObservableList<Appointment>>> getApptMapByWeek(ObservableList<Appointment> sortedList) {
        
        apptMapByWeek = new TreeMap<>();
        Integer wKey;
        Integer dKey;
        for (Appointment appt:sortedList) {
            wKey = getWeekOfYear(appt.dateProperty().get());
            dKey = appt.dateProperty().get().getDayOfMonth();
            if (!apptMapByWeek.containsKey(wKey)) {
                apptMapByWeek.put(wKey, new TreeMap<>());
            }
            if (!apptMapByWeek.get(wKey).containsKey(dKey)) {
                apptMapByWeek.get(wKey).put(dKey, FXCollections.observableArrayList());
            }
            apptMapByWeek.get(wKey).get(dKey).add(appt);
        }
        return apptMapByWeek;
    }
    
    /*
    month is from 1 - 12
    */
    public Map<Integer, ObservableList<String>> checkApptByMonth(int month, Map<Integer, Map<Integer, ObservableList<String>>> apptMap) {
        for (Integer key:apptMap.keySet()) {
            if (key.intValue() == month) {
                return apptMap.get(key);
            }                
        }
        return null;
    }
    
    public Map<Integer, ObservableList<Appointment>> checkApptByWeek(int weekOfYear, Map<Integer, Map<Integer, ObservableList<Appointment>>> apptMap) {
        for (Integer key:apptMap.keySet()) {
            if (key.intValue() == weekOfYear) {
                return apptMap.get(key);
            }                
        }
        return null;
    }
    
    public ObservableList<String> matchApptDatesInMonth(Map<Integer, ObservableList<String>> apptInMonth, int dateToMatch) {
        if (apptInMonth.containsKey(Integer.valueOf(dateToMatch))) {
            return apptInMonth.get(Integer.valueOf(dateToMatch));
        }
        return null;
    }
    
    public ObservableList<Appointment> matchApptDatesInWeek(Map<Integer, ObservableList<Appointment>> apptInWeek, int dateToMatch) {
        if (apptInWeek.containsKey(Integer.valueOf(dateToMatch))) {
            return apptInWeek.get(Integer.valueOf(dateToMatch));
        }
        return null;
    }
    
    public static void main(String[] args) {
        final AppointmentDB apptDB = AppointmentDB.getInstance();
    }  
    
}
