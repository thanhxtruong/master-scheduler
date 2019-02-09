/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.ApptCboOptions;
import c195.thanhtruong.model.CalendarByMonth;
import c195.thanhtruong.model.CalendarByWeek;
import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_WEEK;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class CalendarByWeekController extends AbstractController implements Initializable {
    
    @FXML
    private GridPane container;
    
    @FXML
    private AnchorPane CalByWeek;

    @FXML
    private Button previousWeekBut;

    @FXML
    private Label currentWeekLabel;

    @FXML
    private Button nextWeekBut;

    @FXML
    private Button newApptBut;

    @FXML
    private Button editApptBut;

    @FXML
    private Button deleteApptBut;

    @FXML
    private Button apptDetailBut;

    @FXML
    private Button closeCalBut;
    
    private Customer selectedCust;

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
    void handleNextWeek(ActionEvent event) {
        CalendarByWeek.getNextWeek(currentCalDate);
        printAllDays(currentCalDate);
    }

    @FXML
    void handlePreviousWeek(ActionEvent event) {
        CalendarByWeek.getPreviousWeek(currentCalDate);
        printAllDays(currentCalDate);
    }

    @FXML
    void handleSeeDetails(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("ApptList.fxml")
                .setTitle("Appointment List")
                .setCustomer(selectedCust)
                .build();
        windowDisplay.displayScene();
    }
    
    private List<Label> labels;
    private List<ComboBox> cboBoxes;
    private Calendar currentCalDate;
    
    private void printAllDays(Calendar currentFirst) {
        // Set currennt date to the first date of the week
        currentFirst.set(DAY_OF_WEEK, currentFirst.getFirstDayOfWeek());
        Calendar tempCalDate = (Calendar)currentFirst.clone();
        
        currentWeekLabel.setText(CalendarByWeek.getDateAsString(currentFirst));
        
        int rowNo = 1;
        int colNo = 1;
        Insets labelPadding = new Insets(0, 0, 0, 5);
        container.setAlignment(Pos.TOP_CENTER);
        for (Integer key:ApptCboOptions.getInstance().getHourMap().keySet()) {
            AnchorPane anchorPane = new AnchorPane();
            container.add(anchorPane, 0, rowNo, 1, 1);
            Label hrLabel = new Label(key.toString());
            hrLabel.setFont(new Font(18));
            hrLabel.setPadding(labelPadding);
            hrLabel.setAlignment(Pos.TOP_CENTER);
            hrLabel.setStyle("-fx-background-color: #cce6ff");
            anchorPane.getChildren().add(hrLabel);
            AnchorPane.setTopAnchor(hrLabel, 0.0);
            AnchorPane.setBottomAnchor(hrLabel, 0.0);
            AnchorPane.setLeftAnchor(hrLabel, 0.0);
            AnchorPane.setRightAnchor(hrLabel, 0.0);
            rowNo++;
        }
        
        for (int i=1; i<8; i++) {
            AnchorPane anchorPane = new AnchorPane();
            container.add(anchorPane, colNo, 0, 1, 1);
            Date currentDate = tempCalDate.getTime();
            Label dLabel = new Label(new SimpleDateFormat("d").format(currentDate));
            dLabel.setFont(new Font(18));
            dLabel.setPadding(labelPadding);
            dLabel.setAlignment(Pos.TOP_LEFT);
            dLabel.setStyle("-fx-background-color: #cce6ff");
            anchorPane.getChildren().add(dLabel);
            AnchorPane.setTopAnchor(dLabel, 0.0);
            AnchorPane.setBottomAnchor(dLabel, 0.0);
            AnchorPane.setLeftAnchor(dLabel, 0.0);
            AnchorPane.setRightAnchor(dLabel, 0.0);
            tempCalDate.add(DAY_OF_WEEK, 1);
            colNo++;
        }
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
            
    }

    @Override
    public void displayData(Customer selectedCust, Appointment appointment) {
        this.selectedCust = selectedCust;
        currentCalDate = Calendar.getInstance();
//        LocalDate currentLcDate = calByMonth.getLcDate();
//        int currentMonth = currentLcDate.getMonthValue();
//        int currentYear = currentLcDate.getYear();
//        int currentDate = currentLcDate.getDayOfMonth();
        
        printAllDays(currentCalDate);
        
    }
        
}
        

