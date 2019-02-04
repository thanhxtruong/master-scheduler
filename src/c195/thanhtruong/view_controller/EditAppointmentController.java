/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.ApptCboOptions;
import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML
    void handleCancelUpdateAppt(ActionEvent event) {

    }

    @FXML
    void handleSaveUpdate(ActionEvent event) {

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
    public void displayCustData(Customer selectedCust, Appointment appointment) {
        Appointment tempAppt = appointment;
        apptTitle.setText(tempAppt.getTitle());
        apptDescription.setText(tempAppt.getDescription());
        location.setText(tempAppt.getTitle());
        
        apptType.setItems(ApptCboOptions.getInstance().getTypeList());
        // Display the current selected type
        apptType.getSelectionModel().select(tempAppt.getType());
        
        // Display the current selected date        
        apptDate.setValue(tempAppt.Date().get());
        
        apptStartHr.setItems(ApptCboOptions.getInstance().getHours());
        apptStartMin.setItems(ApptCboOptions.getInstance().getMinutes());
        apptEndHr.setItems(ApptCboOptions.getInstance().getHours());
        apptEndMin.setItems(ApptCboOptions.getInstance().getMinutes());
        // Display the current selected time
        apptStartHr.setValue(Integer.toString(tempAppt.getStartDateTime().toLocalDateTime().getHour()));
        apptEndHr.setValue(Integer.toString(tempAppt.getEndDateTime().toLocalDateTime().getHour()));
        apptStartMin.setValue(Integer.toString(tempAppt.getStartDateTime().toLocalDateTime().getMinute()));
        apptEndMin.setValue(Integer.toString(tempAppt.getEndDateTime().toLocalDateTime().getMinute()));
    }

    
    
}
