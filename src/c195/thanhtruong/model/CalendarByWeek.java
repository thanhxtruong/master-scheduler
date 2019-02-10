/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import java.util.Date;
import java.util.List;

/**
 *
 * @author thanhtruong
 */
public class CalendarByWeek {
    List<LocalDate> allDatesList = new ArrayList<>();
    static SimpleDateFormat mdyf = new SimpleDateFormat("MMM d, yyyy");
        
    public static String getDateAsString(Calendar firstDateOfWeek) {
        Calendar lastDateOfWeek = (Calendar)firstDateOfWeek.clone();
        lastDateOfWeek.add(DAY_OF_MONTH, 6);
        return mdyf.format(firstDateOfWeek.getTime()) + " - " + mdyf.format(lastDateOfWeek.getTime());
    }
    
    public static Calendar getPreviousWeek(Calendar currentFirst) {
        currentFirst.add(DAY_OF_MONTH, -7);
        return currentFirst;
    }
    
    public static Calendar getNextWeek(Calendar currentFirst) {
        currentFirst.add(DAY_OF_MONTH, 7);
        return currentFirst;
    }
}
