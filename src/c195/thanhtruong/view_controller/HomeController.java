/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.service.ApptAlertService;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class HomeController extends AbstractController implements Initializable {
    
    @FXML
    private Button custModule;

    @FXML
    private Button calendarModule;

    @FXML
    private Button reportMenu;

    @FXML
    private Button logoutBut;
    
    @FXML
    void handleCalModButton(ActionEvent event) {
        Customer selectedCust = DialogPopup.selectCustomer();
        getDialogStage().close();
        if (selectedCust != null) {
            WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("CalendarByCust.fxml")
                .setTitle("Appointments")
                .setCustomer(selectedCust)
                .build();
            windowDisplay.displayScene();
        }
        
    }

    @FXML
    void logout(ActionEvent event) {
        DialogPopup.exitConfirmation(getDialogStage());
    }

    @FXML
    void handleCustModButton(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("CustomerList.fxml")
                .setTitle("Customer Maintenance")
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleReportMenu(ActionEvent event) {
        String selectedReport = DialogPopup.selectReport();
        getDialogStage().close();
        switch (selectedReport) {
            case "Total appointment by Type and Month":
                WindowsDisplay windowDisplay = new WindowsBuilder()
                    .setFXMLPath("ApptTypeByMonthReport.fxml")
                    .setTitle("Total Appointments by Type and Month")
                    .build();
                windowDisplay.displayScene();
        }
        
    }
    
    private void initService() {
        final ApptAlertService alertService = new ApptAlertService();
        alertService.setOnSucceeded(new EventHandler<WorkerStateEvent> () {
            @Override
            public void handle(WorkerStateEvent event) {
                checkUpcomingAppt();
                alertService.restart();
            }
        });
        alertService.start();
    }
    
    public void checkUpcomingAppt() {
        if (AppointmentDB.getInstance().getApptListByUser() != null) {
            for (Appointment appt:AppointmentDB.getInstance().getApptListByUser()) {
                LocalDateTime apptStart = appt.getStartDateTime().toLocalDateTime();
                if (apptStart.isAfter(LocalDateTime.now(ZoneId.systemDefault()))
                        && apptStart.isBefore(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(15))) {
                    DialogPopup.showAlert(getDialogStage(), "Reminder",
                        "Appointment start in 15 minutes",
                        appt.getTitle() + "\n" + "Meet with: " + appt.getCustName() + 
                        "\n" + appt.getStartDateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH : mm")) +
                        " - " + appt.getEndDateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH : mm")),
                        Alert.AlertType.INFORMATION);
                }
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void displayData(Customer selectedCust, Appointment appoinment) {
        checkUpcomingAppt();
        initService();
    }
    
}
