/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class CalendarByMonthController extends AbstractController implements Initializable {
    
    @FXML
    private Button previousMonthBut;

    @FXML
    private Label currentMonthLabel;

    @FXML
    private Button nextMonthBut;

    @FXML
    private Button newApptBut;

    @FXML
    private Button editApptBut;

    @FXML
    private Button deleteApptBut;

    @FXML
    private Button closeCalBut;

    @FXML
    void handleCloseCal(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("Home.fxml")
                .setTitle("Home")
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleDeleteAppt(ActionEvent event) {
        
    }

    @FXML
    void handleEditAppt(ActionEvent event) {
        
    }

    @FXML
    void handleNewAppt(ActionEvent event) {
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("AddAppointment.fxml")
                .setTitle("Add Appointment")
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleNextMonth(ActionEvent event) {

    }

    @FXML
    void handlePreviousMonth(ActionEvent event) {

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
    public void displayCustData(Customer selectedCust) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}