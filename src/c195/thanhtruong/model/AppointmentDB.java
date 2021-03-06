
package c195.thanhtruong.model;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.service.ActivityLogger;
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
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;
import java.util.Calendar;
import static java.util.Calendar.WEEK_OF_YEAR;
import static java.time.ZonedDateTime.now;


/**
 * The AppointmentDB class is used to download appointments from the MYSQL database
 * and to retrieve lists/maps of appointments for other method calls.
 * This class uses the Singleton design pattern to allow only one object to be created
 * in memory and shared among multiple classes as the lists/maps of appointments
 * are updated/downloaded from the DB after each INSERT, DELETE, UPDATE query.
 * @author thanhtruong
 */
public class AppointmentDB {
    
    private static ObservableList<Appointment> apptListByCust;
    private static ObservableList<Appointment> apptListByUser;
    private static ObservableList<Appointment> allApptList;
    private static ObservableList<Appointment> sortedList = FXCollections.observableArrayList();
    private static Map<Integer, Map<Integer, ObservableList<String>>> apptMapByMonth;
    private static Map<Integer, Map<Integer, ObservableList<Appointment>>> apptMapByWeek;
    private static final AppointmentDB instance;
    private String sqlStatement;
    private static boolean overlappedAppt;
    
    static {
        instance = new AppointmentDB();
        // Callback functional interface is used to listen for changes on the 
        // elements, which update the TableView automatically without the need
        // to reload the FXML
        Callback<Appointment, Observable[]> extractor = (Appointment a) -> new Observable[] {
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
            a.userNameProperty(),
            a.custNameProperty()
        };
        apptListByCust = FXCollections.observableArrayList(extractor);
        apptListByUser = FXCollections.observableArrayList(extractor);
        allApptList = FXCollections.observableArrayList(extractor);
    }
    private AppointmentDB() {}
    
    public static AppointmentDB getInstance() {
        return instance;
    }
    
    public ObservableList<Appointment> getApptListByCust() {
        return apptListByCust;
    }
    
    public ObservableList<Appointment> getApptListByUser() {
        return apptListByUser;
    }

    public ObservableList<Appointment> getAllApptList() {
        return allApptList;
    }

    public static boolean isOverlappedAppt() {
        return overlappedAppt;
    }
    
    /**
     * This method downloads appointments by either selected Customer or User.
     * Appointment time is converted to the local date and time.
     * @param model Customer or User or null. When null, all appointments will be downloaded.
     */
    public void downloadAppt(AbstractModel model) {
        // Case model to either Customer or User obj
        String modelClass;
        if (model != null) {
            ModelFactory.getModelClass(model);
            modelClass = model.getClass().getSimpleName();
        } else {
            modelClass = "all";
        }
        switch (modelClass) {
            case "Customer": 
                apptListByCust.clear();
                sqlStatement = "SELECT customerName, appointmentId, "
                    + "appointment.customerId, title, description, location, contact, "
                    + "start, end, type, appointment.userId, userName\n"
                    + "FROM appointment\n"
                    + "INNER JOIN customer\n"
                    + "ON appointment.customerId = customer.customerId\n"
                    + "INNER JOIN user\n"
                    + "ON appointment.userId = user.userId\n"
                    + "WHERE customer.customerId = " + ((Customer)model).getCustomerID();
                break;
            case "User":
                apptListByUser.clear();
                sqlStatement = "SELECT customerName, appointmentId, "
                + "appointment.customerId, title, description, location, contact, "
                + "start, end, type, appointment.userId, userName\n"
                + "FROM appointment\n"
                + "INNER JOIN customer\n"
                + "ON appointment.customerId = customer.customerId\n"
                + "INNER JOIN user\n"
                + "ON appointment.userId = user.userId\n"
                + "WHERE appointment.userId = " + ((User)model).getUserId();
                break;
            case "all":
                sqlStatement = "SELECT customerName, appointmentId, "
                + "appointment.customerId, title, description, location, contact, "
                + "start, end, type, appointment.userId, userName\n"
                + "FROM appointment\n"
                + "INNER JOIN customer\n"
                + "ON appointment.customerId = customer.customerId\n"
                + "INNER JOIN user\n"
                + "ON appointment.userId = user.userId";
                break;
        }
        
        try {
            // Connect to the DB
            DBConnection.makeConnection();
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
                    result.getString("userName"), result.getString("customerName"));
                
                switch (modelClass) {
                    case "Customer":
                        apptListByCust.add(tempAppt);
                        break;
                    case "User":
                        apptListByUser.add(tempAppt);
                        break;
                    case "all":
                        allApptList.add(tempAppt);
                        break;
                }
            }
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }            
    }
    
    /**
     * This method adds new appointment to the MySQL DB.
     * The event is logged in the ActivityLogger.
     * The apptListByCust is updated after insertion.
     * @param newAppt
     * @param selectedCust 
     */
    public void insertAppt(Appointment newAppt, Customer selectedCust) {
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            // Start and End Time have been converted to DB TimeZone in AddAppointmentController
            sqlStatement = "INSERT INTO appointment\n" +
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
            
            // Get appointmentId for the new appointment
            sqlStatement = "SELECT last_insert_id() FROM appointment";
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();
            result = Query.getResult();
            result.next();
            int apptID = result.getInt("last_insert_id()");
            
            ActivityLogger.logActivities(MainApp.getCurrentUser().getUserName() +
                    " added a new appointmentId #" + apptID);
            
            DBConnection.closeConnection();
            
            //Update apptListByCust;
            downloadAppt(selectedCust);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    /**
     * This method updates a current appointment in the MySQL DB.
     * The event is logged in the ActivityLogger.
     * The apptListByCust is updated after insertion.
     * @param newAppt
     * @param selectedAppt
     * @param selectedCust 
     */
    public void updateAppt(Appointment newAppt, Appointment selectedAppt, Customer selectedCust) {
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
            
            ActivityLogger.logActivities(MainApp.getCurrentUser().getUserName() + 
                    " updated appointmentId #" + selectedAppt.getAppointmentId() );
            
            DBConnection.closeConnection();
            
            downloadAppt(selectedCust);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    /**
     * This method deletes an appointment from the MySQL DB.
     * The event is logged in the ActivityLogger.
     * The apptListByCust is updated after insertion.
     * @param selectedAppt
     * @param selectedCust 
     */
    public void deleteAppt(Appointment selectedAppt, Customer selectedCust ) {
        try {
            
            int apptID = selectedAppt.getAppointmentId();
            // Connect to the DB
            DBConnection.makeConnection();
            
            sqlStatement = "DELETE FROM appointment\n" +
                            "WHERE appointmentId = " + selectedAppt.getAppointmentId();
            Query.makeQuery(sqlStatement);
            
            ActivityLogger.logActivities(MainApp.getCurrentUser().getUserName() +
                    " deleted appointmentId #" + apptID);
                        
            DBConnection.closeConnection();
            
            downloadAppt(selectedCust);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
    /**
     * This method sorts a list of appointments by month then by dates in a month.
     * @return sortedList
     */
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
    
    /**
     * This method sorts a list of appointments by Week then by dates in a week.
     * @return sortedList
     */
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
    
    /**
     * This method takes a LocalDate as an input and returns the Week of Year 
     * for the given date.
     * This method is used in the Collections.sort() functional interface of the 
     * sortApptByWeek() method as well as in the key-value mapping of the
     * getApptMapByWeek() method.
     * @param lcDate
     * @return int value of WEEK_OF_YEAR
     */
    public int getWeekOfYear(LocalDate lcDate) {
        Calendar calDate = Calendar.getInstance();
        calDate.set(lcDate.getYear(), lcDate.getMonthValue()-1, lcDate.getDayOfMonth());
        return calDate.get(WEEK_OF_YEAR);
    }
    
    /**
     * This method takes an ObservableList of sorted appointments and map it by
     * month then by dates in the month.
     * @param sortedList ObservableList of appointments sorted by month and dates
     * @return a 2-layer map of appointments mapped by month (key1) and dates (key2)
     */
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
    
    /**
     * This method takes an ObservableList of sorted appointments and map it by
     * week then by dates in the week.
     * @param sortedList ObservableList of appointments sorted by week and dates
     * @return a 2-layer map of appointments mapped by week (key1) and dates (key2)
     */
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
    
    /**
     * Retrieve lists of all appointments mapped by dates for the given month.
     * @param month Ranges from 1 to 12
     * @param apptMap Map of lists of all appointment by month (outer layer) and
     * dates in month (inner layer).
     * @return lists of all appointments mapped by dates for the given month
     */
    public Map<Integer, ObservableList<String>> checkApptByMonth(int month, Map<Integer, Map<Integer, ObservableList<String>>> apptMap) {
        for (Integer key:apptMap.keySet()) {
            if (key.intValue() == month) {
                return apptMap.get(key);
            }                
        }
        return null;
    }
    
    /**
     * Retrieve lists of all appointments mapped by dates for the given week.
     * @param week int value of the WEEK_OF_YEAR
     * @param apptMap Map of lists of all appointment by week (outer layer) and
     * dates in month (inner layer).
     * @return lists of all appointments mapped by dates for the given week
     */
    public Map<Integer, ObservableList<Appointment>> checkApptByWeek(int weekOfYear, Map<Integer, Map<Integer, ObservableList<Appointment>>> apptMap) {
        for (Integer key:apptMap.keySet()) {
            if (key.intValue() == weekOfYear) {
                return apptMap.get(key);
            }                
        }
        return null;
    }
    
    /**
     * This method is used to check if there are any appointments in the list of
     * all appointments by Month for the given date.
     * @param apptInMonth Lists of all appointments mapped by dates of a given month.
     * @param dateToMatch Date to check for scheduled appointment(s).
     * @return list of scheduled appointments or null.
     */
    public ObservableList<String> matchApptDatesInMonth(Map<Integer, ObservableList<String>> apptInMonth, int dateToMatch) {
        if (apptInMonth.containsKey(Integer.valueOf(dateToMatch))) {
            return apptInMonth.get(Integer.valueOf(dateToMatch));
        }
        return null;
    }
    
    /**
     * This method is used to check if there are any appointments in the list of
     * all appointments by Week for the given date.
     * @param apptInWeek Lists of all appointments mapped by dates of a given week.
     * @param dateToMatch Date to check for scheduled appointment(s).
     * @return list of scheduled appointments or null.
     */
    public ObservableList<Appointment> matchApptDatesInWeek(Map<Integer, ObservableList<Appointment>> apptInWeek, int dateToMatch) {
        if (apptInWeek.containsKey(Integer.valueOf(dateToMatch))) {
            return apptInWeek.get(Integer.valueOf(dateToMatch));
        }
        return null;
    }
    
    /**
     * This method checks for overlapping appointments for user input validation.
     * The method checks for conflict within the apptListByCust first before
     * checking for conflicts within the apptListByUser. The IllegalArgumentException
     * is thrown if there is conflict. The exception is caught and resolved within
     * the method calling this method validation (see AddAppointmentController and
     * EditAppointmentController). The static boolean "overlappedAppt" is set to 
     * true if there is a conflict, which is used as a flag in the "finally" section
     * of the try-catch-finally exception handling method of the caller.
     * @param nStartDT the Start datetime value of the new appointment
     * @param nEndDT the End datetime value of the new appointment
     */
    public void checkOverlappingAppt(LocalDateTime nStartDT, LocalDateTime nEndDT) {
        // Check for conflict within the apptListByCust
        LocalDateTime cStartDT, cEndDT;
        for (Appointment appt:apptListByCust) {
            cStartDT = appt.getStartDateTime().toLocalDateTime();
            cEndDT = appt.getEndDateTime().toLocalDateTime();
            if (nStartDT.isEqual(cStartDT) || nEndDT.isEqual(cEndDT) ||
                    (nStartDT.isAfter(cStartDT) && nStartDT.isBefore(cEndDT)) ||
                    (nEndDT.isAfter(cStartDT) && nEndDT.isBefore(cEndDT))) {
                overlappedAppt = true;
                throw new IllegalArgumentException(
                    "New appointment time is conflicting with existing AppointmentId #" + 
                    appt.getAppointmentId());
            }
        }
        //Check for conflict within apptListByUser
        for (Appointment appt:apptListByUser) {
            cStartDT = appt.getStartDateTime().toLocalDateTime();
            cEndDT = appt.getEndDateTime().toLocalDateTime();
            if (nStartDT.isEqual(cStartDT) || nEndDT.isEqual(cEndDT) ||
                    (nStartDT.isAfter(cStartDT) && nStartDT.isBefore(cEndDT)) ||
                    (nEndDT.isAfter(cStartDT) && nEndDT.isBefore(cEndDT))) {
                overlappedAppt = true;
                throw new IllegalArgumentException(
                    "New appointment time is conflicting with existing AppointmentId #" + 
                    appt.getAppointmentId());
            }
        }
        overlappedAppt = false;
    }
}
