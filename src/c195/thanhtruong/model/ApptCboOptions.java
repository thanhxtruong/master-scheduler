/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Singleton class used to effectively create the final list of all options for
 * comboboxes used in GUI.
 * @author thanhtruong
 */
public class ApptCboOptions {    
    
    private static ObservableList<String> typeList = FXCollections.observableArrayList();
    private static ObservableList<String> hourList = FXCollections.observableArrayList();
    private static Map<Integer, String> hourMap = new TreeMap<>();
    private static ObservableList<String> minuteList = FXCollections.observableArrayList();
    private static Map<Integer, String> minuteMap = new TreeMap<>();

    private static final ApptCboOptions instance;
    
    static {
        instance = new ApptCboOptions();
        typeList.addAll("Consultance", "Product Launch");
        hourList.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minuteList.addAll("00", "15", "30", "45");
        int i, j;
        for (i=0, j=0; i<24; i++, j++) {
            hourMap.put(i, hourList.get(j));
        }
        for (i=0, j=0; i<4; i++, j++) {
            minuteMap.put(i, minuteList.get(j));
        }
    }
    
    private ApptCboOptions() {}       
    
    public static ApptCboOptions getInstance() {
        return instance;
    }

    public ObservableList<String> getTypeList() {
        return typeList;
    }

    public Map<Integer, String> getHourMap() {
        return hourMap;
    }

    public Map<Integer, String> getMinuteMap() {
        return minuteMap;
    }

    public ObservableList<String> getHourList() {
        return hourList;
    }

    public  ObservableList<String> getMinuteList() {
        return minuteList;
    }
    
    public String getValueByKey(Integer k) {
        for (Integer key:hourMap.keySet()) {
            if (key.equals(k))
                return hourMap.get(key);
        }
        return null;
    }
        
}
