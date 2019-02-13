/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.Customer;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class AllApptReportController extends AbstractController implements Initializable {

    @FXML
    private TableView<Appointment> apptTable;
    @FXML
    private TableColumn<Appointment, String> apptTitleCol;
    @FXML
    private TableColumn<Appointment, String> apptDescCol;
    @FXML
    private TableColumn<Appointment, String> apptLocationCol;
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;
    @FXML
    private TableColumn<Appointment, LocalDate> apptDateCol;
    @FXML
    private TableColumn<Appointment, LocalTime> apptStartTimeCol;
    @FXML
    private TableColumn<Appointment, LocalTime> apptEndTimeCol;
    @FXML
    private TableColumn<Appointment, String> custCol;
    @FXML
    private TableColumn<Appointment, String> consultantCol;
    @FXML
    private Button closeButton;
    @FXML
    private Button saveBut;
    
    private ObservableList<Appointment> apptList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleClose(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("Home.fxml")
                .setTitle("Home")
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    private void saveReport(ActionEvent event) {
        
    }

    @Override
    public void displayData(Customer selectedCust, Appointment appointment) {
        AppointmentDB apptDB = AppointmentDB.getInstance();
        apptDB.downloadAppt(null);
        apptList = apptDB.getAllApptList();
        apptList.sorted(new Comparator<Appointment>() {
            @Override
            public int compare(Appointment appt1, Appointment appt2) {
                String user1 = appt1.getUserName();
                String user2 = appt2.getUserName();
                int uComp = user1.compareToIgnoreCase(user2);
                if (uComp != 0) {
                    return uComp;
                } else {
                    String cust1 = appt1.getCustName();
                    String cust2 = appt2.getCustName();
                    return cust1.compareToIgnoreCase(cust2);
                }
            }
        });
        
        apptTitleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        apptDescCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        apptLocationCol.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        apptTypeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        apptDateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        apptStartTimeCol.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        apptEndTimeCol.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
        custCol.setCellValueFactory(cellData -> cellData.getValue().custNameProperty());
        consultantCol.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        apptTable.setItems(apptList);
    }
    
}
