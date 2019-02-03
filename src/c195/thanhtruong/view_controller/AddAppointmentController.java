/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<String> apptHour;

    @FXML
    private ComboBox<String> apptMin;

    @FXML
    private Button addApptCancel;

    @FXML
    private Button addApptButton;
    
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();

    @FXML
    void handleAddAppt(ActionEvent event) {
        
    }

    @FXML
    void handleCancelAddAppt(ActionEvent event) {
        
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        apptHour.setItems(hours);
        apptMin.setItems(minutes);
    }    

    @Override
    public void displayCustData(Customer selectedCust) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
