
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class.
 * Nested CalendarByWeekController and CalendarByMonthController, which are 
 * controllers associated with FXML for the two Tabs.
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
    private CalendarByWeekController calByWeekController;
    
    @FXML
    private Tab weekTab;
    
    private Customer selectedCust;
    private Calendar currentCalDate;
    
    // Display appointments by Week when user clicks on Week tab.
    @FXML
    void displayApptByWeek() {
        calByWeekController.displayAllAppt(currentCalDate);
    }
        
    /**
     * This method is called by WindowsDisplay after loading the controller.
     * This method calls the calculateHeight() method in CalendarByWeekController
     * to get the height of the AnchorPane inside grids as soon as the stage is loaded.
     */
    public void callCalculateHeight() {
        calByWeekController.calculateHeight();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
    }    

    @Override
    public void displayData(Customer selectedCust, Appointment appoinment) {
        // Set the selectedCust
        this.selectedCust = selectedCust;
        currentCalDate = Calendar.getInstance();
        calByMonthController.displayData(selectedCust, null);
        calByMonthController.setDialogStage(getDialogStage());
        calByWeekController.displayData(selectedCust, null);
        calByWeekController.setDialogStage(getDialogStage());
    }
    
}
