
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
public class EditAppointmentController extends AbstractController implements Initializable {

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
    private Button updateApptCancel;

    @FXML
    private Button saveUpdateButton;
    
    private Customer selectedCust;
    private Appointment tempAppt;
    private boolean validApptDate;

    @FXML
    void handleCancelUpdateAppt(ActionEvent event) {
        DialogPopup.exitConfirmation(getDialogStage());
    }

    /**
     * Event handler for the "Save" (appointment) button.
     * This methods validates user's input to check for missing required input,
     * conflict in appointment time, and appointments outside of business hours.
     * @param event 
     */
    @FXML
    void handleSaveUpdate(ActionEvent event) {
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
        
        // Concatanate the String Start DateTime
        String startdtConcat = date + " " + startHr + ":" + startMin + ":00.0";
        String enddtConcat = date + " " + endHr + ":" + endMin + ":00.0";

        // Parse String to LocalDateTime in order to covert to timezone in local DB
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss.S");
        LocalDateTime ldtStart = LocalDateTime.parse(startdtConcat, df);
        LocalDateTime ldtEnd = LocalDateTime.parse(enddtConcat, df);
        
        DataInput dataInput = new DataInput();
        try {
            dataInput.checkMissingInput(title, description, loc, type, date,
                                    startHr, startMin, endHr, endMin);
            Appointment.checkValidApptDate(apptDate.getValue(), Integer.parseInt(startHr),
                    Integer.parseInt(startMin), Integer.parseInt(endHr), Integer.parseInt(endMin));
            AppointmentDB.getInstance().checkOverlappingAppt(ldtStart, ldtEnd);
        } catch (NullPointerException | IllegalArgumentException ex)  {
            DialogPopup.showAlert(getDialogStage(),
                                    "Warning",
                                    "",
                                    ex.getMessage(),
                                    AlertType.ERROR);
        } finally {
            if (!dataInput.isMissingInput() && Appointment.isValidApptDate()
                    && !AppointmentDB.isOverlappedAppt()) {
                //Convert LocalDateTime to ZonedDateTime using user's timezone
                ZonedDateTime lczdtStart = ldtStart.atZone(ZoneId.systemDefault());
                ZonedDateTime lczdtEnd = ldtEnd.atZone(ZoneId.systemDefault());

                // Convert to DB timezone
                ZonedDateTime dbzdtStart = lczdtStart.withZoneSameInstant(ZoneId.of("UTC"));
                ZonedDateTime dbzdtEnd = lczdtEnd.withZoneSameInstant(ZoneId.of("UTC"));

                // Convert the ZonedDateTime back to LocalDateTime in order to convert it back to Timestamp
                LocalDateTime lcdbzdtStart = dbzdtStart.toLocalDateTime();
                LocalDateTime lcdbzdtEnd = dbzdtEnd.toLocalDateTime();
                Timestamp lcdbzTSStart = Timestamp.valueOf(lcdbzdtStart);
                Timestamp lcdbzTSEnd = Timestamp.valueOf(lcdbzdtEnd);

                // Now, we're ready to create the new appointment to add to the DB
                Appointment newAppt = new Appointment(title, description, loc, type,
                        lcdbzTSStart,lcdbzTSEnd, MainApp.getCurrentUser().getUserName(),
                        selectedCust.getCustomerName());

                AppointmentDB.getInstance().updateAppt(newAppt, tempAppt, selectedCust);

                getDialogStage().close();
            }
        }        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
    }    

    /**
     * Populate data for current appointment in the Edit window.     * 
     * @param selectedCust customer selected by user from the dialog prompt
     * @param appointment appointment selected by user before clicking "Edit"
     */
    @Override
    public void displayData(Customer selectedCust, Appointment appointment) {
        this.selectedCust = selectedCust;
        this.tempAppt = appointment;
        apptTitle.setText(tempAppt.getTitle());
        apptDescription.setText(tempAppt.getDescription());
        location.setText(tempAppt.getLocation());
        
        apptType.setItems(ApptCboOptions.getInstance().getTypeList());
        // Display the current selected type
        apptType.getSelectionModel().select(tempAppt.getType());
        
        // Display the current selected date        
        apptDate.setValue(tempAppt.dateProperty().get());
        
        apptStartHr.setItems(ApptCboOptions.getInstance().getHourList());
        apptStartMin.setItems(ApptCboOptions.getInstance().getMinuteList());
        apptEndHr.setItems(ApptCboOptions.getInstance().getHourList());
        apptEndMin.setItems(ApptCboOptions.getInstance().getMinuteList());
        // Display the current selected time
        Integer startHr = tempAppt.getStartDateTime().toLocalDateTime().getHour();
        Integer endHr = tempAppt.getEndDateTime().toLocalDateTime().getHour();
        Integer startMin = tempAppt.getStartDateTime().toLocalDateTime().getMinute();
        Integer endMin = tempAppt.getEndDateTime().toLocalDateTime().getMinute();
        
        apptStartHr.setValue(ApptCboOptions.getInstance().getHrByKey(startHr));
        apptEndHr.setValue(ApptCboOptions.getInstance().getHrByKey(endHr));
        apptStartMin.setValue(ApptCboOptions.getInstance().getMinByKey(startMin));
        apptEndMin.setValue(ApptCboOptions.getInstance().getMinByKey(endMin));        
    }
}
