/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

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
public class EditAppointmentController implements Initializable {

    @FXML
    private TextField apptTitle;
    @FXML
    private TextField apptDescription;
    @FXML
    private TextField location;
    @FXML
    private DatePicker apptDate;
    @FXML
    private ComboBox<?> apptType;
    @FXML
    private ComboBox<?> apptHour;
    @FXML
    private ComboBox<?> apptMin;
    @FXML
    private Button editApptCancel;
    @FXML
    private Button saveApptButton;
    
    @FXML
    private void handleCancelEditAppt(ActionEvent event) {
    }

    @FXML
    private void handleEditAppt(ActionEvent event) {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        // TODO
    }    

    
    
}
