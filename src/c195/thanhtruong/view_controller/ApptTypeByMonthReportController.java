/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.ApptTypeCount;
import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author TTruong
 */
public class ApptTypeByMonthReportController extends AbstractController implements Initializable {

    @FXML
    private TableView<ApptTypeCount> apptTable;
    @FXML
    private TableColumn<ApptTypeCount, String> apptTypeCol;
    @FXML
    private TableColumn<ApptTypeCount, Integer> totalApptCol;
    @FXML
    private Button closeButton;
    
    private Map<String, Map<Integer, Map<Integer, ObservableList<Integer>>>> apptTypeByMonth = new TreeMap<>();
    private ObservableList<ApptTypeCount> apptCount = FXCollections.observableArrayList();
    
    private void mapApptByType() {
        AppointmentDB apptDB = AppointmentDB.getInstance();
        apptDB.downloadAppt(null);
        
        DateTimeFormatter moYrf = DateTimeFormatter.ofPattern("MMM yyyy");
        String typeKey;
        Integer yrKey;
        Integer moKey;
        for (Appointment appt:AppointmentDB.getInstance().getAllApptList()) {
            typeKey = appt.typeProperty().get();
            yrKey = appt.dateProperty().getValue().getYear();
            moKey = appt.dateProperty().getValue().getMonthValue();
            if (!apptTypeByMonth.containsKey(typeKey)) {
                apptTypeByMonth.put(typeKey, new TreeMap<>());
            }
            if (!apptTypeByMonth.get(typeKey).containsKey(yrKey)) {
                apptTypeByMonth.get(typeKey).put(yrKey, new TreeMap<>());
            }
            if (!apptTypeByMonth.get(typeKey).get(yrKey).containsKey(moKey)) {
                apptTypeByMonth.get(typeKey).get(yrKey).put(moKey, FXCollections.observableArrayList());
            }
            apptTypeByMonth.get(typeKey).get(yrKey).get(moKey).add(appt.getAppointmentId());
        }
        
        for (String key1:apptTypeByMonth.keySet()) {
            ApptTypeCount apptTypeCount = new ApptTypeCount(key1, apptTypeByMonth.get(key1).size());
            apptCount.add(apptTypeCount);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void handleClose(ActionEvent event) {
        
    }

    @Override
    public void displayData(Customer selectedCust,
            Appointment appointment) {
        mapApptByType();
        apptTypeCol.setCellValueFactory(cellData -> cellData.getValue().apptTypeProperty());
        totalApptCol.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        apptTable.setItems(apptCount);
    }
    
}
