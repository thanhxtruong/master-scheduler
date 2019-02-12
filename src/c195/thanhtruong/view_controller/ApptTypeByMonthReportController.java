/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.ApptTypeCount;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
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
public class ApptTypeByMonthReportController implements Initializable {

    @FXML
    private TableView<HashMap> apptTable;
    @FXML
    private TableColumn<HashMap, String> apptTypeCol;
    @FXML
    private TableColumn<HashMap, Integer> totalAppt;
    @FXML
    private Button closeButton;
    
    private Map<String, ObservableList<Integer>> apptTypeByMonth = new HashMap<>();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ApptTypeCount apptTypeByMonth = new ApptTypeCount();
        apptTypeByMonth.mapApptByType();
        apptTypeByMonth
        apptTypeCol.setCellValueFactory(cellData -> cellData.getValue().);
        apptTable.setItems(AppointmentDB.getInstance().getApptListByCust());
    }    

    @FXML
    private void handleClose(ActionEvent event) {
        
    }
    
}
