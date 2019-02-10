/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.ApptCboOptions;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.DataInput;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class AddAppointmentController extends AbstractController implements Initializable {
    @FXML
    private TextField apptTitle;

    @FXML
    private TextField apptDescription;

    @FXML
    private TextField location;

    @FXML
    private DatePicker apptDate;

    @FXML
    private ComboBox<String> apptType;

    @FXML
    private ComboBox<String> apptStartHr;

    @FXML
    private ComboBox<String> apptStartMin;

    @FXML
    private ComboBox<String> apptEndHr;

    @FXML
    private ComboBox<String> apptEndMin;

    @FXML
    private Button addApptCancel;

    @FXML
    private Button addApptButton;
    
    private Customer selectedCust;    
    
    @FXML
    void handleAddAppt(ActionEvent event) {
        String title = apptTitle.getText();
        String description = apptDescription.getText();
        String loc = location.getText();
        String type = apptType.getSelectionModel().getSelectedItem();
        
        String date;
        if (apptDate.getValue() != null) {
            date = apptDate.getValue().toString();
        } else {
            date = null;
        }
        
        String startHr = apptStartHr.getSelectionModel().getSelectedItem();
        String startMin = apptStartMin.getSelectionModel().getSelectedItem();
        String endHr = apptEndHr.getSelectionModel().getSelectedItem();
        String endMin = apptEndMin.getSelectionModel().getSelectedItem();
        
        boolean missingInput = DataInput.isInputMissing(title, description, loc, type, date,
                                    startHr, startMin, endHr, endMin);
        if (!missingInput) {
            // Concatanate the String Start DateTime3
            String startdtConcat = date + " " + startHr + ":" + startMin + ":00.0";
            String enddtConcat = date + " " + endHr + ":" + endMin + ":00.0";
            
            // Parse String to LocalDateTime in order to covert to timezone in local DB
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss.S");
            LocalDateTime ldtStart = LocalDateTime.parse(startdtConcat, df);
            LocalDateTime ldtEnd = LocalDateTime.parse(enddtConcat, df);
            
            // Convert to DB timezone
            ZonedDateTime dbzdtStart = ldtStart.atZone(ZoneId.of("UTC"));
            ZonedDateTime dbzdtEnd = ldtEnd.atZone(ZoneId.of("UTC"));
            
            // Convert the ZonedDateTime back to LocalDateTime in order to convert it back to Timestamp
            LocalDateTime lcdbzdtStart = dbzdtStart.toLocalDateTime();
            LocalDateTime lcdbzdtEnd = dbzdtEnd.toLocalDateTime();
            Timestamp lcdbzTSStart = Timestamp.valueOf(lcdbzdtStart);
            Timestamp lcdbzTSEnd = Timestamp.valueOf(lcdbzdtEnd);
            
            // Now, we're ready to create the new appointment to add to the DB
            Appointment newAppt = new Appointment(title, description, loc, type,
                    lcdbzTSStart,lcdbzTSEnd, MainApp.getCurrentUser().getUserName());
            
            AppointmentDB.getInstance().insertAppt(newAppt, selectedCust);
            
            getDialogStage().close();
        } else {
            DialogPopup.showAlert(getDialogStage(),
                                    "Warning",
                                    "Missing input!",
                                    "Please, fill in the missing input",
                                    AlertType.ERROR);
        }
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("CalendarByCust.fxml")
                .setTitle("Appointments")
                .setCustomer(selectedCust)
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleCancelAddAppt(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("CalendarByCust.fxml")
                .setTitle("Appointments")
                .setCustomer(selectedCust)
                .build();
        windowDisplay.displayScene();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apptStartHr.setItems(ApptCboOptions.getInstance().getHourList());
        apptStartMin.setItems(ApptCboOptions.getInstance().getMinuteList());
        apptEndHr.setItems(ApptCboOptions.getInstance().getHourList());
        apptEndMin.setItems(ApptCboOptions.getInstance().getMinuteList());
        apptType.setItems(ApptCboOptions.getInstance().getTypeList());
    }    

    @Override
    public void displayData(Customer selectedCust, Appointment appointment) {
        this.selectedCust = selectedCust;
    }
    
}
