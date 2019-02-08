/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
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
//    public static void main(String[] args) {
//        List<LocalDate> allDatesOfMonth = new ArrayList<>();
//        LocalDate date = LocalDate.now();
//        // Get the current day
//        System.out.println(date.getDayOfWeek());
//        
//        // Get the current date
//        Calendar firstDayofMonth = Calendar.getInstance();
//        // Get the current month
//        System.out.println(date.getMonth());
//        // Get the current year
//        System.out.println(date.getYear());
//        // Get the first date of the month        
//        firstDayofMonth.set(Calendar.DAY_OF_MONTH, 1);
//        System.out.println("firstDayOfMonth.get(DAY_OF_WEEK): " + firstDayofMonth.get(DAY_OF_WEEK));
//        Date day = firstDayofMonth.getTime();
//        System.out.println("firstDayOfMonth.getTime(): " + day);
//        DateFormat fd = new SimpleDateFormat("EEEEEEEE");
//        System.out.println("firstDayOfMonth: " + fd.format(day));
//        
//        
//        // OR. However, this doesn't give the actual day
//        LocalDate firstDayofMonth2 = LocalDate.now();
//        System.out.println(firstDayofMonth2.withDayOfMonth(1));
//        
//        YearMonth ym = YearMonth.of(2019, Month.FEBRUARY);
//        LocalDate firstOfMonth = ym.atDay(1);
//        LocalDate firstOfFollowingMonth = ym.plusMonths(1).atDay(1);
//        System.out.println(firstOfMonth.until(firstOfFollowingMonth, ChronoUnit.DAYS));
//        
//        Calendar cal = Calendar.getInstance();
//        cal.clear();
//        cal.set(date.getYear(), date.getMonthValue() - 1, 1);
//        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        for (int i=0; i<daysInMonth; i++) {
//            LocalDate days = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
//            System.out.println(days.format(DateTimeFormatter.ISO_DATE));
//            cal.add(Calendar.DAY_OF_MONTH, 1);
//        }
//        
//        // Display current date as Month Year
//        SimpleDateFormat dmf = new SimpleDateFormat("MMMMMMMMM yyyy");
//        System.out.println(dmf.format(cal.getTime()));
//        
//        LocalDate previousMonth = LocalDate.of(2019, Month.JANUARY, 1).minusMonths(1);
//        System.out.println(previousMonth);
//    }
}
