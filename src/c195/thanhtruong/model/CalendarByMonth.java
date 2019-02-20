
package c195.thanhtruong.model;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class consists of a list of all calendar dates by Month.
 * @author thanhtruong
 */
public class CalendarByMonth {
    
    Date currentDate;
    LocalDate lcDate;
    Calendar currentCalDate;
    List<LocalDate> allDatesList = new ArrayList<>();
    SimpleDateFormat dmf = new SimpleDateFormat("MMMMMMMMM yyyy");
    

    public CalendarByMonth() {
        //Get current Calendar Date
        this.currentDate = Calendar.getInstance().getTime();        
        this.lcDate = LocalDate.now();
        this.currentCalDate = Calendar.getInstance();
    }

    public LocalDate getLcDate() {
        return lcDate;
    }
    
    public Date getCurrentDate() {
        return currentDate;
    }   
        
    public String getMonthYearAsString(Date date) {        
        return dmf.format(date);
    }
    
    public String getMonthYearAsString(LocalDate lcDate) {
        return dmf.format(Date.from(lcDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
    
    public LocalDate getPreviousMonth() {
        // Convert to LocalDate in order to use minusMonth()
        lcDate = lcDate.minusMonths(1);
        // Update currentDate
        currentDate = Date.from(lcDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return lcDate;
    }
    
    public LocalDate getNextMonth() {
        // Convert to LocalDate in order to use minusMonth()
        lcDate = lcDate.plusMonths(1);
        // Update currentDate
        currentDate = Date.from(lcDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return lcDate;
    }
    
    public List<LocalDate> getAllDatesInMonth(int year, int month) {        
        allDatesList.clear();
        Calendar firstDate = Calendar.getInstance();
        firstDate.set(year, month-1, 1);
        LocalDate lcDate;
        lcDate = LocalDateTime.ofInstant(firstDate.toInstant(), firstDate.getTimeZone().toZoneId()).toLocalDate();
        // Add the first date to the list before incrementing in loop
        allDatesList.add(lcDate);
        int daysInMonth = firstDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i=0; i<daysInMonth-1; i++) {            
            firstDate.add(Calendar.DAY_OF_MONTH, 1);
            lcDate = LocalDateTime.ofInstant(firstDate.toInstant(), firstDate.getTimeZone().toZoneId()).toLocalDate();
            allDatesList.add(lcDate);
        }
        return allDatesList;
    }
    
    public int[] getDatesOnly(int year, int month) {
        allDatesList = getAllDatesInMonth(year, month);
        int[] allDates = new int[allDatesList.size()];
        int i = 0;
        for (LocalDate date:allDatesList) {
            allDates[i] = date.getDayOfMonth();
            i++;
        }
        return allDates;
    }
}
