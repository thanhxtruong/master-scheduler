/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.CalendarByMonth;
import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_WEEK;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class CalendarByMonthController extends AbstractController implements Initializable {
    @FXML
    private AnchorPane CalByMonth;

    @FXML
    private GridPane container;
    
    private List<Label> labels;
    private List<ComboBox> cboBoxes;
    
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
    
    private Customer selectedCust;
    private CalendarByMonth calByMonth;
    private List<LocalDate> allDatesInMonth = new ArrayList<>();
        
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
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("ApptList.fxml")
                .setTitle("Appointment List")
                .setCustomer(selectedCust)
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleEditAppt(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("ApptList.fxml")
                .setTitle("Appointment List")
                .setCustomer(selectedCust)
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleNewAppt(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("AddAppointment.fxml")
                .setTitle("Add Appointment")
                .setCustomer(selectedCust)
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleNextMonth(ActionEvent event) {
        LocalDate newDate = calByMonth.getNextMonth();
        int newMonth = newDate.getMonthValue();
        int newYear = newDate.getYear();
        currentMonthLabel.setText(calByMonth.getMonthYearAsString(newDate));
        clearAllDays();
        printAllDays(newYear, newMonth);
    }

    @FXML
    void handlePreviousMonth(ActionEvent event) {
        LocalDate newDate = calByMonth.getPreviousMonth();
        int newMonth = newDate.getMonthValue();
        int newYear = newDate.getYear();
        currentMonthLabel.setText(calByMonth.getMonthYearAsString(newDate));
        clearAllDays();
        printAllDays(newYear, newMonth);        
    }
    
    public void clearAllDays() {
        for (int i=0; i < 35; i++) {
            if (container.getChildren() != null) {
                container.getChildren().clear();
            }
        }
    }
    
    public void printAllDays(int year, int month) {        
        allDatesInMonth = calByMonth.getAllDatesInMonth(year, month);
        // Get day for first date of month
        Calendar firstDateOfMonth = Calendar.getInstance();
        firstDateOfMonth.set(year, month-1, 1);
        // Get day as an int
        // Substract 1 to get the Column # in GridPane
        int colNo = firstDateOfMonth.get(DAY_OF_WEEK) - 1;
        int rowNo = 0;
        
        int[] allDates;
        CalendarByMonth calByMonth = new CalendarByMonth();
        allDates = calByMonth.getDatesOnly(year, month);
        labels = new ArrayList<>();
        container.setAlignment(Pos.TOP_LEFT);
        for (int i=0; i < allDates.length; i++) {
            if (colNo > 6) {
                colNo = 0;
                rowNo++;
            }
            VBox vBox = new VBox();
            vBox.setStyle("-fx-border-color: #c0c0c0");
            container.add(vBox, colNo, rowNo, 1, 1);
            Label label = new Label(Integer.toString(allDates[i]));
            label.setFont(new Font(18));
            labels.add(label);            
            vBox.getChildren().add(label);                       
            ComboBox cboBox = new ComboBox();            
            vBox.getChildren().add(cboBox);            
            cboBox.setMaxWidth(Double.MAX_VALUE);
            colNo++;
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        calByMonth = new CalendarByMonth();
        currentMonthLabel.setText(calByMonth.getMonthYearAsString(calByMonth.getCurrentDate()));
        
        LocalDate currentDate = calByMonth.getLcDate();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        
        printAllDays(currentYear, currentMonth);
    }    

    @Override
    public void displayData(Customer selectedCust, Appointment appoinment) {
        this.selectedCust = selectedCust;
        
    }
    
}
