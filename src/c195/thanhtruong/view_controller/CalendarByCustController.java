/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class CalendarByCustController extends AbstractController implements Initializable {

    @FXML
    private AnchorPane calByMonth;
    @FXML
    private CalendarByMonthController calByMonthController;
    
    @FXML
    private AnchorPane calByWeek;
    
    @FXML
    private CalendarByWeekController calByWeekCOntroller;
    
    private Customer selectedCust;
    
    
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
        // Set the selectedCust
        this.selectedCust = selectedCust;
        System.err.println("selectedCustomer from CalendarBuCustController: " + this.selectedCust.getCustomerName());
        calByMonthController.displayCustData(selectedCust);
        AppointmentDB apptDB = new AppointmentDB();
        apptDB.downloadAppt(selectedCust);
    }
    
}
