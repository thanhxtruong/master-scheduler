/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author TTruong
 */
public class ApptTypeCount {
    private Map<String, ObservableList<Integer>> apptTypeByMonth = new HashMap<>();
    private ObservableList<ApptTypeCount> apptCount = FXCollections.observableArrayList();
    private StringProperty apptType;
    private IntegerProperty count;

    public ApptTypeCount(StringProperty apptType, IntegerProperty count) {
        this.apptType = apptType;
        this.count = count;
    }
    
    public void mapApptByType() {
        AppointmentDB apptDB = AppointmentDB.getInstance();
        apptDB.downloadAppt(null);
        
        String key;
        for (Appointment appt:AppointmentDB.getInstance().getAllApptList()) {
            key = appt.getType();
            if (!apptTypeByMonth.containsKey(key)) {
                apptTypeByMonth.put(key, FXCollections.observableArrayList());
            }
            apptTypeByMonth.get(key).add(new Integer(appt.getAppointmentId()));
        }
        
        for (StringProperty )
    }

    public Map<String, ObservableList<Integer>> getApptTypeByMonth() {
        return apptTypeByMonth;
    }
    
    
}
