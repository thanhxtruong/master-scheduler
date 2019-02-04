/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Button closeHome;
    
    @FXML
    void handleCalModButton(ActionEvent event) {
        Customer selectedCust = DialogPopup.selectCustomer();
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
    void handleCloseHome(ActionEvent event) {
        DialogPopup.exitConfirmation(getDialogStage());
        getDialogStage().close();
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
    public void displayCustData(Customer selectedCust, Appointment appoinment) {
        
    }
    
}
