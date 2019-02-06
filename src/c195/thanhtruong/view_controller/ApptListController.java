/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class ApptListController extends AbstractController implements Initializable {

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
    private TextField apptSearchText;
    @FXML
    private Button searchButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button modifyButton;
    @FXML
    private Button closeButton;
    
    private Customer selectedCust;
    
    @FXML
    private void handleDeleteCust(ActionEvent event) {
    }

    @FXML
    private void handleModifyCust(ActionEvent event) {
        System.err.println("Selected from table: " + apptTable.getSelectionModel().getSelectedItem().getStartDateTime());
        Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();
        System.err.println("Set selected appointment: " + selectedAppt.getStartDateTime());
        Stage currentStage = getDialogStage();
        
        if (selectedAppt != null) {
            WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("EditAppointment.fxml")
                .setTitle("Update Appointment")
                .setOwnerStage(currentStage)
                .setCustomer(selectedCust)
                .setAppointment(selectedAppt)
                .build();
            windowDisplay.displayScene();
        } else {
            DialogPopup.showAlert(getDialogStage(), "Attention",
                    "No appointment selected!", "Please, select an appointment to modify!");
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        
    }    

    @Override
    public void displayCustData(Customer selectedCust, Appointment appointment) {
        this.selectedCust = selectedCust;
        
        AppointmentDB apptDB = new AppointmentDB();
        apptDB.downloadAppt(this.selectedCust);
        
        apptTitleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        apptDescCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        apptLocationCol.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        apptTypeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        apptDateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        apptStartTimeCol.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        apptEndTimeCol.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
        apptTable.setItems(apptDB.getApptListByCust());
    }

    
    
}
